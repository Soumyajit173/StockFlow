package com.guvi.projects.StockFlow.service.impl;

import com.guvi.projects.StockFlow.dto.OrderItemRequest;
import com.guvi.projects.StockFlow.dto.OrderRequest;
import com.guvi.projects.StockFlow.dto.OrderResponse;
import com.guvi.projects.StockFlow.entity.Order;
import com.guvi.projects.StockFlow.entity.OrderItem;
import com.guvi.projects.StockFlow.entity.Product;
import com.guvi.projects.StockFlow.entity.User;
import com.guvi.projects.StockFlow.enums.OrderStatus;
import com.guvi.projects.StockFlow.exception.BadRequestException;
import com.guvi.projects.StockFlow.exception.ResourceNotFoundException;
import com.guvi.projects.StockFlow.repository.OrderRepository;
import com.guvi.projects.StockFlow.repository.ProductRepository;
import com.guvi.projects.StockFlow.repository.UserRepository;
import com.guvi.projects.StockFlow.service.OrderService;
import com.guvi.projects.StockFlow.util.MapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Order placeOrder(OrderRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        User user = userRepository.findByEmailIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequest req : request.items()) {
            System.out.println(req.productId());
            Product product = productRepository.findById(req.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getStock() < req.quantity()) {
                throw new BadRequestException("Insufficient stock");
            }

            product.setStock(product.getStock() - req.quantity());

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(req.quantity());
            item.setPrice(product.getPrice());
            item.setOrder(order);

            items.add(item);
        }

        order.setItems(items);
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Already cancelled");
        }

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELLED);
        return order;
    }


    public Page<OrderResponse> getOrders(int page, int size) {
        return orderRepository
                .findAll(PageRequest.of(page, size))
                .map(MapperUtil::toOrderResponse);
    }
}
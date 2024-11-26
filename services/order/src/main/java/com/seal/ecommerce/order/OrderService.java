package com.seal.ecommerce.order;


import com.seal.ecommerce.customer.CustomerClient;
import com.seal.ecommerce.customer.CustomerResponse;
import com.seal.ecommerce.exception.BusinessException;
import com.seal.ecommerce.kafka.OrderConfirmation;
import com.seal.ecommerce.kafka.OrderProducer;
import com.seal.ecommerce.orderline.OrderLineRequest;
import com.seal.ecommerce.orderline.OrderLineService;
import com.seal.ecommerce.product.ProductClient;
import com.seal.ecommerce.product.PurchaseRequest;
import com.seal.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest orderRequest) {

        // Find Customer by ID - Using Feign Client
        CustomerResponse customer = customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create Order :: No Customer found with ID: " + orderRequest.customerId()));

        // Purchase the product -> Using RestTemplate
        List<PurchaseResponse> purchaseProducts = productClient.purchaseProducts(orderRequest.products());

        // Create Order
        Order order = orderRepository.save(mapper.toOrder(orderRequest));

        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        //todo start payment process

        //send the order confirmation --> notification service (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchaseProducts

                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::toOrderResponse)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID: %d not found", orderId)));
    }
}

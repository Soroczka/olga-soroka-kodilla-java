package com.kodilla.good.patterns.challenges.food2door.service;

public interface OrderRepository {
    boolean createOrder(OrderRequest orderRequest);
}
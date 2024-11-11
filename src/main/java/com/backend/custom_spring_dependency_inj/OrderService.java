package com.backend.custom_spring_dependency_inj;

import com.backend.custom_spring_dependency_inj.annotations.SimpleComponent;
import com.backend.custom_spring_dependency_inj.annotations.SimplyAutoWired;

@SimpleComponent
public class OrderService {

    @SimplyAutoWired
    private OrderRepository repository;

    public Order getOrderDetails(Integer orderId){
        return repository.getById(orderId);
    }
}

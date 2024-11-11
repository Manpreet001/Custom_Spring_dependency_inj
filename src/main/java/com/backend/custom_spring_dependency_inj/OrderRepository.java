package com.backend.custom_spring_dependency_inj;

import com.backend.custom_spring_dependency_inj.annotations.SimpleComponent;

import java.util.HashMap;
import java.util.Map;

@SimpleComponent
public class OrderRepository {
    Map<Integer, Order> map = new HashMap<>();

    OrderRepository(){
        map.put(1, new Order(1, "", 10.1));
        map.put(2, new Order(2, "", 20.2));
        map.put(3, new Order(3, "", 30.5));
    }

    public Order getById(Integer orderId){
        return map.get(orderId);
    }
}

package com.backend.custom_spring_dependency_inj;

public class Order {
    private int id;
    private String description;
    private Double price;

    public Order(int id, String description, Double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}

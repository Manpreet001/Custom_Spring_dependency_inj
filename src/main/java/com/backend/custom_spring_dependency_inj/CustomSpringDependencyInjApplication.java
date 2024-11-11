package com.backend.custom_spring_dependency_inj;

public class CustomSpringDependencyInjApplication {


    public static void main(String[] args) {
		try{
			ApplicationContext applicationContext = new ApplicationContext(ApplicationConfig.class);
			OrderService orderService = applicationContext.getInstance(OrderService.class);
			Order order = orderService.getOrderDetails(2);
			System.out.println(order);
        }
		catch(Exception e){
			e.printStackTrace();
		}
    }


}

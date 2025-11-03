package org.FoodHub;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


public class Tester {

    @Test
    void testFoodItem() {
        FoodItem foodItem = new FoodItem("Yoghurt", 10, 5.00);
        Assertions.assertEquals("Yoghurt", foodItem.getName());
        Assertions.assertEquals(10, foodItem.getQuantity());
        Assertions.assertEquals(5.00, foodItem.getPrice(), 0.0);
    }

    @Test
    void testSettingOrderAndDeliveryStatuses() {
        Order order = new Order(List.of(), OrderStatus.INCOMING, System.currentTimeMillis(), OrderType.DELIVERY);
        order.setOrderStatus(OrderStatus.STARTED);
        order.setDeliveryStatus(DeliveryStatus.Out_For_Delivery);
        Assertions.assertEquals(OrderStatus.STARTED, order.getOrderStatus());
        Assertions.assertEquals(DeliveryStatus.Out_For_Delivery, order.getDeliveryStatus());
    }
}

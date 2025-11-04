package org.FoodHub;

import org.junit.Assert.*;
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
    void testOrderCreationAndAttributes() {
        FoodItem item1 = new FoodItem("Brisket", 2, 50.0);
        FoodItem item2 = new FoodItem("Macaroni", 1, 4.0);

        Order order = new Order(List.of(item1, item2), OrderStatus.INCOMING, System.currentTimeMillis(), OrderType.DELIVERY);

        Assertions.assertEquals(2, order.getFoodItems().size());
        Assertions.assertEquals(OrderStatus.INCOMING, order.getOrderStatus());
        Assertions.assertEquals(OrderType.DELIVERY, order.getOrderType());
        Assertions.assertEquals(104.0, order.calculateTotalPrice());
        Assertions.assertNotNull(order.getDeliveryStatus());
        Assertions.assertEquals(OrderTypeIcon.Delivery_Icon, order.getOrderTypeIcon());
    }

    @Test
    void testSettingOrderAndDeliveryStatuses() {
        Order order = new Order(List.of(), OrderStatus.INCOMING, System.currentTimeMillis(), OrderType.DELIVERY);
        order.setOrderStatus(OrderStatus.STARTED);
        order.setDeliveryStatus(DeliveryStatus.Out_For_Delivery);

        Assertions.assertEquals(OrderStatus.STARTED, order.getOrderStatus());
        Assertions.assertEquals(DeliveryStatus.Out_For_Delivery, order.getDeliveryStatus());

        order.setOrderStatus(OrderStatus.COMPLETED);

        Assertions.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());

        order.setOrderStatus(OrderStatus.CANCELLED);

        Assertions.assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());
    }

    @Test
    void testOrderManager() {
        OrderManager om = new OrderManager();
        Order order = new Order(List.of(new FoodItem("Pancakes", 100, 5)), OrderStatus.INCOMING, System.currentTimeMillis(), OrderType.TOGO);
        om.addOrder(order);

        Assertions.assertEquals(order, om.findOrder(order.getOrderID()));
    }

    @Test
    void testFileAccessor() {
        FileAccesser accesser = new FileAccesser();

        Assertions.assertEquals("json", accesser.getExtension("file.json"));
        Assertions.assertEquals("xml", accesser.getExtension("file.xml"));
        Assertions.assertEquals("", accesser.getExtension("file"));
    }

    @Test
    void test() {

    }
}

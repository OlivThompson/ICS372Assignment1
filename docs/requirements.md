### 1. The software shall only show delivery status if the order type is delivery
### 2. Any order with missing data shall be thrown out into "orders/ErrorOrders" directory
### 3. The software shall be able to update visually to the UI in real time while orders are being put into the "orders" directory
### 4. When setting the order status to "completed" and click "update status", the software shall automatically export the order into the "orders/completedOrder" directory
### 5. When selecting an order there shall be a button to display order and when clicked the software shall present all the order's details
### 6. In each order type, the software shall have a logo to represent their order type for pick up, delivery, togo
### 7. When an order is being processed with no date, the software shall create order_date at the moment it is being processed
### 8. If SavedDataForLoad.json file already exists but is completely empty, the software shall still be able to startup the program and load all the orders that still needs to be processed
### 9. The software shall have a butto nthat will export all orders called "Export Orders", and shall export all current processed orders into "orders/all_orders" directory
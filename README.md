# ICS372Assignment1
####
File will create a directory for completed_orders and all_orders inside orders folder  
Completed orders will create a completedOrder folder if does not exist already and will be exported to there, it will be overwritten everytime the program restarts  
The same will go for "Export All Orders"  
## Imporant  
Files will be exported after user has exit --- It does not export on runtime
  
### Major flaw in this at the moment is that  
+ User needs to have orders folder in the Application folder (NOT inside src folder) if it does not exist already
+ This program does not fix with the items duplications yet, if an order json file has more than 2 of the same item in 1 order structure, it will not add  
into the quantity,
instead it is turned into its own item object, but it will add and  calculate prices correctly. (Already meet requirement for assignment)
+ This program is a Data Processor Application, it will not save any data into any database it just takes in information and change the state of those files and export it, the original files will stay the same after the of the program.
+ This program DOES NOT read orders that are in JSONArray structured, it only reads a single structure of Order, then exports into JSONArray structured orders (IT WILL FAIL IF YOU TRY TO RUN A JSONArray structured order like the all_orders.json <----The all order exported file)

# How to use
When application is started, user will be prompted with a console for selection of choices

1. Select Order#
   1. You will type in the order## --Not the full order name--
      1. View order to get the full order view
      2. Start the order
      3. Complete the order
      4. Cancel the order
   2. Each of these options will have their own restrictions based on the status of the order
2. Get all Order Status
   1. Will be a quick way to access the status of the orders
3. Show all uncompleted orders
   1. Will print out all the uncompleted orders and total price
4. Show all completed orders
   1. Will print out all completed orders only and total price
   2. This will also export the files into Order#.json after the program ends
5. Export all completed orders
   1. Will export all the orders into one--- All these orders are taken from the Orders folder, and will include (Incoming/Completed/Cancelled) orders after the program ends.
   2. When orders being exported it does not include, the order status! It will just add all the json files into one structured orders array
10. Exit

### The folders will contain the orders/all_orders & orders/completedOrder as examples along with json files, but user can delete it and must have orders folder.  
### User will have to throw in their own JSON file structured as in the example of order.json file in orders folder so it can be read. It does not create an order, it reads the order.
# snapcheck

This project contains the following

1. REST API for fetching all payments, optionally filtering by start and end dates.
   http://localhost:8080/payments?start=2016-01-01&end=2019-01-01
2. Utility to sort the list of payments fetched from the database by their dates using quicksort algorithm.
3. Utility to sort the list of payments fetched from the database by their dates using insertion sort algorithm.
4. Junit test cases to test the above two sorting utility functions.
5. The `Payment` data model contains the following properties
    i. `paymentNumber` (int, primary_key)
    ii. `amount` (double, precision = 2,10)
    iii. `date` (Date)
6. The database of choice is MySQL, the programmic langauge of choice is Java, framework of choice is Springboot with embedded Tomcat as the application server.

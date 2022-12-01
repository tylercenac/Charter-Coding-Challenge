Instructions:

    1. Navigate to the main directory (containing the docker-compose.yml file)
    2. Execute command "docker-compose up --build"
    3. In a browser, navigate to "http://localhost:8012/swagger-ui/index.html#/reward-points-controller"
    4. Use the POST request to populate the database with test data
        i. Paramaters:
            a. customerId: A string that will represent a specific customer
            b. purchaseAmount: An integer representing the amount of money spent by the customer in a purchase
            c. date: A string in mm-dd-yyyy format representing the date the purchase was made
    5. Use the GET requests to interact with the database and see various breakdowns of reward points for each customer
        i. GET requests
            a. Total reward points earned by a specific customerId
            b. Total reward points earned by each customerId in the database
            c. Reward points earned by a specific customerId separated by month
            d. Reward points earned by each customerId in the database separated by month


See the "Screenshots" directory for examples of data and requests.
The whole microtest project has to be build and the subprojects have to be started.

Example calls:
Adds 300 to the total:      localhost:3000/api/transaction/300
Removes 200 from the total: localhost:3000/api/transaction/-200

The postgresql connection config is located at the transaction-service application.yaml.
The kafka is expected on localhost:9092, the topic is notification-1.

I had only 4 hours of free time to work on the task.  As a next step I would work on the implementation extras: unit tests, etc.
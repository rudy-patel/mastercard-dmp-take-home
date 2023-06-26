# Simple Fraud Detection Service
This is a proof of concept **API** that functions as a simple credit card fraud detection service. Given a transaction which includes the card number and transaction amount, it determines if that transaction is fraudulent based on a set of criteria.

The criteria for determining a fraudulent transaction:
* If the amount of a transaction is over $50,000.00, decline the transaction.
* If the card has been used over 60 times in the last 7 days, decline the transaction.
* If the card has been used under 35 times in the last 7 days, decline the transaction if the (transaction
amount/times used in last 7 days) > 500. (E.g. Decline if transaction amount is $9000 and the card has
been used 2 times in the last 7 days. 9000/2 = 4500)
* Approve all other transactions.

### Running the Server
To run the Spring Boot server locally, follow these steps:

1. Make sure you have Java Development Kit (JDK) installed on your machine.

2. Clone the project repository from GitHub.

3. Open a terminal and navigate to the project root directory.

4. Run the following command to build the project: `./gradlew build`.

5. Once the build is successful, run the following command to start the server locally: `./gradlew bootRun`.
This will start the server on `http://localhost:8080`.

### APIs
#### Analyze Transaction
Analyzes a transaction and returns the analysis response.

**Endpoint**: `POST /analyzeTransaction`

**Request Body**:
```
{
  "transaction": {
    "cardNum": 5206840000000001,
    "amount": 100.0,
  }
}
```

**Example Request**:
```
curl -X POST -H "Content-Type: application/json" -d '{
  "transaction": {
    "cardNum": 5206840000000001,
    "amount": 399.99
  }
}' http://localhost:8080/analyzeTransaction
```

**Example Response**:

```
{
    "cardNumber":"5206********0001",
    "transactionAmount":399.99,
    "transactionStatus":"APPROVED",
    "cardUsageCount":30
}
```

#### Get Monitoring Statistics
Retrieves the monitoring statistics.

**Endpoint**: `GET /monitoringStats`

**Example Request**:
```
curl -X GET http://localhost:8080/monitoringStats
```
Example Response:
```
{
    "transactionCount":1,
    "totalTransactionAmount":0.0,
    "percentageApproved":0.0
}
```

### Testing
This project includes the following tests:
* **Unit tests** for *each* class – with test coverage!
* **Integration tests** for the APIs in `TransactionController`
* **Load test** for the `/analyzeTransaction` API

#### Test Coverage
To view the test coverage report for the project, follow the steps below:

1. Ensure that the project has been built and the tests have been executed.

2. Locate the generated test coverage report. The report is usually generated in the `build/reports/jacoco/test/html` directory of the project.

3. Open the test coverage report in a web browser. You can do this by navigating to the `index.html` file in the `html` directory.

That's it! You can now explore the test coverage report to gain insights into the coverage of your tests.

#### Load Test

To perform a load test on the `analyzeTransaction` API, follow these steps:

##### Prerequisites

1. Make sure you have Java Development Kit (JDK) installed on your machine.
2. Clone the project repository from GitHub

#### Set up and Run the Server

1. Open a terminal and navigate to the project root directory.
2. Run the following command to start the server locally: `./gradlew bootRun`
This will start the server on `http://localhost:8080`. Keep the server running while performing the load test.

#### Running the Load Test

1. Open a new terminal window and navigate to the project root directory.
2. Run the following command to execute the load test: `./gradlew loadTest`
This command will run the Gatling load test script and generate the load on the `analyzeTransaction` API endpoint.

3. Monitor the test execution in the terminal. Gatling will provide real-time statistics and summary reports during and after the test. For example:
```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                        100 (OK=100    KO=0     )
> min response time                                   6816 (OK=6816   KO=-     )
> max response time                                   7872 (OK=7872   KO=-     )
> mean response time                                  7368 (OK=7368   KO=-     )
> std deviation                                        294 (OK=294    KO=-     )
> response time 50th percentile                       7463 (OK=7463   KO=-     )
> response time 75th percentile                       7599 (OK=7599   KO=-     )
> response time 95th percentile                       7768 (OK=7768   KO=-     )
> response time 99th percentile                       7861 (OK=7861   KO=-     )
> mean requests/sec                                 11.111 (OK=11.111 KO=-     )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                             0 (  0%)
> 800 ms < t < 1200 ms                                   0 (  0%)
> t > 1200 ms                                          100 (100%)
> failed                                                 0 (  0%)
================================================================================
```

4. Once the load test is completed, you can find the detailed test reports in the `build/gatling-results` directory.

**Note**: Be mindful of the number of requests you are executing in your load test! Since we call external APIs, your IP Address will be **blocked** for *24 hours* if you exceed a certain number of requests/second. See [RandomAPI FAQ](https://api.random.org/faq) for more details.

### Documentation

The documentation for this project is generated using Dokka and provides detailed information about the codebase.

#### Accessing the Documentation
To access the documentation, follow these steps:

1. Navigate to the project's root directory
2. Generate the documentation by running the following Gradle command: `./gradlew dokkaHtml`
3. After the command completes, open the generated documentation by opening the following file in your web browser: `build/dokka/html/index.html`

Once you have opened the main page of the generated documentation in your web browser, you can navigate through the different sections to explore the codebase's documentation. 

### Assumptions
* This project assumes that card number is **sensitive information**. Therefore, the card number is obfuscated \(52068400000000000001 becomes 5206********0001\) in both the API response and logs.
* This service also assumes that the external service (random.org) is available to serve requests.

### Potential improvements
* Use *Spring actuator* to add some additional metrics related to: HTTP Requests, JVM memory, CPU usage, system load averages, etc.
* Use the Dockerfile to work on deploying this to a **Kubernetes** cluster.
* Implement necessary **security measures** such as authentication, authorization, and secure communication (e.g., HTTPS) to protect sensitive data and prevent unauthorized access.
* Implement **caching** mechanisms for frequently accessed data to improve performance and reduce unnecessary external API calls or expensive computations.
* Implement **rate limiting** to prevent abuse or excessive usage of the API by individual clients, ensuring fair and efficient resource allocation.
* Implement a robust **CI/CD** (Continuous Integration/Continuous Deployment) pipeline to automate build, testing, and deployment processes, ensuring smooth delivery of updates and minimizing manual errors.
* Design the architecture in a scalable manner to handle increased traffic and growing user base. Consider technologies like load balancing, horizontal scaling, and cloud infrastructure to achieve scalability.
* If the API evolves over time, consider implementing **versioning** to ensure backward compatibility and provide a smooth transition for existing clients.
* Since the API handles sensitive data and operates in regulated industries, ensure **compliance** with relevant standards and regulations such as GDPR, HIPAA, PCI-DSS, etc.

### Resources
* [Load testing with Gatling](https://www.blazemeter.com/blog/api-load-testing#why)

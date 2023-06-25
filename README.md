# Simple fraud detection service


### TODO
* Unit test `ExternalApiServiceImpl`

### Test Coverage Instructions

To view the test coverage report for the project, follow the steps below:

1. Ensure that the project has been built and the tests have been executed.

2. Locate the generated test coverage report. The report is usually generated in the `build/reports/jacoco/test/html` directory of the project.

3. Open the test coverage report in a web browser. You can do this by navigating to the `index.html` file in the `html` directory.

That's it! You can now explore the test coverage report to gain insights into the coverage of your tests.

### Load Testing

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


### Assumptions
* This project assumes that card number is **sensitive information**. Therefore, the card number is obfuscated \(52068400000000000001 becomes 5206********0001\)in both the API response and logs.

### Improvements
* We can use *Spring actuator* to add some additional metrics related to: HTTP Requests, JVM memory, CPU usage, system load averages, etc.

### Resources
* [Load testing with Gatling](https://www.blazemeter.com/blog/api-load-testing#why)

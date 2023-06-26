import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

// This is a modified implementation from: https://www.blazemeter.com/blog/api-load-testing#why

class BlazeMeterGatlingTest extends Simulation {
   private val baseUrl = "http://localhost:8080"  // Update the base URL to match your API endpoint
   private val endpoint = "/analyzeTransaction"  // Update the endpoint to the one you want to test
   private val contentType = "application/json"
   private val requestCount = 3

   val httpProtocol: HttpProtocolBuilder = http
     .baseUrl(baseUrl)
     .inferHtmlResources()
     .acceptHeader("*/*")
     .contentTypeHeader(contentType)
     .userAgentHeader("curl/7.54.0")

   val scn: ScenarioBuilder = scenario("RecordedSimulation")
     .exec(http("request_0")
       .post(endpoint)
       .body(StringBody("""{
         "transaction": {
           "cardNum": 5206840000000001,
           "amount": 399.99
         }
       }""")).asJson
       .check(status.is(200)))

   setUp(scn.inject(atOnceUsers(requestCount))).protocols(httpProtocol)
}

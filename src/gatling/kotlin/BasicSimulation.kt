import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.*
import io.gatling.javaapi.http.*
import java.time.Duration

class BasicSimulation : Simulation() {

    private val httpProtocol = HttpDsl.http
        .baseUrl("http://localhost:8086")
        .contentTypeHeader("application/json")
        .acceptHeader("application/json")

    private val requestBody = """
        {
          "id": 1,
          "customActionId": 1,
          "requestType": "INFRA_SERVER",
          "runnerSystemType": "JENKINS",
          "additionalInfo": {
            "type": "JENKINS"
          },
          "itsmTicketNo": "ITSM-2024-001",
          "projectId": 101,
          "organizationCode": "ORG-CLVR",
          "requesterId": 6,
          "targetInputs": [
            {"requestType": "INFRA_SERVER", "targetId": "1", "targetKey": "1", "targetName": "ggg" },
            { "requestType": "INFRA_SERVER", "targetId": "2", "targetKey": "2", "targetName": "ggg" },
            { "requestType": "INFRA_SERVER", "targetId": "3", "targetKey": "3", "targetName": "ggg" },
            { "requestType": "INFRA_SERVER", "targetId": "4", "targetKey": "4", "targetName": "ggg" },
            { "requestType": "INFRA_SERVER", "targetId": "5", "targetKey": "5", "targetName": "ggg" },
            { "requestType": "INFRA_SERVER", "targetId": "6", "targetKey": "6", "targetName": "ggg" },
            { "requestType": "INFRA_SERVER", "targetId": "7", "targetKey": "7", "targetName": "ggg" },
            { "requestType": "INFRA_SERVER", "targetId": "8", "targetKey": "8", "targetName": "ggg" },
            { "requestType": "INFRA_SERVER", "targetId": "9", "targetKey": "9", "targetName": "ggg" },
            { "requestType": "INFRA_SERVER", "targetId": "10", "targetKey": "10", "targetName": "ggg" }
          ]
        }
    """

    private val scn = scenario("CustomAction Load Test")
        .exec(
            HttpDsl.http("POST CustomAction Request")
                .post("/customAction/request")
                .body(StringBody(requestBody))
                .check(
                    HttpDsl.status().`is`(200)
                )
        )

    init {
        setUp(
            scn.injectOpen(
                atOnceUsers(1000)
            )
        )
            .protocols(httpProtocol)
            .assertions(
                global().responseTime().mean().lt(3000),
                global().failedRequests().percent().lt(5.0)
            )
    }
}
package test.scala.Simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class checkresponsewithJson extends Simulation{

  //1 Http Conf
  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")
  //.proxy(Proxy("localhost", 8888))


  // 2 Scenario Definition
  val scn = scenario("Check JSON path")
      .exec(http("Get Specific games")
      .get("videogames/1")
      .check(jsonPath("$.name").is("Resident Evil 5")))
    .pause(2, 10)
  //change 4 to 5



  // 3 Load Scenario
  setUp {
    scn.inject(atOnceUsers(1))
  }.protocols(httpConf)


}

package test.scala

import io.gatling.core.Predef._
import io.gatling.http.Predef._


class AddPauseTime extends Simulation{

  //1 Http Conf
  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")
  //.proxy(Proxy("localhost", 8888))


  // 2 Scenario Definition
  val scn = scenario("Video game db -3 calls - pause time")
    .exec(http("Get All Games")
      .get("videogames")
    .check(status.is(200)))
      .pause(5)

    .exec(http("Get All Games-1st")
      .get("videogames/1")
    .check(status.is(100)))
      .pause(2, 10)

    .exec(http("Get All Games-2nd")
      .get("videogames")
    .check(status.not(404)))


  // 3 Load Scenario
  setUp {
    scn.inject(atOnceUsers(1))
  }.protocols(httpConf)

}

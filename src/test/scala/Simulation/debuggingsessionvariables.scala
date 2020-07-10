package test.scala.Simulation

  import io.gatling.core.Predef._
  import io.gatling.http.Predef._

  class debuggingsessionvariables extends Simulation{

    //extract data from the response body

    //1 Http Conf
    val httpConf = http.baseUrl("http://localhost:8080/app/")
      .header("Accept", "application/json")
    //.proxy(Proxy("localhost", 8888))


    // 2 Scenario Definition
    val scn = scenario("Check JSON path")
      .exec(http("Get Specific games")
        .get("videogames/1")
        .check(jsonPath("$.name").is("Resident Evil 4")))
      .pause(2, 10)

      .exec(http("Get All video games")
        .get("videogames")
        .check(jsonPath("$[1].id").saveAs("gameid")))
      .exec{session => println(session); session}
      //check only one response



      .exec(http("Get specific game ")
        .get("videogames/${gameid}")
        .check(jsonPath("$.name").is("Gran Turismo 3"))
        .check(bodyString.saveAs("responseBody")))
      .exec { session => println(session("responseBody").as[String]); session}
    //checks the full response



    // 3 Load Scenario
    setUp {
      scn.inject(atOnceUsers(1))
    }.protocols(httpConf)


  }




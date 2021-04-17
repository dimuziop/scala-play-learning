package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class AppointmentControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    /*"render the index page from a new instance of controller" in {
      val controller = new AppointmentController(stubControllerComponents())
      val home = controller.getAll().apply(FakeRequest(GET, "/api/v1/appointments"))

      status(home) mustBe NO_CONTENT
      /*contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")*/
    }

    "render the index page from the application" in {
      val controller = inject[AppointmentController]
      val home = controller.getAll().apply(FakeRequest(GET, "/api/v1/"))

      status(home) mustBe NO_CONTENT
      /*contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")*/
    }*/

    "render the appointments page from the router" in {
      val request = FakeRequest(GET, "/api/v1/appointments")
      val appointments = route(app, request).get

      status(appointments) mustBe OK
      contentType(appointments) mustBe Some("application/json")
      /*contentAsString(quotes) must include ("{}")*/
    }
  }
}

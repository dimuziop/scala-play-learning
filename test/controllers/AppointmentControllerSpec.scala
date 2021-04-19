package controllers

import appointment.domain.{Appointment, AppointmentService}
import appointment.infrastructure.AppointmentDto
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.{Json, OFormat}
import play.api.test.Helpers._
import play.api.test._
import shared.domain.ResourceNotFound

import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.language.postfixOps

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class AppointmentControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar{

  "HomeController GET" should {

    val appointmentListMutable = new ListBuffer[Appointment]()

    val appos = List(
      Appointment(UUID.randomUUID().toString, "App1", "A brief description 1", "Some place"),
      Appointment(UUID.randomUUID().toString, "App2", "A brief description 2", "Some place"),
      Appointment("8497bcb8-defb-4da5-a83a-e2e2728558e7", "App3", "A brief description 3", "Some place")
    )

    implicit val appointmentsJson: OFormat[Appointment] = Json.format[Appointment]

    "return get all appointments" in {
      val appointmentServiceMock = mock[AppointmentService]
      when(appointmentServiceMock.getAll).thenReturn(appos)
      val controller = new AppointmentController(appointmentServiceMock, stubControllerComponents())
      val appointments = controller.getAll().apply(FakeRequest(GET, "/api/v1/appointments"))

      status(appointments) mustBe OK
      contentType(appointments) mustBe Some("application/json")
      val result: List[Appointment] = Json.fromJson[Seq[Appointment]](contentAsJson(appointments)).get.toList
      result mustBe appos
    }

    "return get all appointments with no appointments" in {
      val appointmentServiceMock = mock[AppointmentService]
      when(appointmentServiceMock.getAll).thenReturn(List())
      val controller = new AppointmentController(appointmentServiceMock, stubControllerComponents())
      val appointments = controller.getAll().apply(FakeRequest(GET, "/api/v1/appointments"))

      status(appointments) mustBe OK
      contentType(appointments) mustBe Some("application/json")
      val result: List[Appointment] = Json.fromJson[Seq[Appointment]](contentAsJson(appointments)).get.toList
      result mustBe List()
    }

    "return appointment with id 8497bcb8-defb-4da5-a83a-e2e2728558e7" in {
      val appointmentServiceMock = mock[AppointmentService]
      when(appointmentServiceMock.getById(appos(2).id)).thenReturn(Right(appos(2)))
      val controller = new AppointmentController(appointmentServiceMock, stubControllerComponents())
      val appointments = controller.getById(appos(2).id).apply(FakeRequest(GET, s"/api/v1/appointments/${appos(2).id}"))

      status(appointments) mustBe OK
      contentType(appointments) mustBe Some("application/json")
      val result: Appointment = Json.fromJson[Appointment](contentAsJson(appointments)).get
      result mustBe appos(2)
    }

    "return not found appointment" in {
      val appointmentServiceMock = mock[AppointmentService]
      when(appointmentServiceMock.getById("no-exists")).thenReturn(Left(new ResourceNotFound("")))
      val controller = new AppointmentController(appointmentServiceMock, stubControllerComponents())
      val appointments = controller.getById("no-exists").apply(FakeRequest(GET, "/api/v1/appointments/no-exists"))

      status(appointments) mustBe NOT_FOUND

    }

    "create a new appointment" in {

      implicit val appointmentDtoJson: OFormat[AppointmentDto] = Json.format[AppointmentDto]

      val serviceMock = mock[AppointmentService]
      val appDto = AppointmentDto("The title", "The description", "Some place")
      val appointment = appDto.toNewAppointment
      val returnValue: Either[Nothing, Appointment] = Right(appointment)

      when(serviceMock.create(any[Appointment])).thenReturn(returnValue)

      val controller = new AppointmentController(serviceMock, stubControllerComponents())

      val body = Json.toJson(appDto)

      val request = FakeRequest(POST, "/api/v1/appointments").withJsonBody(body)

      val appointments = controller.create()(request)

      status(appointments) mustBe CREATED

    }

    /*"return get all appointments with no appointments" in {
      val appointmentServiceMock = mock[AppointmentService]
      when(appointmentServiceMock.getAll).thenReturn(List())
      val controller = new AppointmentController(appointmentServiceMock, stubControllerComponents())
      val appointments = controller.getAll().apply(FakeRequest(GET, "/api/v1/appointments"))

      status(appointments) mustBe OK
      contentType(appointments) mustBe Some("application/json")
      val result: List[Appointment] = Json.fromJson[Seq[Appointment]](contentAsJson(appointments)).get.toList
      result mustBe List()
    }*/

    /*"render the index page from the application" in {
      val controller = inject[AppointmentController]
      val home = controller.getAll().apply(FakeRequest(GET, "/api/v1/"))

      status(home) mustBe NO_CONTENT
      /*contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")*/
    }*/

    "render the appointment page from the router" in {
      val request = FakeRequest(GET, "/api/v1/appointments")
      val appointments = route(app, request).get

      status(appointments) mustBe OK
      contentType(appointments) mustBe Some("application/json")
      val result: Seq[Appointment] = Json.fromJson[Seq[Appointment]](contentAsJson(appointments)).get
      result.filter(_.id == "8497bcb8-defb-4da5-a83a-e2e2728558e7").head mustBe (Appointment("8497bcb8-defb-4da5-a83a-e2e2728558e7", "App3", "A brief description 3", "Some place"))
    }
  }
}

package controllers

import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import quotes.domain.{Appointment, AppointmentService}
import play.api.libs.json._

import javax.inject.Inject

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 15:04
 */
class AppointmentController @Inject()(
                                       appointmentService: AppointmentService,
                                       val controllerComponents: ControllerComponents) extends BaseController {

  implicit val appointmentsJson = Json.format[Appointment]

  def getAll: Action[AnyContent] = Action {
    val appointments = appointmentService.getAll
    if (appointments.isEmpty) {
      NoContent
    } else {
      Ok(Json.toJson(appointments))
    }
  }

  def getById(id: String): Action[AnyContent] = Action {
    //appointmentService.getById(id).fold(() => NoContent)(appointment => Ok(Json.toJson(appointment))).
    appointmentService.getById(id) match {
      case Left(_) => NotFound
      case Right(appointment) => Ok(Json.toJson(appointment))
    }
  }

}

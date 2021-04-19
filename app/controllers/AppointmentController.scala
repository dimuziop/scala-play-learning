package controllers

import appointment.domain.{Appointment, AppointmentService}
import appointment.infrastructure.AppointmentDto
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 15:04
 */
@Singleton
class AppointmentController @Inject()(
                                       appointmentService: AppointmentService,
                                       val controllerComponents: ControllerComponents) extends BaseController {

  implicit val appointmentsJson: OFormat[Appointment] = Json.format[Appointment]

  def getAll: Action[AnyContent] = Action {
    val appointments = appointmentService.getAll
    if (appointments.isEmpty) {
      Ok(Json.toJson(List()))
    } else {
      Ok(Json.toJson(appointments))
    }
  }

  def getById(id: String): Action[AnyContent] = Action {
    appointmentService.getById(id) match {
      case Left(_) => NotFound
      case Right(appointment) => Ok(Json.toJson(appointment))
    }
  }

  def create(): Action[AnyContent] = Action { implicit request =>

    println(request.body)

    implicit val appointmentDtoJson: OFormat[AppointmentDto] = Json.format[AppointmentDto]
    val requestContent = request.body.asJson.flatMap(Json.fromJson[AppointmentDto](_).asOpt)

    requestContent match {
      case Some(appDto) =>
        val res = appointmentService.create(appDto.toNewAppointment)
        res match {
          case Left(_) => InternalServerError
          case Right(appointment) => Created(Json.toJson(appointment))
        }
      case None => BadRequest
    }
  }

}

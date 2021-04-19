package appointment.application

import appointment.domain.{Appointment, AppointmentRepository, AppointmentService}
import shared.domain.{Failure, ResourceNotFound}

import javax.inject.Inject

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 16:32
 */
class Service @Inject()(appointmentRepository: AppointmentRepository) extends AppointmentService {
  override def getAll: List[Appointment] = this.appointmentRepository.getAll

  override def getById(id: String): Either[Failure, Appointment] =
    this.appointmentRepository.getAll.find(appointment => appointment.id == id)
      .fold[Either[Failure, Appointment]](Left(new ResourceNotFound("")))(x => Right(x))

  override def create(appointment: Appointment): Either[Failure, Appointment] = {
    this.appointmentRepository.create(appointment)
  }
}

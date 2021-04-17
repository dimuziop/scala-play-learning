package quotes.application

import quotes.domain.{Appointment, AppointmentRepository, AppointmentService}

import javax.inject.Inject

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 16:32
 */
class Service @Inject()(appointmentRepository: AppointmentRepository) extends AppointmentService {
  override def getAll: List[Appointment] = this.appointmentRepository.getAll

  override def getById(id: String): Either[Option[Nothing], Appointment] =
    this.appointmentRepository.getAll.find(appointment => appointment.id == id)
      .fold[Either[Option[Nothing], Appointment]](Left(None))(x => Right(x))
}

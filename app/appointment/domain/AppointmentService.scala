package appointment.domain

import com.google.inject.ImplementedBy
import appointment.application.Service
import shared.domain.Failure

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 15:34
 */
@ImplementedBy(classOf[Service])
trait AppointmentService {

  def getAll: List[Appointment]
  //TODO: failure
  def getById(id: String): Either[Failure, Appointment]

  def create(appointment: Appointment): Either[Failure, Appointment]

}

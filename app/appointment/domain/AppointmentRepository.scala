package appointment.domain

import com.google.inject.ImplementedBy
import appointment.infrastructure.InMemoryRepo
import shared.domain.Failure

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 15:33
 */
@ImplementedBy(classOf[InMemoryRepo])
trait AppointmentRepository {

  def getAll: List[Appointment]

  def create(appointment: Appointment): Either[Failure, Appointment]

}

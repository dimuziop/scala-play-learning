package quotes.domain

import com.google.inject.ImplementedBy
import quotes.application.Service

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 15:34
 */
@ImplementedBy(classOf[Service])
trait AppointmentService {

  def getAll: List[Appointment]
  //TODO: failure
  def getById(id: String): Either[Option[Nothing], Appointment]

}

package quotes.domain

import com.google.inject.ImplementedBy
import quotes.infrastructure.InMemoryRepo

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 15:33
 */
@ImplementedBy(classOf[InMemoryRepo])
trait AppointmentRepository {

  def getAll: List[Appointment]

}

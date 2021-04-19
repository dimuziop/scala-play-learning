package appointment.infrastructure

import appointment.domain.{Appointment, AppointmentRepository}
import shared.domain.{Failure, StorageError}

import java.util.UUID
import scala.collection.mutable.ListBuffer

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 15:31
 */
class InMemoryRepo extends AppointmentRepository{

  val memoryStorage = new ListBuffer[Appointment]()

  override def getAll: List[Appointment] = {
    memoryStorage ++ List(
      Appointment(UUID.randomUUID().toString, "App1", "A brief description 1", "Some place"),
      Appointment(UUID.randomUUID().toString, "App2", "A brief description 2", "Some place"),
      Appointment("8497bcb8-defb-4da5-a83a-e2e2728558e7", "App3", "A brief description 3", "Some place")
    )
  }.toList

  override def create(appointment: Appointment): Either[Failure, Appointment] =
    try {
      memoryStorage += appointment
      Right(appointment)
    } catch {
      case e: Exception => Left(new StorageError(e.toString))
    }
}

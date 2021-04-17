package quotes.infrastructure

import quotes.domain.{Appointment, AppointmentRepository}

import java.util.UUID

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 15:31
 */
class InMemoryRepo extends AppointmentRepository{
  override def getAll: List[Appointment] = {
    List(
      Appointment(UUID.randomUUID().toString, "App1", "A brief description 1", "Some place"),
      Appointment(UUID.randomUUID().toString, "App2", "A brief description 2", "Some place"),
      Appointment("8497bcb8-defb-4da5-a83a-e2e2728558e7", "App3", "A brief description 3", "Some place")
    )
  }
}

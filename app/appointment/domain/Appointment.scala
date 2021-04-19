package appointment.domain

import java.util.UUID

/**
 * User: patricio
 * Date: 17/4/21
 * Time: 15:26
 */
case class Appointment(id: String, title: String, description: String, location: String)

object Appointment {
  def create(title: String, description: String, location: String): Appointment = new Appointment(UUID.randomUUID().toString, title, description, location)
}

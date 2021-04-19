package appointment.infrastructure

import appointment.domain.Appointment

/**
 * User: patricio
 * Date: 18/4/21
 * Time: 08:54
 */
case class AppointmentDto(title: String, description: String, location: String) {
  def toNewAppointment:Appointment = Appointment.create(title, description, location)
}

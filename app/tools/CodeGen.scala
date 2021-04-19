package tools

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile",
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/postgres?user=admin&password=root",
    "/home/patricio/Documents/Courses/Udemy/scala/learning-from-play/scala-play-initial/app",
    "infrastructure.models", None, None, true, false
  )
}
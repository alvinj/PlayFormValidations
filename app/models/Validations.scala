package models

case class Validations (
  username: String,
  firstName: String,
  number: Int,
  score: Int,
  host: String,
  age: Option[Int],
  notes: Option[String]
) 


package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Validations
import play.api.data.validation.Constraints._
import scala.util.matching.Regex

object ValidationsController extends Controller {

  val x = pattern("".r, "", "")
  
  val validationsForm = Form(
    mapping(
      "username" -> nonEmptyText(5, 20),
      "firstName" -> text(1, 20),
      "number" -> number(1, 5),
      "score" -> number.verifying(min(1), max(100)),      
      "host" -> nonEmptyText.verifying(pattern("[a-z]+".r, "One or more lowercase characters", "Error")),
      "age" -> optional(number),
      "notes" -> optional(text)
    )(Validations.apply)(Validations.unapply)
      verifying("If age is given, it must be greater than zero", model =>
        model.age match {
          case Some(age) => age < 0 
          case None => true
        }
      )
  )
  
  def add = Action {
    Ok(views.html.validationsform(validationsForm))
  }

  /**
   * Handle the 'add' form submission.
   */
  def save = Action { implicit request =>
    validationsForm.bindFromRequest.fold(
      errors => BadRequest(views.html.validationsform(errors)),
      stock => {
        // would normally do a 'save' here
        Redirect(routes.ValidationsController.add)
      }
    )
  }

}






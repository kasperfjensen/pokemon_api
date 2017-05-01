package controllers

import play.api._
import play.api.mvc._

class HealthController extends Controller {

  def health = Action {
    Ok("Up!")
  }

}

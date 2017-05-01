package controllers

import play.api._
import play.api.mvc._

class WelcomeController extends Controller {

  def index = Action {
    Ok("Welcome!")
  }

}
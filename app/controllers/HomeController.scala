package controllers

import java.sql.DriverManager
import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class HomeController @Inject() extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  def home=  Action{

      Ok(views.html.home())
  }


}

package controllers

import java.sql.DriverManager

import model.Pizza
import play.api.mvc.{Action, Controller}

import scala.util.Random

class AddPizzaController extends Controller{


  var total=0
  def addPizza= Action {
    implicit request =>
      val quantity = request.body.asFormUrlEncoded.get("quantity")(0)
      val size=request.body.asFormUrlEncoded.get("Size")(0)
      val base=request.body.asFormUrlEncoded.get("base")(0)
      val sauce=request.body.asFormUrlEncoded.get("sauce")(0)
      val topping=request.body.asFormUrlEncoded.get("Topping").toList
      val cheese=request.body.asFormUrlEncoded.get("cheese")(0)
      val pizza=new Pizza(quantity.toString.toInt,size,base,sauce,topping,cheese)
      val total=pizza.calculateBill()

      Class.forName("com.mysql.jdbc.Driver")
      val con=DriverManager.getConnection("jdbc:mysql://localhost/pizzasystem","root","tausif")
      val st=con.createStatement()

      val orderId= if(request.session.get("orderID").isEmpty)
        {
         Random.nextInt()
        }else{
        val oId=request.session.get("orderID").get
        oId.toInt
      }

      val query="insert into order_detail(detail_id,quant,size,base,sauce,topping,cheese,total) values("+orderId+","+quantity+",'"+size+"','"+base+"','"+sauce+"','"+topping.mkString(",")+"','"+cheese+"',"+total+")"
      val res=st.executeUpdate(query)
      Ok(views.html.home()).withSession("orderID"->orderId.toString)
      
  }
}

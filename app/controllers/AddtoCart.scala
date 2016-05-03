package controllers

import java.sql.DriverManager

import model.Pizza
import play.api.mvc.{Action, Controller}

import scala.util.Random

/**
  * Created by tsn3316 on 5/3/16.
  */
class AddtoCart extends Controller{

  def addPizza=Action{
    implicit request =>
      val size=request.getQueryString("Size").getOrElse("")
      val base=request.getQueryString("base").getOrElse("")
      val topping=request.getQueryString("Topping").toList
      val total=request.getQueryString("total").getOrElse("0")
      val item=request.getQueryString("item").getOrElse("0")

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

      val query="insert into order_detail(detail_id,quant,size,base,sauce,topping,cheese,total) values("+orderId+","+1+",'"+size+"','"+base+"','"+""+"','"+topping.mkString(",")+"','"+""+"',"+total+")"
      val res=st.executeUpdate(query)
      Ok(views.html.addToCart(item)).withSession("orderID"->orderId.toString)
  }

}

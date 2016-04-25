package controllers

import java.sql.DriverManager

import play.api.mvc.{Action, Controller}

class OrderCancelController extends Controller{

  def cancelOrder=Action{
    request=>
    Class.forName("com.mysql.jdbc.Driver")
    val connection=DriverManager.getConnection("jdbc:mysql://localhost/pizzasystem","root","tausif")
    val orderId=request.session.get("orderID").getOrElse("")
    if(orderId.isEmpty){
      Ok(views.html.error())
    }else{
      val st=connection.createStatement();
      val query="delete from order_detail where detail_id="+orderId.toInt
      val orderNo=st.executeUpdate(query)
      println("order deleted "+orderNo)
      Ok(views.html.orderCancel()).withNewSession
    }
  }

}

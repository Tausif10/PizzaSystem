package controllers

import java.sql.DriverManager
import java.util.Calendar

import play.api.mvc.{Action, Controller}

import scala.util.Random

/**
  * Created by tsn3316 on 4/15/16.
  */
class PrintBillController extends Controller{

  def printBill= Action{
    request=>
      val orderId=request.session.get("orderID").getOrElse("")
      val custName=request.body.asFormUrlEncoded.get("custName")(0)
      val totalBill=request.body.asFormUrlEncoded.get("total")(0)
      val paymentId=Random.nextInt();
      val date=Calendar.getInstance().getTime

      Class.forName("com.mysql.jdbc.Driver")
      val con=DriverManager.getConnection("jdbc:mysql://localhost/pizzasystem","root","tausif")
      val st=con.createStatement()

      val sql="insert into order1 values("+orderId+",'"+custName+"',"+paymentId+","+totalBill+","+"'"+date+"')"
      st.executeUpdate(sql)

      Ok(views.html.home()).withNewSession
  }

}

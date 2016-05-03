package controllers

import java.sql.DriverManager

import model.{Order, Pizza}
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._

class BillController extends Controller {



  def bill = Action {
    request =>
      var sum=0
      var costList:List[Int]=Nil
      var detailList:List[String]=Nil

      Class.forName("com.mysql.jdbc.Driver")
      val con=DriverManager.getConnection("jdbc:mysql://localhost/pizzasystem","root","tausif")
      val st=con.createStatement()

      val orderId=request.session.get("orderID").getOrElse("")
      val item=request.getQueryString("item").getOrElse("")
      println("bill add item "+item)
      if(orderId.isEmpty)
        {
          Ok(views.html.noOrder());
        }else {
        val sql = "select * from order_detail where detail_id="+orderId.toInt;
        val detail=st.executeQuery(sql)

       while(detail.next()){
         sum=sum+detail.getString("total").toInt;
         val details=detail.getString(3)+" | "+detail.getString(4)+" | "+detail.getString(5)+" | "+detail.getString(6)+" | "+detail.getString(7)+" | "+detail.getString(8)
         val cost=detail.getInt(9)
         detailList=detailList:::details::Nil
         costList=costList:::cost::Nil
        }

       println("d= "+detailList)

        val pizza = new Pizza();
        val serviceTax = pizza.getServiceTax(sum)
        val vat = pizza.getVat(sum)
        val total = pizza.getTotal(sum)

        Ok(views.html.bill(item.toString,detailList,costList,sum, serviceTax, vat, total))
      }


  }



  def error=Action{
    Ok(views.html.error())
  }
}
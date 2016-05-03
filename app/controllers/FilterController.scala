package controllers

import java.sql.DriverManager

import play.api.mvc.{Action, Controller}

/**
  * Created by tsn3316 on 4/26/16.
  */
class FilterController extends Controller{

  def filter=Action{
    request=>
      var items:List[String]=Nil
      var typeId:List[Int]=Nil
      var priceList:List[Int]=Nil
      var msg=""
      var details:List[String]=Nil
      var sizes:List[String]=Nil
      var typeSize:List[String]=Nil
      var typeSizeId:List[Int]=Nil


      val size=request.queryString.getOrElse("size",Nil)
      val itm=if(request.session.get("item").getOrElse("").isEmpty){
        request.getQueryString("item").get
      }else{
        request.session.get("item").getOrElse("")
      }

      val chkList=size.toList

        Class.forName("com.mysql.jdbc.Driver")
        val connection = DriverManager.getConnection("jdbc:mysql://localhost/pizzasystem", "root", "tausif")
        val sizeSt=connection.createStatement();
        val query1 = "select * from item_size where item_id=(select item_id from items where item='" + itm.toLowerCase + "')"
        val resultSet = sizeSt.executeQuery(query1)
        while (resultSet.next()) {
          sizes=sizes:::resultSet.getString(3)::Nil
        }

      println("size "+size.length)
      if(size.length>0) {
        msg="Result Not found"
        val st = connection.createStatement();
        println("sizes="+size.mkString("'","','","'").toLowerCase)
        val query = "select * from item_type where size_id in(select id from item_size where size in(" + size.mkString("'","','","'").toLowerCase+ "))"
        val result = st.executeQuery(query)


        while (result.next()) {
          msg=""
          typeId = typeId ::: result.getInt(1) :: Nil
          items = items ::: result.getString(4) :: Nil
          typeSizeId=typeSizeId:::result.getInt(3)::Nil
        }
        for(id<-typeId){
          val st1 = connection.createStatement();
          val query = "select * from type_detail where type_id="+id
          val result1 = st1.executeQuery(query)
          while (result1.next()) {
            details = details ::: result1.getString(2) :: Nil
            priceList = priceList ::: result1.getInt(3) :: Nil
          }
        }

        for(sizeId<-typeSizeId){
          val st1 = connection.createStatement();
          val query = "select size from item_size where id="+sizeId
          val res = st1.executeQuery(query)
          while (res.next()){
            typeSize=typeSize:::res.getString(1)::Nil
          }

        }
      }else{
        msg="Result Not found"
        val st = connection.createStatement();
        val query = "select * from item_type"
        val result = st.executeQuery(query)


        while (result.next()) {
          msg=""
          typeId = typeId ::: result.getInt(1) :: Nil
          items = items ::: result.getString(4) :: Nil
          typeSizeId=typeSizeId:::result.getInt(3)::Nil
        }
        for(id<-typeId){
          val st1 = connection.createStatement();
          val query = "select * from type_detail where type_id="+id
          val result1 = st1.executeQuery(query)
          while (result1.next()) {
            details = details ::: result1.getString(2) :: Nil
            priceList = priceList ::: result1.getInt(3) :: Nil
          }
        }

        for(sizeId<-typeSizeId){
          val st1 = connection.createStatement();
          val query = "select size from item_size where id="+sizeId
          val res = st1.executeQuery(query)
          while (res.next()){
            typeSize=typeSize:::res.getString(1)::Nil
          }

        }

      }
      println("check list "+chkList)
      Ok(views.html.newHome(itm,chkList,sizes,msg,items,details,priceList,typeSize))

  }
}

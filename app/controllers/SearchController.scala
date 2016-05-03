package controllers

import java.sql.DriverManager

import play.api.mvc.{Action, Controller}

/**
  * Created by tsn3316 on 4/25/16.
  */
class SearchController extends Controller {

  def search = Action {
    request =>
      var sizes:List[String]=Nil
      var types:List[String]=Nil
      var typeId:List[Int]=Nil
      var details:List[String]=Nil
      var cost:List[Int]=Nil
      var sizeId:List[Int]=Nil
      var typeSize:List[String]=Nil

      var msg=""
      val item = request.getQueryString("item").getOrElse("")
      if (!item.isEmpty) {
        Class.forName("com.mysql.jdbc.Driver")
        val con = DriverManager.getConnection("jdbc:mysql://localhost/pizzasystem", "root", "tausif")
        val st = con.createStatement()
        val word = item.split(" ")

        for (i <- word) {
          msg = "Result Not found"
          val query = "select * from item_size where item_id=(select item_id from items where item='" + i.toLowerCase + "')"
          val resultSet = st.executeQuery(query)
          while (resultSet.next()) {
            msg = ""
            sizes = sizes ::: resultSet.getString(3) :: Nil
          }
          val ps = con.createStatement()
          val typeResultSet = ps.executeQuery("select * from item_type where item_id=(select item_id from items where item='" + i.toLowerCase + "')")
          while (typeResultSet.next()) {
            typeId = typeId ::: typeResultSet.getInt(1)::Nil
            types = types ::: typeResultSet.getString(4) :: Nil
            sizeId=sizeId:::typeResultSet.getInt(3)::Nil
          }

          for (id <- typeId) {
          val detail = con.createStatement()
          val detailResultSet = detail.executeQuery("select * from type_detail where type_id="+id)
            while (detailResultSet.next()){
              details=details:::detailResultSet.getString(2)::Nil
              cost=cost:::detailResultSet.getInt(3)::Nil
            }
        }

          for (id <- sizeId) {
            val st2 = con.createStatement()
            val detailResultSet = st2.executeQuery("select * from item_size where id="+id)
            while (detailResultSet.next()){
                typeSize=typeSize:::detailResultSet.getString(3)::Nil
            }
          }

        }
      }
      Ok(views.html.newHome(item,Nil,sizes,msg,types,details,cost,typeSize)).withSession("item"->item)
  }

}

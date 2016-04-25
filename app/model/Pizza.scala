package model

case class Pizza(quantity:Int,size:String,base:String,sauce:String,topping:List[String],cheese:String){

  def this(){
    this(0,"","","",Nil,"")
  }

  def calculateBill():Float ={
    size.toLowerCase match {
      case "small" => {
        (getBaseAmount+(getToppingAmount*topping.size))*quantity
      }
      case "medium" => {
        (getBaseAmountMedium+(getToppingAmount*topping.size))*quantity
      }
      case "large" => {
        (getBaseAmountLarge+(getToppingAmount*topping.size))*quantity
      }
    }
  }

  private def getBaseAmount: Float = {
    println(base.toLowerCase)
    (base.toLowerCase).trim match {
        case "normal" =>75
        case "pan crust" | "thin crust" => 100
        case "cheesy bites" => 125
      }
  }

  private def getBaseAmountMedium: Float = {
    base.toLowerCase.trim match {
      case "normal" => math.round((75*25/100f+75))
      case "pan crust" | "thin crust" => math.round((100*25f/100+100))
      case "cheesy bites" => math.round((125*25f/100+125))
    }
  }

  private def getBaseAmountLarge: Float = {
    base.toLowerCase match {
      case "normal" => math.round((75*50f/100+75))
      case "pan crust" | "thin crust" => math.round((100*50f/100+100))
      case "cheesy bites" => math.round((125*50f/100+125))
    }
  }

 private def getToppingAmount:Int={
    size.toLowerCase match {
      case "small" => 15
      case "medium" => 30
      case "large" => 45
    }
   }

  def getServiceTax(total:Float)={
    math.round(total*5/100)
  }

  def getVat(total:Float)={
    math.round(total*6/100)
  }

  def getTotal(total:Float)={
    math.round(total+getServiceTax(total)+getVat(total))
  }
}



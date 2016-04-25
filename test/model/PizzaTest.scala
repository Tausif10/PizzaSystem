package model

import org.scalatest.{Matchers, FunSpec}

/**
  * Created by tsn3316 on 4/19/16.
  */
class PizzaTest extends FunSpec with Matchers {

  describe("Pizza") {

    it("should return 0 when bill is zero") {
      val pizza=new Pizza(1,"small","abc","Margarita",List("Onion"),"Cream")
      pizza.getServiceTax(10) should not be(0)

    }

  }
}

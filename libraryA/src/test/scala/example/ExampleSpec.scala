package example

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ExampleSpec extends AnyFunSuite with Matchers {
  test("Example test") {
    val sut = new Example

    sut.add(2, 2) shouldBe 4
  }
}

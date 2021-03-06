package org.autotdd.engine

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.autotdd.engine._

class EngineConstructionSmokeTest extends FlatSpec with ShouldMatchers with PosNegTestTrait {
  sealed abstract class Frame(val first: Int, val second: Int, val third: Int = 0, val size: Int = 2)
  case class NormalFrame(f: Int, s: Int) extends Frame(f, s);
  case class SpareFrame(f: Int, s: Int, t: Int) extends Frame(f, s, t);
  case class StrikeFrame(f: Int, s: Int, t: Int) extends Frame(f, s, t, 1);
  "The bowling kata get engine" should "build correctly" in {
    val get = Engine2((p1: List[Int], p2: Int) => 0);

    get constraint (List(7, 10, 4, 3), 0, 7,
      (rolls, i) => rolls(i),
      (rolls, i) => i >= 0 && i < rolls.length)

    get constraint (List(7, 10, 4, 3), -1, 0)
    get constraint (List(7, 10, 4, 3), 4, 0)
    get constraint (List(7, 10, 4, 3), 5, 0)
    assert(get.toString()
      ==
      "if(i.>=(0).&&(i.<(rolls.length)))\n" +
      " rolls.apply(i)\n" +
      "else\n" +
      " 0\n", "[" + get.toString + "]")
  }

  "The bowling kata makeFrame engine" should "build correctly" in {
    val get = Engine2((rolls: List[Int], i: Int) => 0);
    get constraint (List(7, 10, 4, 3), 0, 7,
      (rolls, i) => rolls.apply(i),
      (rolls, i) => i >= 0 && i < rolls.length)

    val makeFrame = Engine2[List[Int], Int, Frame]((rolls: List[Int], i: Int) => NormalFrame(get(rolls, i), get(rolls, i + 1)))

    makeFrame.constraint(List(7, 2, 5, 5, 3, 0, 10, 2, 4), 0, NormalFrame(7, 2))

    makeFrame.constraint(List(7, 2, 5, 5, 3, 0, 10, 2, 4), 6, StrikeFrame(10, 2, 4),
      (rolls, i) => StrikeFrame(rolls(i), get(rolls, i + 1), get(rolls, i + 2)),
      (rolls, i) => get(rolls, i) == 10)

    makeFrame.constraint(List(7, 2, 5, 5, 3, 0, 10, 2, 4), 2, SpareFrame(5, 5, 3),
      (rolls, i) => SpareFrame(get(rolls, i), get(rolls, i + 1), get(rolls, i + 2)),
      (rolls, i) => get(rolls, i) + get(rolls, i + 1) == 10)

    makeFrame.constraint(List(7, 2, 5, 5, 3, 0, 10, 2, 4), 4, NormalFrame(3, 0))

    makeFrame.assertion(List(7), 0, NormalFrame(7, 0))

    makeFrame.assertion(List(5, 5), 0, SpareFrame(5, 5, 0))

    makeFrame.assertion(List(10), 0, StrikeFrame(10, 0, 0))

    makeFrame.assertion(List(10, 2), 0, StrikeFrame(10, 2, 0))
  }

}
package org.autotdd.eclipse2

import org.junit.runner.RunWith
import org.autotdd.engine.tests.AutoTddRunner
import org.autotdd.engine.Engine1

@RunWith(classOf[AutoTddRunner])
case class EngineDescription(val name: String, val description: String)

object AutoTddParser {
  val itemParse = Engine1((item: String) => {
    val index = item.indexOf("\n")
    index match {
      case -1 => throw new IllegalArgumentException(item);
      case _ => EngineDescription(item.substring(0, index), item.substring(index + 1))
    }
  })
  //  itemParse.constraint("abc\ndef", EngineDescription("abc", "def"))
  //  //TODO Need to be able to assert throws exceptions
  //
  //  val parse = Engine1((t: String) => t.split(AutoTddRunner.separator).map(itemParse))
  //  parse.constraint("abc\ndef" + AutoTddRunner.separator + "123\n234",
  //    Array(EngineDescription("abc", "def"), EngineDescription("123", "234")))

  def apply(s: String) = itemParse(s)
}
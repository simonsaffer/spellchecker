package problems

import org.scalatest.FunSuite

import scala.collection.mutable

/**
  * Created by simonsaffer on 2015-12-26.
  */
class SpellCheckerTest extends FunSuite {

  test("Test editDistance1") {

    val expected = Set(
      // Deletes
      "ow", "nw", "no",
      // Transposes
      "onw", "nwo",
      // Replaces
      "aow", "bow", "cow", "dow", "eow", "fow", "gow", "how", "iow", "jow", "kow", "low", "mow", "now", "oow", "pow", "qow",
      "row", "sow", "tow", "uow", "vow", "wow", "xow", "yow", "zow", "naw", "nbw", "ncw", "ndw", "new", "nfw", "ngw", "nhw",
      "niw", "njw", "nkw", "nlw", "nmw", "nnw", "now", "npw", "nqw", "nrw", "nsw", "ntw", "nuw", "nvw", "nww", "nxw", "nyw",
      "nzw", "noa", "nob", "noc", "nod", "noe", "nof", "nog", "noh", "noi", "noj", "nok", "nol", "nom", "non", "noo", "nop",
      "noq", "nor", "nos", "not", "nou", "nov", "now", "nox", "noy", "noz",
      // Inserts
      "anow", "bnow", "cnow", "dnow", "enow", "fnow", "gnow", "hnow", "inow", "jnow", "know", "lnow", "mnow", "nnow", "onow", 
      "pnow", "qnow", "rnow", "snow", "tnow", "unow", "vnow", "wnow", "xnow", "ynow", "znow", "naow", "nbow", "ncow", "ndow",
      "neow", "nfow", "ngow", "nhow", "niow", "njow", "nkow", "nlow", "nmow", "nnow", "noow", "npow", "nqow", "nrow", "nsow",
      "ntow", "nuow", "nvow", "nwow", "nxow", "nyow", "nzow", "noaw", "nobw", "nocw", "nodw", "noew", "nofw", "nogw", "nohw",
      "noiw", "nojw", "nokw", "nolw", "nomw", "nonw", "noow", "nopw", "noqw", "norw", "nosw", "notw", "nouw", "novw", "noww",
      "noxw", "noyw", "nozw", "nowa", "nowb", "nowc", "nowd", "nowe", "nowf", "nowg", "nowh", "nowi", "nowj", "nowk", "nowl",
      "nowm", "nown", "nowo", "nowp", "nowq", "nowr", "nows", "nowt", "nowu", "nowv", "noww", "nowx", "nowy", "nowz"
    )

    assertResult(expected)(SpellChecker.editDistance1("now"))
  }

  test("Test countFrequencies") {

    var frequencies = SpellChecker.countFrequencies(Seq("a"))
    assertResult(1)(frequencies("a"))
    assertResult(1)(frequencies("b"))
    assertResult(1)(frequencies("c"))
    frequencies = SpellChecker.countFrequencies(Seq("a", "b"))
    assertResult(1)(frequencies("a"))
    assertResult(1)(frequencies("b"))
    assertResult(1)(frequencies("c"))
    frequencies = SpellChecker.countFrequencies(Seq("a", "b", "a", "a", "b", "a", "b", "c"))
    assertResult(4)(frequencies("a"))
    assertResult(3)(frequencies("b"))
    assertResult(1)(frequencies("c"))
    assertResult(1)(frequencies("d"))

  }

  test("Test correct") {

    val checker = new SpellChecker("how now brown cow now")

    assertResult("how")(checker.correct("hw"))
    assertResult("how")(checker.correct("hwo"))

    assertResult("now")(checker.correct("nw"))
    assertResult("now")(checker.correct("naw"))
    assertResult("now")(checker.correct("naow"))

    assertResult("brown")(checker.correct("brow"))
    assertResult("brown")(checker.correct("bron"))
    assertResult("brown")(checker.correct("bronw"))

    assertResult("cow")(checker.correct("cw"))
    assertResult("cow")(checker.correct("cowe"))
    assertResult("cow")(checker.correct("caw"))
    assertResult("cow")(checker.correct("caw"))

    assertResult("now")(checker.correct("cnow"))

    //TODO make this test pass assertResult("how")(checker.correct("woh"))

    assertResult("shenanigans")(checker.correct("shenanigans"))

  }

}

package problems

import scala.collection.mutable
import SpellChecker._

/**
  * Created by simonsaffer on 2015-12-19.
  */
class SpellChecker(dictionary: String) {

  val wordsFrequencies = countFrequencies(dictionary.split(" "))

  def knownWords(words: Set[String]): Set[String] = for (word <- words if (wordsFrequencies.contains(word))) yield word

  def correct(word: String): String = {
    lazy val originalWord: Set[String] = knownWords(Set(word))
    lazy val editDistance1Words: Set[String] = editDistance1(word)
    lazy val knownEditDistance1Words: Set[String] = knownWords(editDistance1Words)
    lazy val editDistance2Words: Set[String] = editDistance1Words.flatMap(w => knownWords(editDistance1(w)))

    lazy val candidates = Seq(originalWord, knownEditDistance1Words, editDistance2Words)

    val setToChooseFrom = candidates.find(!_.isEmpty).getOrElse(Set(word))

    setToChooseFrom.maxBy(wordsFrequencies)
  }

}

object SpellChecker {

  def countFrequencies(features: Seq[String]) = {
    val frequencies = mutable.LinkedHashMap[String, Int]().withDefaultValue(0)
    features.foreach(f => frequencies(f) += 1)
    frequencies.withDefaultValue(1)
  }

  val alphabet = "abcdefghijklmnopqrstuvwxyz"

  def editDistance1(word: String) = {

    val delete = for (i <- 0 until word.length) yield (word.substring(0, i) + word.substring(i+1, word.length))
    val transpose = for (i <- 0 until word.length + 1 if (word.length > i + 1)) yield
      (word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 2, word.length))
    val replace = for (i <- 0 until word.length; a <- alphabet) yield
      (word.substring(0, i) + a + word.substring(i+1, word.length))
    val inserts = for (i <- 0 until word.length+1; a <- alphabet) yield
      (word.substring(0, i) + a + word.substring(i, word.length))

    (delete ++ transpose ++ replace ++ inserts).toSet
  }

  def main(args: Array[String]) {

    val source = scala.io.Source.fromFile("data.txt")
    val lines = try source.mkString finally source.close()

    val checker = new SpellChecker(lines)

    val sc = new java.util.Scanner(System.in)

    val N = sc.nextInt()
    sc.nextLine()

    for(i <- 1 to N) {

      val words: Seq[String] = sc.nextLine().split(" ")

      println(words.map(w => checker.correct(w)).mkString(" "))
    }

  }

}

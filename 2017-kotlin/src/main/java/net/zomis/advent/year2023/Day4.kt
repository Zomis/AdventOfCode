package net.zomis.advent.year2023

import net.zomis.advent.Day
import kotlin.math.pow

data class ScratchCard(val cardNumber: Int, val winning: List<Int>, val myNumbers: List<Int>) {
    val winningSet = winning.toSet()
    val myNumbersSet = myNumbers.toSet()
}

class Day4 : Day<List<ScratchCard>> {
    override fun testCase1(): String = """Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"""

    override fun parse(text: String): List<ScratchCard> {


        return text.lines().map { line ->
            val regex = Regex("Card\\s+(\\d+):([^|]+)+\\|(.+)")
            regex.find(line)!!.let { r ->
                ScratchCard(
                    r.groupValues[1].trim().toInt(),
                    r.groupValues[2].trim().split(Regex("\\s+")).map { it.toInt() },
                    r.groupValues[3].trim().split(Regex("\\s+")).map { it.toInt() }
                )
            }
        }
    }

    override fun part2(input: List<ScratchCard>): Any {
        val cardCounts = IntArray(input.size) { 1 }
        for (i in cardCounts.indices) {
            val count = cardCounts[i]
            val card = input[i]
            for (win in 1..card.correctMatches()) {
                cardCounts[i + win] += count
            }
        }
        return cardCounts.sum()
    }

    override fun part1(input: List<ScratchCard>): Any {
        return input.sumOf { it.points() }
    }
}
private fun ScratchCard.points(): Int {
    val matches = this.correctMatches()
    if (matches == 0) return 0
    return (2.0).pow(matches - 1).toInt()
}

private fun ScratchCard.correctMatches(): Int {
    return this.winningSet.size - this.winningSet.minus(this.myNumbersSet).size
}

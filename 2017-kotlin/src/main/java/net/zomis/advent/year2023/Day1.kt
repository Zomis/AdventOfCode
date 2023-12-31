package net.zomis.advent.year2023

import net.zomis.advent.Day

data class NumberCount(val text: String, val digit: Int) {
    private val active = mutableSetOf<Int>()

    fun reset() {
        active.clear()
    }

    fun checkNext(char: Char): Boolean {
        for (i in (active + 0)) {
            active.remove(i)
            if (text[i] == char) {
                active.add(i + 1)
            }
        }
        return active.remove(text.length)
    }

}

class Day1 : Day<List<String>> {
    private val counts = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    private val numberCounts = counts.mapIndexed { index, s -> NumberCount(s, index) }

    override fun parse(text: String): List<String> = text.split("\n")

    override fun part1(input: List<String>): Any {
        return input.sumOf { firstAndLast(it) }
    }

    override fun part2(input: List<String>): Any {
        return input.sumOf { firstAndLastWithText(it) }
    }

    private fun firstAndLast(it: String): Int {
        val digits = it.filter { it.isDigit() }.map { it.digitToInt() }
        return digits.first() * 10 + digits.last()
    }

    private fun firstAndLastWithText(it: String): Int {
        numberCounts.forEach(NumberCount::reset)
        val digits = mutableListOf<Int>()
        for (ch in it) {
            if (ch.isDigit()) {
                digits.add(ch.digitToInt())
            } else {
                for (nc in numberCounts) {
                    if (nc.checkNext(ch)) {
                        digits.add(nc.digit)
                    }
                }
            }
        }
        return digits.first() * 10 + digits.last()
    }
}
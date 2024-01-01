package net.zomis.advent.year2023

import net.zomis.advent.Day
import net.zomis.advent.common.Direction4
import net.zomis.advent.common.Direction8
import net.zomis.advent.common.Point

class Day3 : Day<List<CharArray>> {
    override fun parse(text: String): List<CharArray> {
        return text.lines().map { it.toCharArray() }
    }

    override fun part2(input: List<CharArray>): Any {
        val symbolPoints = findSymbols(input).filter { input.charAt(it) == '*' }
        val gears = symbolPoints.map {  p ->
            findNumbersAround(input, listOf(p)).map { leftmostDigitPoint(input, it) }.toSet()
        }.filter { it.size == 2 }

        return gears.sumOf {
            readNumbers(input, it).reduce { acc, l -> acc * l }
        }
    }

    override fun part1(input: List<CharArray>): Any {
        val symbolPoints = findSymbols(input)
        val numberPositions = findNumbersAround(input, symbolPoints)
        val readNumbers = readNumbers(input, numberPositions)
        return readNumbers.sum()
    }

    private fun readNumbers(input: List<CharArray>, numberPositions: Set<Point>): List<Long> {
        val numberStarts = numberPositions.map {
            leftmostDigitPoint(input, it)
        }.toSet()
        return numberStarts.map {
            var pos = it
            var number = input.charAt(it)!!.digitToInt().toLong()
            while (input.charAt(pos + Direction4.RIGHT.delta())?.isDigit() == true) {
                pos += Direction4.RIGHT.delta()
                number *= 10
                number += input.charAt(pos)!!.digitToInt().toLong()
            }
            number
        }
    }

    private fun leftmostDigitPoint(input: List<CharArray>, point: Point): Point {
        var pos = point
        while (input.charAt(pos + Direction4.LEFT.delta())?.isDigit() == true) pos += Direction4.LEFT.delta()
        return pos
    }

    private fun findNumbersAround(input: List<CharArray>, symbolPoints: List<Point>): Set<Point> {
        val dirs = Direction8.values()
        val result = mutableSetOf<Point>()
        for (p in symbolPoints) {
            for (dir in dirs) {
                val target = p + dir.delta()
                if (input.charAt(target)?.isDigit() == true) result.add(target)
            }
        }
        return result
    }

    private fun findSymbols(input: List<CharArray>): List<Point> {
        val points = mutableListOf<Point>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                val ch = input[y][x]
                if (!ch.isDigit() && ch != '.') points.add(Point(x, y))
            }
        }
        return points
    }

    override fun testCase1(): String = """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...${'$'}.*....
.664.598.."""

}

private fun List<CharArray>.charAt(target: Point): Char? = this.getOrNull(target.y)?.getOrNull(target.x)

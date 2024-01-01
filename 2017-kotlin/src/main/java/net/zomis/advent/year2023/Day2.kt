package net.zomis.advent.year2023

import net.zomis.advent.Day
import substringBetween
import kotlin.math.max

class CubeGame(
    val id: Int,
    val cubeReveals: List<Map<String, Int>>
) {
    fun hasCubes(limit: Map<String, Int>): Boolean {
        // no cube reveal may be more than any limit
        return cubeReveals.all { revealMap ->
            revealMap.all { reveal ->
                val cubeLimit = limit[reveal.key]
                cubeLimit != null && reveal.value <= cubeLimit
            }
        }
    }

    fun maxCubesUsed(): Map<String, Int> {
        return cubeReveals.fold(emptyMap<String, Int>()) { a, b ->
            val keys = a.keys + b.keys
            keys.associateWith { max(a[it] ?: 0, b[it] ?: 0) }
        }
    }
}

class Day2 : Day<List<CubeGame>> {
    override fun parse(text: String): List<CubeGame> {
        // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        return text.split("\n").map { it.trim() }.map {
            val gameId = it.substringBetween("Game ", ":")
            val reveals = it.substringAfter(": ").split("; ").map {  reveal ->
                reveal.split(", ").associate {  numColorPair ->
                    numColorPair.substringAfter(" ") to numColorPair.substringBefore(" ").toInt()
                }
            }
            CubeGame(gameId.toInt(), reveals)
        }
    }

    override fun testCase1(): String = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"""

    override fun part2(input: List<CubeGame>): Any {
        return input.sumOf {
            it.maxCubesUsed().values.reduce { acc, i -> acc * i }
        }
    }

    override fun part1(input: List<CubeGame>): Any {
        val limit = mapOf("red" to 12, "green" to 13, "blue" to 14)
        return input.filter { it.hasCubes(limit) }.sumOf { it.id }
    }
}
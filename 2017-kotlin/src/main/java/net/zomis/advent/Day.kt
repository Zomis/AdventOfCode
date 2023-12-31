package net.zomis.advent

interface Day<T> {
    fun parse(text: String): T
    fun part1(input: T): Any
    fun part2(input: T): Any
}
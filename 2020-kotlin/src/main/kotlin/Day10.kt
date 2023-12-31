package net.zomis.aoc.y2020

object Day10 {

    val day = Days.day<List<Long>>(this) {
        input {
            map { it.toLong() }.toList()
        }
        part1 {
            val diffs = input.sorted().zipWithNext().map { it.second - it.first }.groupingBy { it }.eachCount().mapValues { it.value + 1 }
            diffs.getValue(1L) * diffs.getValue(3L)
        }
        part2 {
            combinationsFor(0L, input.toSet())
        }
    }

    val assoc = mutableMapOf<Long, Long>()

    fun combinationsFor(value: Long, numbers: Set<Long>): Long {
        val known = assoc.get(value)
        if (known != null) return known

        val matches = (1..3).map { value + it }.filter { numbers.contains(it) }

        val learned = when {
            matches.isEmpty() -> 1L
            else -> matches.fold(0L) { acc, l -> acc + combinationsFor(l, numbers) }
        }
        assoc[value] = learned
        return learned
    }

}

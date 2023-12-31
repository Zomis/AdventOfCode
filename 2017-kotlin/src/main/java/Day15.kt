import net.zomis.advent.Day

class Day15: Day<Pair<Int, Int>> {
    override fun parse(text: String): Pair<Int, Int> {
        val arr = text.lines().map { it.substringAfter("starts with ").toInt() }.toIntArray()
        return Pair(arr[0], arr[1])
    }

    fun next(input: Pair<Long, Long>, generatorCondition: Pair<(Int) -> Boolean, (Int) -> Boolean>): Pair<Long, Long> {
        var a = input.first
        do {
            a = a * 16807 % 2147483647
        } while (!generatorCondition.first.invoke(a.toInt()))

        var b = input.second
        do {
            b = b * 48271 % 2147483647
        } while (!generatorCondition.second.invoke(b.toInt()))
        return Pair(a, b)
    }

    private val matchAND = (1.shl(16) - 1).toLong()
    private val zero = 0.toLong()
    fun match(input: Pair<Long, Long>): Boolean {
        val a = input.first.and(matchAND)
        val b = input.second.and(matchAND)
        return a.xor(b) == zero
    }

    override fun part1(input: Pair<Int, Int>): Any {
        val range = 0 until 40_000_000
        return countMatches(input, range, Pair({ _ -> true }, { _ -> true }))
    }

    private fun countMatches(input: Pair<Int, Int>, range: IntRange, generatorCondition: Pair<(Int) -> Boolean, (Int) -> Boolean>): Any {
        var count = 0
        var values = Pair(input.first.toLong(), input.second.toLong())
        for (i in range) {
            values = next(values, generatorCondition)
            if (match(values)) {
                count++
            }
        }
        return count
    }

    override fun part2(input: Pair<Int, Int>): Any {
        return countMatches(input, 0 until 5_000_000, Pair({ a -> a % 4 == 0 }, { b -> b % 8 == 0 }))
    }

}
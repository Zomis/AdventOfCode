import net.zomis.advent.Day
import java.util.*

class Day6: Day<IntArray> {
    override fun parse(text: String): IntArray {
        return text.split(Regex("\\s")).map { it.trim().toInt() }.toIntArray()
    }

    override fun part1(input: IntArray): Any {
        return solve(input) { count, _ -> count }
    }

    fun solve(input: IntArray, finisher: (Int, Int) -> Int): Any {
        var count = 0
        var seen = HashMap<String, Int>()
        while (true) {
            val serialized = input.map { it.toString() }.joinToString()
            if (seen.containsKey(serialized)) {
                return finisher(count, seen.get(serialized)!!)
            }
            seen.put(serialized, count)
            val chosenBank = input.withIndex().maxBy { it.value }!!
            val index = chosenBank.index
            val value = chosenBank.value
            input[index] = 0
            for (i in 1..value) {
                val modifyIndex = (index + i) % input.size
                input[modifyIndex]++
            }
            count++
        }
    }

    override fun part2(input: IntArray): Any {
        return solve(input) { count, previous -> count - previous }
    }

}
import net.zomis.advent.Day

class Day5: Day<IntArray> {

    override fun parse(text: String): IntArray {
        return text.split("\n").map { it.trim() }.filter { !it.isEmpty() }.map { it.toInt() }.toIntArray()
    }

    override fun part1(input: IntArray): Any {
        return solve(input, { _, _ -> 1 })
    }

    fun solve(input: IntArray, valueModification: (IntArray, Int) -> Int): Int {
        var idx = 0
        var count = 0
        while (true) {
            val oldIdx = idx
            val value = input[oldIdx]
            count++
            idx += value
            if (idx < 0 || idx >= input.size) {
                break
            }
            input[oldIdx] += valueModification.invoke(input, value)
        }
        return count
    }

    override fun part2(input: IntArray): Any {
        return solve(input, { _, v -> if (v >= 3) -1 else 1 })
    }


}
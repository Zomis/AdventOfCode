import net.zomis.advent.Day

class Day1: Day<IntArray> {
    override fun parse(text: String): IntArray {
        return text.toCharArray().map { Character.digit(it, 10) }.toIntArray()
    }

    override fun part1(input: IntArray): Any {
        return perform(input, { arr, idx -> arr[(idx + 1) % arr.size] })
    }

    override fun part2(input: IntArray): Any {
        return perform(input, { arr, idx -> arr[(idx + arr.size / 2) % arr.size] })
    }

    fun perform(numbers: IntArray, otherOperation: (IntArray, Int) -> Int): Int {
        return numbers.foldIndexed(0, { index, current: Int, value ->
            val other: Int = otherOperation.invoke(numbers, index)
            val add: Int = if (other == value) value else 0
            current + add
        })
    }

}

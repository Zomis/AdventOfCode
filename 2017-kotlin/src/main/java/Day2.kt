import net.zomis.advent.Day
import java.util.regex.Pattern

class Day2: Day<Array<IntArray>> {
    override fun part1(input: Array<IntArray>): Any {
        return perform(input, { highLowDiff(it) })
    }

    override fun part2(input: Array<IntArray>): Any {
        return perform(input, { divisible(it) })
    }

    private fun divisible(numbers: IntArray): Int {
        numbers.forEach { a ->
            numbers.forEach { b ->
                if (a != b && a % b == 0) {
                    return a / b
                }
            }
        }
        return 0
    }

    override fun parse(text: String): Array<IntArray> {
        return text.split('\n').map { s -> s.trim().split(Pattern.compile("\\s+"))
                .map { it.trim().toInt() }.toIntArray() }.toTypedArray()
    }

    fun highLowDiff(numbers: IntArray): Int {
        return numbers.max()!! - numbers.min()!!
    }

    fun perform(values: Array<IntArray>, operation: (IntArray) -> Int): Int {
        return values.fold(0, { sum, next -> sum + operation.invoke(next) })
    }

}

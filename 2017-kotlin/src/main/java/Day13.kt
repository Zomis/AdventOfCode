import net.zomis.advent.Day

class Day13: Day<Map<Int, Int>> {
    override fun parse(text: String): Map<Int, Int> {
        return text.lineSequence().associateBy({ it.substringBefore(": ").toInt() }, { it.substringAfter(": ").toInt() })
    }

    fun caught(barrier: Map.Entry<Int, Int>, delay: Int): Boolean {
        val bounceRate = barrier.value * 2 - 2
        val caught = (barrier.key + delay) % bounceRate == 0
        // println("$barrier bounce $bounceRate result in $caught")
        return caught
    }

    fun severity(input: Map<Int, Int>, delay: Int): Int {
        val result = input.asSequence().filter { caught(it, delay) }.sumBy { it.key * it.value }
        // println("Severity of $delay is $result")
        return result
    }

    override fun part1(input: Map<Int, Int>): Any {
        return severity(input, 0)
    }

    override fun part2(input: Map<Int, Int>): Any {
        return generateSequence({ 0 }, { it + 1 }).filter { delay -> input.asSequence().none { caught(it, delay) }}.first()
    }

}
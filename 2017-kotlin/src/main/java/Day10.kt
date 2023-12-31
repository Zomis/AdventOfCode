import net.zomis.advent.Day

class Day10: Day<IntArray> {
    override fun parse(text: String): IntArray {
        return text.split(",").map { it.toInt() }.toIntArray()
    }

    override fun part1(input: IntArray): Any {
        val array = (0..255).toMutableList()
        val result = round(0, 0, array, input)
        return result.values[0] * result.values[1]
    }

    class RoundResult(val currentPosition: Int, val skip: Int, val values: MutableList<Int>)

    private fun round(startPosition: Int, skipStart: Int, values: MutableList<Int>, lengths: IntArray): RoundResult {
        var currentPosition = startPosition
        var skip = skipStart
        for (len in lengths) {
            reverse(values, currentPosition, len)
            currentPosition += len + skip
            skip++
        }
        return RoundResult(currentPosition, skip, values)
    }

    private fun reverse(array: MutableList<Int>, currentPosition: Int, len: Int) {
        for (i in 0 until len/2) {
            /*
            * curr i  len   A B
            * 0    0   2    0 1
            * 0    1   2    ---
            * 0    2   23   2  20
            * 2    2   8    4
            * */

            val switchIndexA = (currentPosition + i) % array.size
            val switchIndexB = (currentPosition + len - 1 - i ) % array.size
            val temp = array[switchIndexA]
            array[switchIndexA] = array[switchIndexB]
            array[switchIndexB] = temp
        }
    }

    fun knotHash(input: String): String {
        var str = input.toCharArray().map { it.toInt() } + arrayOf(17, 31, 73, 47, 23)
        val values = (0..255).toMutableList()
        var currentPosition = 0
        var skip = 0
        for (i in 1..64) {
            val result = round(currentPosition, skip, values, str.toIntArray())
            currentPosition = result.currentPosition
            skip = result.skip
        }

        return (0 until 16).map {
            val subList = values.subList(it * 16, it * 16 + 16)
            val result = Integer.toString(subList.reduce({ a, b -> a xor b}), 16)
            if (result.length == 1) "0" + result else result
        }.joinToString("")
    }

    override fun part2(input: IntArray): Any {
        val str = input.map { it.toString() }.joinToString(",")
        return knotHash(str)
    }

}
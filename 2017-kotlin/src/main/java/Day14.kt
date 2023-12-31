import net.zomis.advent.Day

class Day14: Day<String> {
    override fun parse(text: String): String {
        return text
    }

    private val range = 0..127

    fun hashes(input: String): List<String> {
        val operation = Day10::knotHash
        val day = Day10()
        return range.map { "$input-$it" }.map { operation.invoke(day, it) }
    }

    override fun part1(input: String): Any {
        val hashes = hashes(input)
        return range.sumBy { y -> range.count { x -> bitset(hashes, x, y) }}
        //return hashes.sumBy { it.toCharArray().sumBy { Integer.bitCount(Integer.parseInt(it.toString(), 16)) }}
    }

    fun bitset(hashes: List<String>, x: Int, y: Int): Boolean {
        val hash = hashes[y]
        val char = hash[x / 4]
        val hexValue = Integer.parseInt(char.toString(), 16)
        val bit = 3 - x % 4
        val andTwo = 1.shl(bit)
        val result = hexValue.and(andTwo) == andTwo
        // println("hash $hash char $char hex $hexValue bit $bit andTwo $andTwo result $result")
        return result
        // return Integer.bitCount(Integer.parseInt(it.toString(), 16))
    }

    fun floodFill(hashes: List<String>, map: Array<BooleanArray>, seen: MutableSet<Int>, x: Int, y: Int): Int {
        if (x < 0 || x >= 128 || y < 0 || y >= 128) {
            return 0
        }
        if (!bitset(hashes, x, y)) {
            return 0
        }
        val key = y * 128 + x
        if (seen.add(key)) {
            floodFill(hashes, map, seen, x - 1, y)
            floodFill(hashes, map, seen, x, y - 1)
            floodFill(hashes, map, seen, x + 1, y)
            floodFill(hashes, map, seen, x, y + 1)
            return 1
        }
        return 0
    }

    override fun part2(input: String): Any {
        val hashes = hashes(input)
        val map = range.map { y -> range.map { x -> bitset(hashes, x, y) }.toBooleanArray() }.toTypedArray()
        val seen: MutableSet<Int> = mutableSetOf()

        return range.sumBy { y -> range.sumBy { x -> floodFill(hashes, map, seen, x, y) } }
    }

}
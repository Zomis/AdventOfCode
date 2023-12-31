import net.zomis.advent.Day

class Day9: Day<CharArray> {
    override fun parse(text: String): CharArray {
        return text.toCharArray()
    }

    override fun part1(input: CharArray): Any {
        return process(input).score
    }

    class ProcessResult(val score: Int, val garbageCount: Int)

    fun process(input: CharArray): ProcessResult {
        var ignoreNext = false
        var insideGarbage = false
        var insideGroups = 0
        var score = 0
        var garbageCount = 0
        for (ch in input) {
            if (ignoreNext) {
                ignoreNext = false
                continue
            }
            if (ch == '!') {
                ignoreNext = true
                continue
            }
            if (insideGarbage) {
                when (ch) {
                    '!' -> { ignoreNext = true }
                    '>' -> { insideGarbage = false }
                    else -> garbageCount++
                }
            } else {
                when (ch) {
                    '<' -> { insideGarbage = true }
                    '{' -> insideGroups++
                    '}' -> {
                        score += insideGroups
                        insideGroups--
                    }
                }
            }
        }
        return ProcessResult(score, garbageCount)
    }

    override fun part2(input: CharArray): Int {
        return process(input).garbageCount
    }

}
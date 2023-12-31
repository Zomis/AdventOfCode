import net.zomis.advent.Day

class Day23: Day<List<Day18.Instruction18>> {
    val day18 = Day18()
    override fun parse(text: String): List<Day18.Instruction18> {
        return day18.parse(text)
    }

    override fun part1(input: List<Day18.Instruction18>): Any {
        val prog = Day18.Program(0, input, mutableMapOf(), {_, _ -> 0}, {_, _ -> 0})
        var count = 0
        while (!prog.stopped) {
            val result = prog.runStep()
            if (result.first?.type == "mul") {
                count++
            }
        }
        return count
    }

    override fun part2(input: List<Day18.Instruction18>): Any {
        var b = 79
        var c = b
        b *= 100
        b -= -100000
        c = b
        c -= -17000
        var f = 1
        var g = 0
        var d = 0
        var h = 0
        do {
            f = 1
            d = 2
            do {
                if (b % d == 0) {
                    f = 0
                }
                d++
                g = d
                g -= b
            } while (g != 0) // jnz g -13
            if (f == 0) {
                h++
            }
            g = b
            g -= c
            if (g == 0) {
                return h //     jnz 1 3 // Exit
            }
            b += 17
        } while (true) // jnz 1 -23
    }

}
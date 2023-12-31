import net.zomis.advent.Day
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

fun MutableMap<String, Long>.numberOrMapValue(value: String): Long {
    val number = Regex("-?\\d+").matches(value)
    return if (number) value.toLong() else getOrElse(value, { 0 })
}

class Day18: Day<List<Day18.Instruction18>> {
    val SOUND_VALUE = "SOUND_VALUE"

    override fun parse(text: String): List<Instruction18> {
        return text.lines()
            .map { it.split(" ") }
            .map { Instruction18(it[0], it[1], it.getOrElse(2, {_ -> ""})) }
    }

    override fun part1(input: List<Instruction18>): Any {
        val vals = mutableMapOf<String, Long>()
        val prog = Program(0, input, vals, { instruction, program -> program.values[SOUND_VALUE] = program.values.numberOrMapValue(instruction.first); 1},
        { instruction, program -> if (program.values.getOrElse(instruction.first, {0}) != 0.toLong()) program.values[instruction.first] = program.values.getOrElse(SOUND_VALUE, {0}); 1})

        while (prog.instruction in 0..input.size) {
            val result = prog.runStep()
            if (result.first?.type == "rcv" && vals[SOUND_VALUE] ?: 0 != 0.toLong()) {
                return vals[SOUND_VALUE] ?: 0
            }
        }

        return vals[SOUND_VALUE] ?: 0
    }

    class Program(val progId: Int, val instructions: List<Instruction18>,
            val values: MutableMap<String, Long>, val sndAction: (Instruction18, Program) -> Int,
              val rcvAction: (Instruction18, Program) -> Int) {
        var instruction = 0
        var stopped = false

        fun runStep(): Pair<Instruction18?, Int> {
            if (instruction in 0 until instructions.size) {
                val ins = instructions[instruction]
                val result = perform(values, ins)
                instruction += result
                return Pair(ins, result)
            }
            this.stopped = true
            return Pair(null, 0)
        }

        private fun perform(values: MutableMap<String, Long>, instruction: Instruction18): Int {
            when (instruction.type) {
                "set" -> values[instruction.first] = values.numberOrMapValue(instruction.second)
                "add" -> {
                    val old = values.getOrElse(instruction.first, {0})
                    val b = values.numberOrMapValue(instruction.second)
                    values[instruction.first] = old + b
                    if (b > 0 && old + b < old) {
                        throw IllegalArgumentException("Addition resulted in less: $old + $b: $instruction")
                    }
                }
                "mul" -> {
                    val old = values.getOrElse(instruction.first, {0})
                    val b = values.numberOrMapValue(instruction.second)
                    values[instruction.first] = old * b
                    if (b > 0 && values[instruction.first]!! < old) {
                        throw IllegalArgumentException("Multiply resulted in less: $old * $b: $instruction")
                    }
                }
                "mod" -> {
                    val a = values.getOrElse(instruction.first, {0})
                    val b = values.numberOrMapValue(instruction.second)
                    if (a < 0) {
                        throw IllegalArgumentException("Modulo negative value: $a % $b: $instruction")
                    }
                    values[instruction.first] = a % b
                }
                "rcv" -> return rcvAction(instruction, this)
                "snd" -> return sndAction(instruction, this)
                "jgz" -> {
                    if (values.numberOrMapValue(instruction.first) > 0.toLong()) {
                        val value: Long = values.numberOrMapValue(instruction.second)
                        // println("Jump $value")
                        return value.toInt()
                    }
                }
                "jnz" -> { // Day23
                    if (values.numberOrMapValue(instruction.first) != 0.toLong()) {
                        val value: Long = values.numberOrMapValue(instruction.second)
                        // println("Jump $value")
                        return value.toInt()
                    }
                }
                "sub" -> { // Day23
                    val a = values.getOrElse(instruction.first, {0})
                    val b = values.numberOrMapValue(instruction.second)
                    values[instruction.first] = a - b
                }
                else -> throw IllegalArgumentException("Unknown instruction: $instruction")
            }
            return 1
        }
    }

    private fun snd(queue: BlockingQueue<Long>): (Instruction18, Program) -> Int {
        return { instruction, program ->
            val value = program.values.numberOrMapValue(instruction.first)
            queue.add(value)
            1
        }
    }

    private fun rcv(queue: BlockingQueue<Long>): (Instruction18, Program) -> Int {
        return lit@ { instruction, program ->
            if (queue.isEmpty()) {
                if (!program.stopped) {
//                    println("Stopping ${program.progId}")
                }
                program.stopped = true
                return@lit 0
            }
            program.stopped = false
            val taken = queue.take()
            program.values[instruction.first] = taken
            1
        }
    }

    override fun part2(input: List<Instruction18>): Any {
        val queue1 = LinkedBlockingQueue<Long>()
        val queue2 = LinkedBlockingQueue<Long>()
        val prog1 = Program(0, input, mutableMapOf(Pair("p", 0.toLong())), snd(queue2), rcv(queue1))
        val prog2 = Program(1, input, mutableMapOf(Pair("p", 1.toLong())), snd(queue1), rcv(queue2))

        var count = 0
        while (count == 0 || queue1.isNotEmpty() || queue2.isNotEmpty()) {
            do {
                prog1.runStep()
            } while (!prog1.stopped)

            do {
                val inst = prog2.runStep()
                if (inst.first?.type == "snd") {
                    count++
                    if (count >= 200000) {
                        throw IllegalStateException("Count is too high! Something is wrong!")
                    }
                }
            } while (!prog2.stopped)
        }
        return count
    }

    data class Instruction18(val type: String, val first: String, val second: String)



}
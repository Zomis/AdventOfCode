import net.zomis.advent.Day

fun String.substringBetween(after: String, before: String): String {
    return this.substringAfter(after).substringBefore(before)
}

class Day25: Day<Day25.TuringInstructions> {
    override fun parse(text: String): TuringInstructions {
        val lines = text.lines()
        val firstLine = lines[0]
        val secondLine = lines[1]
        val startingState = firstLine.substringAfter("state ").substringBefore(".")
        val checksumSteps = secondLine.substringAfter("after ").substringBefore(" steps.").toInt()
        val states = mutableListOf<TuringState>()

        var currentLine = 3
        while (currentLine < lines.size) {
            val inState = lines[currentLine].substringAfter("In state ").substringBefore(":")
            val instruction0 = readCondition(lines, currentLine + 1)
            val instruction1 = readCondition(lines, currentLine + 5)
            states.add(TuringState(inState, listOf(instruction0, instruction1)))
            currentLine += 10
        }
        return TuringInstructions(startingState, checksumSteps, states)
    }

    private fun readCondition(lines: List<String>, startLine: Int): TuringInstruction {
        val currentValueCondition = lines[startLine].substringBetween("value is ", ":").toInt()
        val writeValue = lines[startLine + 1].substringBetween("Write the value ", ".")
        val move = lines[startLine + 2].substringBetween("Move one slot to the ", ".")
        val nextState = lines[startLine + 3].substringBetween("Continue with state ", ".")

//        val instructionStr = (startLine..startLine+3).map { lines[it].trim() }.joinToString(" / ")
        return TuringInstruction(currentValueCondition, writeValue.toInt(), if (move == "right") 1 else -1, nextState)
    }

    override fun part1(input: TuringInstructions): Any {
        val tape = mutableMapOf<Int, Int>()
        var state = input.startingState
        var cursor = 0
        for (i in 1..input.diagnosticChecksumSteps) {
            val result = perform(tape, state, cursor, input)
            state = result.first
            cursor = result.second
        }
        return tape.values.count { it == 1 }
    }

    private fun perform(tape: MutableMap<Int, Int>, state: String, cursor: Int, input: TuringInstructions): Pair<String, Int> {
        val currentValue = tape[cursor] ?: 0
        val instruction = input.states.find { it.state == state }!!.instructions.find { it.currentValueCondition == currentValue }!!
        tape[cursor] = instruction.writeValue
        return Pair(instruction.nextState, cursor + instruction.move)
    }

    override fun part2(input: TuringInstructions): Any {
        return 0 // This fits quite well actually, because there is no part2. We're finished!
    }

    data class TuringInstruction(val currentValueCondition: Int, val writeValue: Int, val move: Int, val nextState: String)
    data class TuringState(val state: String, val instructions: List<TuringInstruction>)
    data class TuringInstructions(val startingState: String, val diagnosticChecksumSteps: Int, val states: List<TuringState>)
}
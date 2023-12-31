import net.zomis.advent.Day
import java.util.*

class Day8: Day<List<Day8.Instruction>> {
    class Instruction(val variable: String, val increase: Boolean, val value: Int,
              val conditionRegister: String, val compareOp: Operation,
                      val compareTo: Int) {

        fun process(map: MutableMap<String, Int>) {
            if (compareOp.operation(map.get(conditionRegister) ?: 0, compareTo)) {
                val changeWith = if (increase) value else -value
                map.merge(variable, changeWith, { old, new -> old + new })
            }
            //                 map.merge(variable, value, { old, new -> old + new * if (increase) 1 else -1 })
        }
    }

    enum class Operation(val text: String, val operation: (Int, Int) -> Boolean) {
        EQUAL("==", { a, b -> a == b }),
        NOT_EQUAL("!=", { a, b -> a != b }),
        LESS_THAN("<", { a, b -> a < b }),
        LESS_THAN_OR_EQUALS("<=", { a, b -> a <= b }),
        GREATER_THAN(">", { a, b -> a > b }),
        GREATER_THAN_OR_EQUALS(">=", { a, b -> a >= b }),
    }

    override fun parse(text: String): List<Instruction> {
        return text.split("\n").map {
            // b dec -195 if ty >= -8
            val params = it.split(" ").map { it.trim() }
            val operation = Operation.values().find { it.text.equals(params[5]) } ?:
                throw IllegalArgumentException("No operation for " + params[5])
            Instruction(params[0], params[1] == "inc", params[2].toInt(), params[4],
                operation, params[6].toInt())
        }
    }

    override fun part1(input: List<Instruction>): Any {
        val register = HashMap<String, Int>()
        input.forEach { it.process(register) }
        return register.maxBy { it.value }!!.value
    }

    override fun part2(input: List<Instruction>): Any {
        val register = HashMap<String, Int>()
        var highest = 0
        for (instruction in input) {
            instruction.process(register)
            highest = Math.max(highest, register.maxBy { it.value }!!.value)
        }
        return highest
    }

}
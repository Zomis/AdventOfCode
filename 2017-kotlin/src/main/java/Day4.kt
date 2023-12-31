import net.zomis.advent.Day
import java.util.stream.Collectors

class Day4: Day<String> {
    override fun parse(text: String): String {
        return text
    }

    override fun part1(input: String): Any {
        return input.split("\n").filter { !it.isEmpty() }.count { valid(it) }
    }

    private fun valid(input: String): Boolean {
        val words = input.split(" ").map { it.trim() }.filter { !it.isEmpty() }
        val nonDupWords = words.toSet()
        val valid = words.size == nonDupWords.size
        return valid
    }

    private fun valid2(input: String): Boolean {
        val words = input.split(" ").map { it.trim() }.filter { !it.isEmpty() }.map {
            it.chars().sorted().mapToObj { it.toString() }.collect(Collectors.joining())
        }
        val nonDupWords = words.toSet()
        val valid = words.size == nonDupWords.size
        return valid
    }

    override fun part2(input: String): Any {
        return input.split("\n").filter { !it.isEmpty() }.count { valid2(it) }
    }

}
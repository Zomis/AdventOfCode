import net.zomis.advent.Day
import java.util.*

typealias BridgePiece = Pair<Int, Int>

class Day24: Day<List<BridgePiece>> {
    override fun parse(text: String): List<BridgePiece> {
        return text.lines().map { it.split("/") }.map {
            val a = it[0].toInt()
            val b = it[1].toInt()
            Pair(a, b)
            // Pair(Math.min(a, b), Math.max(a, b))
        }
    }

    override fun part1(input: List<BridgePiece>): Any {
        return solve(0, 0, 0, input, Comparator({ a, b -> a.strength - b.strength })).strength
    }

    data class BridgeResult(val length: Int, val strength: Int)
    private fun solve(current: Int, length: Int, strength: Int, remaining: List<BridgePiece>, comparator: Comparator<BridgeResult>): BridgeResult {
        val options = remaining.filter { it.first == current || it.second == current }
        if (options.isEmpty()) {
            return BridgeResult(length, strength)
        }
//        println("Used $used has options $options current $current sum $sum")

        return options.map {
            val next = if (it.first == current) it.second else it.first
            val nextList = remaining.toMutableList()
            nextList.remove(it)
//            val usedNext = used.toMutableList()
//            usedNext.add(it)
            solve(next, length + 1,strength + it.first + it.second, nextList, comparator)
        }.maxWith(comparator)!!
    }

    override fun part2(input: List<BridgePiece>): Any {
        return solve(0, 0, 0, input, Comparator({ a, b ->
            val lengthDiff = a.length - b.length
            if (lengthDiff != 0) lengthDiff else a.strength - b.strength
        })).strength
    }

}
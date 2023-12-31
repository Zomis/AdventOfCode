import net.zomis.advent.Day
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class DaysTest {

    @TestFactory
    fun tests(): List<DynamicTest> {
        return arrayOf(
            testPartOne(Day1(), "1122", 3),
            testPartOne(Day1(), "1111", 4),
            testPartOne(Day1(), "1234", 0),
            testPartOne(Day1(), "91212129", 9),

            testPartOneOnly(Day2(), arrayOf(
                    intArrayOf(5, 1, 9, 5),
                    intArrayOf(7, 5, 3),
                    intArrayOf(2, 4, 6, 8)
            ), 18),
            testPartTwoOnly(Day2(), arrayOf(
                    intArrayOf(5, 9, 2, 8),
                    intArrayOf(9, 4, 7, 3),
                    intArrayOf(3, 8, 6, 5)
            ), 9),
            testPartOne(Day3(), "1", 0),
            testPartOne(Day3(), "12", 3),
            testPartOne(Day3(), "23", 2),
            testPartOne(Day3(), "14", 3),
            testPartOne(Day3(), "15", 2),
            testPartOne(Day3(), "22", 3),
            testPartOne(Day3(), "18", 3),
            testPartOne(Day3(), "19", 2),
            testPartOne(Day3(), "27", 4),
            testPartOne(Day3(), "28", 3),
            testPartOne(Day3(), "26", 5),
            testPartOne(Day3(), "25", 4),
            testPartOne(Day3(), "49", 6),
            testPartOne(Day3(), "50", 7),
            testPartOne(Day3(), "1024", 31),

            testPartTwo(Day3(), "140", 142),
            testPartTwo(Day3(), "300", 304),
            testPartTwo(Day3(), "747", 806),
            testPartTwo(Day3(), "748", 806),
            testPartTwo(Day3(), "60", 122),
            testPartTwo(Day3(), "342", 351),

            testPartOne(Day4(), "aa bb cc dd ee\naa bb cc dd aa\naa bb cc dd aaa", 2),
            testPartOne(Day22(), "..#\n#..\n...", 5587),
            testPartTwoOnly(Day5(), intArrayOf(0, 3, 0, 1, -3), 10),
                DynamicTest.dynamicTest("Day 11 steps", { assertEquals(13, Day11().path(20, 6)) }),
                DynamicTest.dynamicTest("Day 11 steps", { assertEquals(26, Day11().path(40, 12)) }),
                DynamicTest.dynamicTest("Day 11 steps", { assertEquals(52, Day11().path(80, 24)) }),
                DynamicTest.dynamicTest("Day 11 steps", { assertEquals(104, Day11().path(160, 48)) }),
                DynamicTest.dynamicTest("Day 11 steps", { assertEquals(208, Day11().path(320, 96)) }),
                DynamicTest.dynamicTest("Day 11 steps", { assertEquals(416, Day11().path(640, 192)) }),
                DynamicTest.dynamicTest("Day 11 steps", { assertEquals(832, Day11().path(1280, 384)) }),

            DynamicTest.dynamicTest("End", {})
        ).toList()
    }

    private fun <T> testPartOneOnly(day: Day<T>, input: T, result: Any): DynamicTest {
        val dayName = day.javaClass.name
        return DynamicTest.dynamicTest("Test Part1 $dayName with input $input", {
            assertEquals(result, day.part1(input), "Mismatch for $dayName with input $input")
        })
    }

    private fun <T> testPartTwoOnly(day: Day<T>, input: T, result: Any): DynamicTest {
        val dayName = day.javaClass.name
        return DynamicTest.dynamicTest("Test Part2 $dayName with input $input", {
            assertEquals(result, day.part2(input), "Mismatch for $dayName with input $input")
        })
    }

    private fun <T> testPartOne(day: Day<T>, input: String, result: Any): DynamicTest {
        val dayName = day.javaClass.name
        return DynamicTest.dynamicTest("Test Part1 $dayName with input $input", {
            assertEquals(result, day.part1(day.parse(input)), "Mismatch for $dayName with input $input")
        })
    }

    private fun <T> testPartTwo(day: Day<T>, input: String, result: Any): DynamicTest {
        val dayName = day.javaClass.name
        return DynamicTest.dynamicTest("Test Part2 $dayName with input $input", {
            assertEquals(result, day.part2(day.parse(input)), "Mismatch for $dayName with input $input")
        })
    }

}
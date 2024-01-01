import net.zomis.advent.Day

fun main(args: Array<String>) {
    val year = 2023
    val range: IntProgression = 1..25
    val packageName = if (year == 2017) "" else "net.zomis.advent.year$year."
    for (day in range.reversed()) {
        val d: Day<Any>
        try {
            val cl = Day::class.java.classLoader.loadClass(packageName + "Day$day")
            println(cl.simpleName)
            d = cl.newInstance() as Day<Any>
        } catch (ex: ClassNotFoundException) {
            continue
        }
        println("Load $day")

        if (d.testCase1() != null) {
            println("Test Part1: " + d.part1(d.parse(d.testCase1()!!)))
        }

        val url = d.javaClass.classLoader.getResource(packageName.replace('.', '/') + "day$day")
        val input = url.readText()
        val parsed1 = d.parse(input)
        println("Part1: " + d.part1(parsed1))

        if (d.testCase2() != null) {
            println("Test Part2: " + d.part2(d.parse(d.testCase2()!!)))
        }

        val parsed2 = d.parse(input)
        println("Part2: " + d.part2(parsed2))
        println()
    }

}
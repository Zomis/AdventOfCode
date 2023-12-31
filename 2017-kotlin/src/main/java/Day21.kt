import net.zomis.advent.Day

fun Array<BooleanArray>.strRep(): String {
    return map { it.map { if (it) '#' else '.' }.joinToString("") }.joinToString("/")
}

fun Array<BooleanArray>.countOn(): Int {
    return sumBy { it.count { it } }
}

fun Array<BooleanArray>.matches(other: Array<BooleanArray>): Boolean {
    val range = 0 until size
    return range.all { y ->
        range.all { x ->
            this[y][x] == other[y][x]
        }
    }
}

class Day21: Day<Collection<Day21.Recipe>> {
    override fun parse(text: String): Collection<Recipe> {
        return text.lines().map { createRecipe(it) }
    }

    private fun createRecipe(it: String): Recipe {
        val patternString = it.substringBefore(" => ")
        val replaceString = it.substringAfter(" => ")
        val textTo2D: (String) -> Array<BooleanArray> = { str ->
            str.split("/").map { it.toCharArray().map { it == '#' }.toBooleanArray() }.toTypedArray()
        }
        val pattern = textTo2D(patternString)
        val replacement = textTo2D(replaceString)
        return Recipe(pattern, replacement)
    }

    override fun part1(input: Collection<Recipe>): Any {
        val inputs = input.flatMap { it.variations() }
        var section = Section(arrayOf(booleanArrayOf(false, true, false), booleanArrayOf(false, false, true), booleanArrayOf(true, true, true)))
        for (i in 1..5) {
            section = section.splitEnhanceAndCombine(inputs)
        }
        return section.countOn()
    }

    override fun part2(input: Collection<Recipe>): Any {
        val inputs = input.flatMap { it.variations() }
        var section = Section(arrayOf(booleanArrayOf(false, true, false), booleanArrayOf(false, false, true), booleanArrayOf(true, true, true)))
        for (i in 1..18) {
            section = section.splitEnhanceAndCombine(inputs)
        }
        return section.countOn()
    }

    class Section(val values: Array<BooleanArray>) {

        val size = values.size
        private val subSize = if (size % 2 == 0) 2 else 3
        fun splitEnhanceAndCombine(input: Collection<Recipe>): Section {
            val sections: Array<Array<Section>> = Array(size / subSize, {idxY ->
                Array(size / subSize, {idxX ->
                    sectionOf(idxX, idxY)
                })
            })
            val transformed = sections.map { it.map {
                it.enhance(input)
            }.toTypedArray() }.toTypedArray()
            val nextSubSize = transformed.first().first().size
            val nextSize = transformed.size * nextSubSize
            val nextRange = 0 until nextSize
            val next = nextRange.map { y ->
                nextRange.map { x ->
                    val sectionX = x / nextSubSize
                    val sectionY = y / nextSubSize
                    val localX = x % nextSubSize
                    val localY = y % nextSubSize
                    transformed[sectionY][sectionX].values[localY][localX]
                }.toBooleanArray()
            }.toTypedArray()
            return Section(next)
        }

        private fun sectionOf(sectionX: Int, sectionY: Int): Section {
            val range = 0 until subSize
            val arr = range.map { y ->
                range.map { x ->
                    val bigX = sectionX * subSize + x
                    val bigY = sectionY * subSize + y
                    this.values[bigY][bigX]
                }.toBooleanArray()
            }.toTypedArray()
            return Section(arr)
        }

        private fun enhance(input: Collection<Recipe>): Section {
            if (size != 2 && size != 3) {
                throw IllegalArgumentException("Unsupported enhancement size: $size")
            }
            val rule = input.filter { it.matches(this) }.toList()
            if (rule.size != 1) {
                // throw IllegalStateException("Multiple matches for ${this.values.strRep()}: $rule")
            }
            return rule.first().applyTo(this)
        }

        fun countOn(): Int {
            return values.countOn()
        }

    }

    class Recipe(private val pattern: Array<BooleanArray>, private val replacement: Array<BooleanArray>) {

        private val patternSection = Section(pattern)
        fun matches(section: Section): Boolean {
            if (section.size != patternSection.size) {
                return false
            }
            return section.values.matches(patternSection.values)
        }

        fun rotate(): Recipe {
            return Recipe(rotate(pattern), replacement)
        }

        fun flipX(): Recipe {
            return Recipe(flipX(pattern), replacement)
        }

        private fun rotate(arr: Array<BooleanArray>): Array<BooleanArray> {
            val range = 0 until arr.size
            val result = range.map({y ->
                range.map {x ->
                    arr[x][arr.size - 1 - y]
                }.toBooleanArray()
            }).toTypedArray()
            if (arr.countOn() != result.countOn()) {
                throw IllegalStateException("Bad rotate transformation: ${arr.strRep()} --> ${result.strRep()}")
            }
            return result
        }

        private fun flipX(arr: Array<BooleanArray>): Array<BooleanArray> {
            val range = 0 until arr.size
            val oldOn = arr.countOn()
            val result = range.map({y ->
                range.map { x ->
                    arr[y][arr.size - 1 - x]
                }.toBooleanArray()
            }).toTypedArray()
            if (oldOn != result.countOn()) {
                throw IllegalStateException("Bad flip transformation: ${arr.strRep()} --> ${result.strRep()}")
            }
            return result
        }

        fun applyTo(section: Section): Section {
            return Section(replacement)
        }

        fun variations(): List<Recipe> {
            val rotated = rotate()
            val rotated2 = rotated.rotate()
            val flipped = flipX()
            val flippedRotated = flipped.rotate()
            val flippedRotated2 = flippedRotated.rotate()
            val list = listOf(rotated, rotated2, rotated2.rotate(),
                flipped, flippedRotated, flippedRotated2, flippedRotated2.rotate()).filter { !it.pattern.matches(this.pattern) }.toMutableList()
            list.add(this)
            return list
        }

        override fun toString(): String {
            return "${this.pattern.strRep()} => ${this.replacement.strRep()}"
        }

    }

}

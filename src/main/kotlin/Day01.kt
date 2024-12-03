import kotlin.math.abs

fun main() {
    val input = loadInput(1)
    val testInput = loadTestInput(1, 1)
    val day01Test = Day01(testInput)
    val day01 = Day01(input)
    // test if implementation meets criteria from the description, like:
    check(day01Test.part1() == 11)
    check(day01Test.part2() == 31)

    day01.part1().println()
    day01.part2().println()
}

class Day01(input: List<String>) {
    private val leftList = mutableListOf<Int>()
    private val rightList = mutableListOf<Int>()

    init {
        for (line in input) {
            line.split("   ").let {
                leftList.add(it[0].toInt())
                rightList.add(it[1].toInt())
            }
        }
    }

    fun part1(): Int {
        leftList.sort()
        rightList.sort()

        val sum = leftList.zip(rightList, Int::minus).sumOf { abs(it) }
        return sum
    }

    fun part2(): Int {
        val valueMap = mutableMapOf<Int, Int>()
        for (number in leftList) {
            valueMap[number] = rightList.count { it == number }
        }
        var sum = 0
        leftList.forEach { sum += valueMap[it]?.times(it) ?: 0 }
        return sum
    }

}

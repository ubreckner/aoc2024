import kotlin.math.abs

fun main() {
    val input = loadInput(2)
    val testInput = loadTestInput(2, 1)

    val dayTest = Day02(testInput)
    val day = Day02(input)
    // test if implementation meets criteria from the description, like:
    check(dayTest.part1() == 2)
    day.part1().println()

    check(dayTest.part2() == 4)
    day.part2().println()
}

class Day02(input: List<String>) {
    private val reports = mutableListOf<List<Int>>()

    init {
        for (line in input) {
            reports.add(line.split(" ").map { it.toInt() })
        }
    }

    fun part1(): Int {
        var sum = 0
        for (report in reports) {
            val levelSteps = report.zipWithNext { a, b -> a - b }
            if (isSafe(levelSteps))
                sum++
        }

        return sum
    }

    private fun isSafe(levelSteps: List<Int>) =
        levelSteps.all { abs(it) in 1..3 } && (levelSteps.all { it > 0 } || levelSteps.all { it < 0 })

    fun part2(): Int {
        var sum = 0
        for (report in reports) {
            var levelSteps = report.zipWithNext { a, b -> a - b }
            if (isSafe(levelSteps))
                sum++
            else {
                for (index in 0..report.lastIndex) {
                    val reportCopy = report.toMutableList().apply { removeAt(index) }
                    levelSteps = reportCopy.zipWithNext { a, b -> a - b }
                    if (isSafe(levelSteps)) {
                        sum++
                        break
                    }
                }
            }
        }
        return sum
    }
}

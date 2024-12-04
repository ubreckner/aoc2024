fun main() {
    val dayToday = 1
    val input = loadInput(dayToday)
    val testInput = loadTestInput(dayToday, 1)

    val dayTest = Day02(testInput)
    val day = Day02(input)
    // test if implementation meets criteria from the description, like:
    check(dayTest.part1() == 0)
    day.part1().println()

    check(dayTest.part2() == 0)
    day.part2().println()
}

class Day00(input: List<String>) {
    private val inputData = mutableListOf<List<Int>>()

    init {
        for (line in input) {
            inputData.add(line.split(" ").map { it.toInt() })
        }
    }

    fun part1(): Int {
        var sum = 0
        return sum
    }

    fun part2(): Int {
        var sum = 0
        return sum
    }
}

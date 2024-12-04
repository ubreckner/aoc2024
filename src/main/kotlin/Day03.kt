fun main() {
    val dayToday = 3

    val input = loadInput(dayToday)
    val testInput = loadTestInput(dayToday, 1)

    val dayTest = Day03(testInput)
    val day = Day03(input)
    // test if implementation meets criteria from the description, like:
    check(dayTest.part1() == 161)
    day.part1().println()

    check(dayTest.part2() == 0)
    day.part2().println()
}

class Day03(input: List<String>) {

    val data = input

    init {
    }

    fun part1(): Int {
        var sum = 0
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
        for (line in data) {
            val res = regex.findAll(line)
            sum += res.sumOf { it.groupValues[1].toInt().times(it.groupValues[2].toInt()) }
        }
        return sum
    }

    fun part2(): Int {
        var sum = 0
        return sum
    }
}

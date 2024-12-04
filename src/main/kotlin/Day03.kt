fun main() {
    val dayToday = 3

    val input = loadInput(dayToday)

    val dayTest1 = Day03(loadTestInput(dayToday, 1))
    val dayTest2 = Day03(loadTestInput(dayToday, 2))
    val day = Day03(input)
    // test if implementation meets criteria from the description, like:
    check(dayTest1.part1() == 161)
    day.part1().println()

    check(dayTest2.part2() == 48)
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
        val regex = Regex("""mul\((\d+),(\d+)\)|(do\(\))|don't\(\)""")
        var active = true
        for (line in data) {
            val res = regex.findAll(line)
            for (match in res) {
                if (match.value == "do()") {
                    active = true
                } else if (match.value == "don't()") {
                    active = false
                } else if (active) {
                    sum += match.groupValues[1].toInt().times(match.groupValues[2].toInt())
                }
            }
        }
        return sum
    }
}

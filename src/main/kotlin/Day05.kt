fun main() {
    val dayToday = 5
    val input = loadInput(dayToday)
    val testInput = loadTestInput(dayToday, 1)

    val dayTest = Day05(testInput)
    val day = Day05(input)
    // test if implementation meets criteria from the description, like:
    check(dayTest.part1() == 143)
    day.part1().println()

    check(dayTest.part2() == 123)
    day.part2().println()
}

class Day05(input: List<String>) {
    private val data: List<MutableList<String>> = input.fold(mutableListOf(mutableListOf())) { acc, str ->
        if (str.isEmpty()) {
            acc.add(mutableListOf())
        } else {
            acc.last().add(str)
        }
        acc
    }
    private val orderingRules = data[0].map { Pair(it.split("|")[0].toInt(), it.split("|")[1].toInt()) }
    private val updates = data[1].map { it.split(",").map { it.toInt() } }

    fun part1(): Int {
        var sum = 0
        updates.filter { testRightOrder(it, orderingRules) }.forEach { sum += it[it.size.div(2)] }
        return sum
    }

    private fun testRightOrder(update: List<Int>, orderingRules: List<Pair<Int, Int>>): Boolean {;
        return orderingRules.none {
            update.containsAll(listOf(it.first, it.second)) && update.indexOf(it.first) > update.indexOf(it.second)
        }
    }

    fun part2(): Int {
        var sum = 0
        val unorderedUpdates = updates.filter { !testRightOrder(it, orderingRules) }
        for (update in unorderedUpdates) {
            val relevantRules = orderingRules.filter { update.containsAll(listOf(it.first, it.second)) }
            var orderedUpdate = update.toMutableList()
            for (page in update) {
                for (rule in relevantRules) {
                    if (rule.second == page && orderedUpdate.indexOf(rule.first) > orderedUpdate.indexOf(rule.second)) {
                        orderedUpdate.add(orderedUpdate.indexOf(rule.first) + 1, page)
                        orderedUpdate.removeAt(orderedUpdate.indexOf(rule.second))
                    }
                }
            }
            sum += orderedUpdate[orderedUpdate.size.div(2)]
        }
        return sum
    }
}

fun main() {
    val dayToday = 4
    val input = loadInput(dayToday)
    val testInput = loadTestInput(dayToday, 1)

    val dayTest = Day04(testInput)
    val day = Day04(input)
    // test if implementation meets criteria from the description, like:
    check(dayTest.part1() == 18)
    day.part1().println()

    check(dayTest.part2() == 9)
    day.part2().println()
}

class Day04(input: List<String>) {
    private val data = input

    fun part1(): Int {
        val searchWord = "XMAS"
        var sum = 0
        for ((y, line) in data.withIndex()) {
            for ((x, _) in line.withIndex()) {
                sum += Direction.entries.toTypedArray().count { (dirX, dirY) ->
                    searchWord.withIndex().all { (idx, wordLetter) ->
                        data.getOrNull(y + idx * dirY)?.getOrNull(x + idx * dirX) == wordLetter
                    }
                }
            }
        }
        return sum
    }

    fun part2(): Int {
        val searchWord = "MAS"
        var sum = 0

        for ((y, line) in data.withIndex()) {
            for ((x, _) in line.withIndex()) {
                if (
                    //first search for down and right direction forwards
                    (searchWord.withIndex().all { (idx, wordLetter) ->
                        data.getOrNull(y + idx * Direction.DOWNRIGHT.y)
                            ?.getOrNull(x + idx * Direction.DOWNRIGHT.x) == wordLetter
                    //and backwards
                    } || searchWord.reversed().withIndex().all { (idx, wordLetter) ->
                        data.getOrNull(y + idx * Direction.DOWNRIGHT.y)
                            ?.getOrNull(x + idx * Direction.DOWNRIGHT.x) == wordLetter
                    })
                    //and then the corresponding opposite direction forwards
                    && (searchWord.withIndex().all { (idx, wordLetter) ->
                        data.getOrNull(y + 2 + idx * Direction.UPRIGHT.y)
                            ?.getOrNull(x + idx * Direction.UPRIGHT.x) == wordLetter
                    //and backwards
                    } || searchWord.reversed().withIndex().all { (idx, wordLetter) ->
                        data.getOrNull(y + 2 + idx * Direction.UPRIGHT.y)
                            ?.getOrNull(x + idx * Direction.UPRIGHT.x) == wordLetter
                    })
                ) {
                    sum++
                }
            }
        }
        return sum
    }

    private enum class Direction(val x: Int, val y: Int) {
        RIGHT(1, 0),
        UPRIGHT(1, -1),
        UP(0, -1),
        UPLEFT(-1, -1),
        LEFT(-1, 0),
        DOWNLEFT(-1, 1),
        DOWN(0, 1),
        DOWNRIGHT(1, 1);

        operator fun component1() = x
        operator fun component2() = y

    }
}

fun main() {
    val dayToday = 6
    val input = loadInput(dayToday)
    val testInput = loadTestInput(dayToday, 1)

    val dayTest = Day06(testInput)
    val day = Day06(input)
    // test if implementation meets criteria from the description, like:
    println(dayTest.part1()).also { check(dayTest.part1() == 41) }
    day.part1().println()

    println(dayTest.part2()).also { check(dayTest.part2() == 6) }
    day.part2().println()
}

class Day06(input: List<String>) {
    private val data = input
    private val directionMap =
        mapOf('^' to Direction.UP, '>' to Direction.RIGHT, '<' to Direction.LEFT, 'v' to Direction.DOWN)

    fun part1(): Int {
        val resSet = getVisitedPoints()
        return resSet.size
    }

    private fun getVisitedPoints(): MutableSet<Pair<Int, Int>> {
        val resSet = mutableSetOf<Pair<Int, Int>>()
        var curPoint = Pair(0, 0)
        var curDirection = '^'

        //Get Starting Point
        curPoint = Pair(data.withIndex().find { (_, elem) -> elem.contains(curDirection) }
            ?.let { curPoint = Pair(0, it.index); it.value }!!.indexOf(curDirection), curPoint.second)

        while (stillInLab(curPoint, data)) {
            while (stillInLab(curPoint, data) && data[curPoint.second][curPoint.first] != '#') {
                resSet.add(curPoint)
                curPoint =
                    Pair(
                        curPoint.first + directionMap[curDirection]!!.x,
                        curPoint.second + directionMap[curDirection]!!.y
                    )
            }
            if (stillInLab(curPoint, data)) {
                curPoint =
                    Pair(
                        curPoint.first - directionMap[curDirection]!!.x,
                        curPoint.second - directionMap[curDirection]!!.y
                    )
            }
            curDirection = rotateRight(curDirection)
        }
        return resSet
    }

    private fun stillInLab(curPoint: Pair<Int, Int>, data: List<String>): Boolean {
        return curPoint.first in data[0].indices && curPoint.second in data.indices
    }

    private fun rotateRight(curDirection: Char): Char {
        return when (curDirection) {
            '^' -> return '>'
            '<' -> return '^'
            'v' -> return '<'
            '>' -> return 'v'
            else -> {
                'E'
            }
        }
    }

    fun part2(): Int {
        var sum = 0
        var startPoint = Pair(0, 0)

        startPoint = Pair(data.withIndex().find { (_, elem) -> elem.contains('^') }
            ?.let { startPoint = Pair(0, it.index); it.value }!!.indexOf('^'), startPoint.second)

        val availablePointsToGuard = getVisitedPoints()
        availablePointsToGuard.remove(startPoint)

        outer@ for (visitedPoint in availablePointsToGuard) {
            var curPoint = VisitedPoint('^', startPoint)
            var curDirection = '^'
            val alreadyVisitedPoints = mutableListOf<VisitedPoint>()

            while (stillInLab(curPoint.point, data)) {
                while (stillInLab(curPoint.point, data)
                    && (data[curPoint.point.second][curPoint.point.first] != '#'
                            && !(curPoint.point.second == visitedPoint.second && curPoint.point.first == visitedPoint.first))
                ) {
                    if (!alreadyVisitedPoints.contains(curPoint)) {
                        alreadyVisitedPoints.add(curPoint)
                    } else {
                        sum++
                        continue@outer
                    }
                    curPoint = VisitedPoint(
                        curDirection,
                        Pair(
                            curPoint.point.first + directionMap[curDirection]!!.x,
                            curPoint.point.second + directionMap[curDirection]!!.y
                        )
                    )
                }
                if (stillInLab(curPoint.point, data)) {
                    curPoint = VisitedPoint(
                        curDirection,
                        Pair(
                            curPoint.point.first - directionMap[curDirection]!!.x,
                            curPoint.point.second - directionMap[curDirection]!!.y
                        )
                    )
                }
                curDirection = rotateRight(curDirection)
                curPoint.direction = curDirection
            }
        }

        return sum
    }

    private enum class Direction(val x: Int, val y: Int) {
        UP(0, -1),
        RIGHT(1, 0),
        LEFT(-1, 0),
        DOWN(0, 1);
    }

    private data class VisitedPoint(var direction: Char, val point: Pair<Int, Int>)
}

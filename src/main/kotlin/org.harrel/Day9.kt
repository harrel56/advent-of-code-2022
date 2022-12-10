package org.harrel

import kotlin.math.abs

fun main() {
    fun pt1(): Any {
        var head = Point(0, 0)
        var tail = Point(0, 0)
        val visited = HashSet<Point>()
        visited.add(tail)
        readToString("/day9.data")
            .split(System.lineSeparator())
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .forEach { (direction, times) ->
                (times downTo 1).forEach { _ ->
                    head = when (direction) {
                        "U" -> Point(head.x, head.y + 1)
                        "D" -> Point(head.x, head.y - 1)
                        "L" -> Point(head.x - 1, head.y)
                        "R" -> Point(head.x + 1, head.y)
                        else -> throw IllegalArgumentException(direction)
                    }
                    val dif = head - tail
                    if (abs(dif.x) > 1 || abs(dif.y) > 1) {
                        tail += dif.toUnaryPoint()
                        visited.add(tail)
                    }
                }
            }
        return visited.size
    }

    fun pt2(): Any {
        val knots = Array(10) { Point(0, 0) }
        val visited = HashSet<Point>()
        visited.add(knots.last())
        readToString("/day9.data")
            .split(System.lineSeparator())
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .forEach { (direction, times) ->
                (times downTo 1).forEach { _ ->
                    knots[0] = when (direction) {
                        "U" -> Point(knots[0].x, knots[0].y + 1)
                        "D" -> Point(knots[0].x, knots[0].y - 1)
                        "L" -> Point(knots[0].x - 1, knots[0].y)
                        "R" -> Point(knots[0].x + 1, knots[0].y)
                        else -> throw IllegalArgumentException(direction)
                    }

                    knots.foldIndexed(knots[0]) { currentIdx, prev, current ->
                        val dif = prev - current
                        if (abs(dif.x) > 1 || abs(dif.y) > 1) {
                            knots[currentIdx] = current + dif.toUnaryPoint()
                        }
                        knots[currentIdx]
                    }
                    visited.add(knots.last())
                }
            }
        return visited.size
    }
    println(pt1())
    println(pt2())
}
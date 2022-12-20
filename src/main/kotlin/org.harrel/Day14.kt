package org.harrel

import java.util.function.Predicate

fun main() {
    fun readWalls(): MutableSet<Point> {
        val points = mutableSetOf<Point>()
        readToString("/day14.data")
            .split(System.lineSeparator())
            .map { line ->
                line.split(" -> ")
                    .map { it.split(",") }
                    .map { Point(it[0].toInt(), it[1].toInt()) }
            }
            .forEach { line ->
                line.windowed(2).forEach {
                    if (it[0].x == it[1].x) {
                        val constX = it[0].x
                        val minY = it[0].y.coerceAtMost(it[1].y)
                        val maxY = it[0].y.coerceAtLeast(it[1].y)
                        (minY..maxY).forEach { y -> points.add(Point(constX, y)) }
                    } else {
                        val constY = it[0].y
                        val minX = it[0].x.coerceAtMost(it[1].x)
                        val maxX = it[0].x.coerceAtLeast(it[1].x)
                        (minX..maxX).forEach { x -> points.add(Point(x, constY)) }
                    }
                }
            }
        return points
    }

    fun doTheFalling(points: MutableSet<Point>, endCondition: Predicate<Point>): Int {
        val DOWN = Point(0, 1)
        val DOWN_LEFT = Point(-1, 1)
        val DOWN_RIGHT = Point(1, 1)
        var cnt = 0

        while (true) {
            var current = Point(500, 0)
            while (true) {
                if (endCondition.test(current)) {
                    return cnt
                } else if (!points.contains(current + DOWN)) {
                    current += DOWN
                } else if (!points.contains(current + DOWN_LEFT)) {
                    current += DOWN_LEFT
                } else if (!points.contains(current + DOWN_RIGHT)) {
                    current += DOWN_RIGHT
                } else {
                    cnt++
                    points.add(current)
                    break
                }
            }
        }
    }

    fun pt1(): Any? {
        val points = readWalls()
        val deepest = points.maxOf { it.y }

        return doTheFalling(points) { p -> p.y >= deepest }
    }

    fun pt2(): Any? {
        val points = readWalls()
        val deepest = points.maxOf { it.y }
        (0..1000).forEach { points.add(Point(it, deepest + 2)) }

        return doTheFalling(points) { _ -> points.contains(Point(500, 0)) }
    }

    println(pt1())
    println(pt2())
}
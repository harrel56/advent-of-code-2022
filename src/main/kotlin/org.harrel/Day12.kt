package org.harrel

import kotlin.math.pow
import kotlin.math.sqrt

fun main() {

    var endPoint = MapPoint(-1, -1, -1)
    val points = mutableMapOf<Point, MapPoint>()
    readToString("/day12.data")
        .split(System.lineSeparator())
        .forEachIndexed { y, row ->
            row.toList().forEachIndexed { x, it ->
                if (it == 'S') {
                    points[Point(x, y)] = MapPoint(x, y, 'a'.code)
                } else if (it == 'E') {
                    endPoint = MapPoint(x, y, 'z'.code)
                    points[Point(x, y)] = endPoint
                } else {
                    points[Point(x, y)] = MapPoint(x, y, it.code)
                }
            }
        }

    fun findShortestPathLength(startPoint: Point): Double {
        val openList = LinkedHashMap<Point, MapPoint>()
        val closedList = LinkedHashMap<Point, MapPoint>()

        openList[startPoint] = points[startPoint]!!
        while (openList.isNotEmpty()) {
            val q = openList.values.minBy { it.f() }
            openList.remove(Point(q.x, q.y))
            val successors = q.getSuccessors(points)
            if (successors.contains(endPoint)) {
                return q.g + 1
            }

            successors.map {
                val copy = it.copy()
                copy.g = q.g + 1
                copy.h = sqrt((copy.x - endPoint.x).toDouble().pow(2.0) + (copy.y - endPoint.y).toDouble().pow(2.0))
                copy
            }.filter {
                val current = openList[Point(it.x, it.y)]
                current == null || current.f() >= it.f()
            }.filter {
                val current = closedList[Point(it.x, it.y)]
                current == null || current.f() >= it.f()
            }.forEach { openList[Point(it.x, it.y)] = it }

            closedList[Point(q.x, q.y)] = q
        }

        return Double.MAX_VALUE
    }

    fun pt1(): Double {
        return findShortestPathLength(Point(0, 20))
    }

    fun pt2(): Double? {
        return points.entries
            .filter { it.value.z == 97 }
            .minOfOrNull { findShortestPathLength(it.key) }
    }

    println(pt1())
    println(pt2())
}
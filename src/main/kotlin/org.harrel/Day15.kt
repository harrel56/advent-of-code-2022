package org.harrel

import kotlin.math.abs

fun main() {
    fun pt1(): Any? {
        val row = 2_000_000
        val points = readToString("/day15.data")
            .split(System.lineSeparator())
            .map { it.split(" ").map { pt -> pt.split(",") } }
            .map { Point(it[0][0].toInt(), it[0][1].toInt()) to Point(it[1][0].toInt(), it[1][1].toInt()) }
        val collidingPoints = points
            .flatMap { listOf(it.first, it.second) }
            .filter { it.y == row }
            .map { it.x }
            .toSet()
        return points
            .map { it.first to abs(it.first.x - it.second.x) + abs(it.first.y - it.second.y) }
            .map { it.first to it.second - abs(it.first.y - row) }
            .filter { it.second > 0 }
            .map { (it.first.x - it.second)..(it.first.x + it.second) }
            .flatMap { it.asIterable() }
            .toSet()
            .filter { !collidingPoints.contains(it) }
            .size
    }

    fun isContainedIn(r1: IntRange, r2: IntRange): Boolean {
        return r1.contains(r2.first) && r1.contains(r2.last)
    }

    fun findLeftover(ranges: List<IntRange>): Set<Int> {
        val result = mutableSetOf<Int>()
        var prev = ranges[0]
        for (range in ranges) {
            if (isContainedIn(prev, range)) {
                continue
            }
            if (prev.last < range.first - 1) {
                result.addAll(((prev.last + 1) until range.first).toList())
            }
            prev = range

        }
        return result
    }

    fun pt2(): Any? {
        val result = mutableSetOf<Point>()
        val pointsWithDist = readToString("/day15.data")
            .split(System.lineSeparator())
            .map { it.split(" ").map { pt -> pt.split(",") } }
            .map { Point(it[0][0].toInt(), it[0][1].toInt()) to Point(it[1][0].toInt(), it[1][1].toInt()) }
            .map { it.first to abs(it.first.x - it.second.x) + abs(it.first.y - it.second.y) }
        (0..4_000_000).forEach { row ->
            val ranges = pointsWithDist
                .map { it.first to it.second - abs(it.first.y - row) }
                .filter { it.second > 0 }
                .map { (it.first.x - it.second)..(it.first.x + it.second) }
                .sortedBy { it.first }
            result.addAll(findLeftover(ranges).map { Point(it, row) })
        }
        return result.first().x * 4_000_000L + result.first().y
    }

    println(pt1())
    println(pt2())
}
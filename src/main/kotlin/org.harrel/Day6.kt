package org.harrel

import kotlin.streams.toList

fun main() {
    fun findMarkerPosition(size: Int): Int {
        return readToString("/day6.data")
            .chars()
            .toList()
            .windowed(size) { HashSet(it) }
            .mapIndexed { idx, set -> idx to set.size }
            .first { it.second == size }
            .first + size
    }

    fun pt1(): Int {
        return findMarkerPosition(4)
    }

    fun pt2(): Int {
        return findMarkerPosition(14)
    }
    println(pt1())
    println(pt2())
}
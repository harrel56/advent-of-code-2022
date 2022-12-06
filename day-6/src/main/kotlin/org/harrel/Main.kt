package org.harrel

import kotlin.streams.toList

fun main() {
    println(pt1())
    println(pt2())
}

fun pt1(): Int {
    return findMarkerPosition(4)
}

fun pt2(): Int {
    return findMarkerPosition(14)
}

fun findMarkerPosition(size: Int): Int {
    return readToString("/input.data")
        .chars()
        .toList()
        .windowed(size) { HashSet(it) }
        .mapIndexed { idx, set -> idx to set.size }
        .first { it.second == size }
        .first + size
}

fun readToString(file: String): String {
    return {}::class.java.getResourceAsStream(file)!!
        .bufferedReader()
        .readText()
}
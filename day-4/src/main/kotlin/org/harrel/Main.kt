package org.harrel

fun main() {
    println(pt1())
    println(pt2())
}

fun pt1(): Any {
    return readToString("/input.data")
        .split(System.lineSeparator())
        .map { it.split(",") }
        .map { it[0].split("-") to it[1].split("-") }
        .count { isContainedIn(it.first, it.second) || isContainedIn(it.second, it.first) }
}

fun pt2(): Any {
    return readToString("/input.data")
        .split(System.lineSeparator())
        .map { it.split(",") }
        .map { it[0].split("-") to it[1].split("-") }
        .count { isOverlapping(it.first, it.second) }
}

fun isContainedIn(range1: List<String>, range2: List<String>): Boolean {
    return range1[0].toInt() <= range2[0].toInt() && range1[1].toInt() >= range2[1].toInt()
}

fun isOverlapping(range1: List<String>, range2: List<String>): Boolean {
    val first = range1[0].toInt()..range1[1].toInt()
    return first.contains(range2[0].toInt()) || first.contains(range2[1].toInt()) || isContainedIn(range2, range1)
}

fun readToString(file: String): String {
    return {}::class.java.getResourceAsStream(file)!!
        .bufferedReader()
        .readText()
}
package org.harrel

import java.lang.IllegalArgumentException

fun main() {
    println(pt1())
    println(pt2())
}

fun pt1(): Any {
    return readToString("/input.data")
        .split(System.lineSeparator())
        .sumOf {
            val dist = it[0].minus(it[2]).plus(23)
            val score = when (dist) {
                -2, 1-> 0
                0 -> 3
                -1, 2 -> 6
                else -> throw IllegalArgumentException()
            }
            getFigurePoints(it) + score
        }
}

fun pt2(): Any {
    return readToString("/input.data")
        .split(System.lineSeparator())
        .sumOf {
            val score = when (getFigurePoints(it)) {
                1 -> 1 + ((it[0].code - 65) + 2) % 3
                2 -> 4 + (it[0].code - 65)
                3 -> 7 + ((it[0].code - 65) + 1) % 3
                else -> throw IllegalArgumentException()
            }
            score
        }
}

fun getFigurePoints(line: String): Int {
    return line[2].minus(87).code
}

fun readToString(file: String): String {
    return {}::class.java.getResourceAsStream(file)!!
        .bufferedReader()
        .readText()
}
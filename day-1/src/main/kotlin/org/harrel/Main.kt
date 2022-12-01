package org.harrel

fun main() {
    println(pt1())
    println(pt2())
}

fun pt1(): Long? {
    return readToString("/input.data")
        .split("\r\n\r\n")
        .maxOfOrNull { elf ->
            elf.split("\r\n").sumOf { it.toLong() }
        }
}

fun pt2(): Long {
    val sorted = readToString("/input.data")
        .split("\r\n\r\n")
        .map { elf ->
            elf.split("\r\n").sumOf { it.toLong() }
        }
        .sortedDescending()

    return sorted[0] + sorted[1] + sorted[2]
}

fun readToString(file: String): String {
    return {}::class.java.getResourceAsStream(file)!!
        .bufferedReader()
        .readText()
}
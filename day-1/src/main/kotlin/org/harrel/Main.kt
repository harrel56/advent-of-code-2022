package org.harrel

fun main() {
    println(pt1())
    println(pt2())
}

fun pt1(): Long? {
    return readToString("/input.data")
        .split(System.lineSeparator() + System.lineSeparator())
        .maxOfOrNull { elf ->
            elf.split(System.lineSeparator()).sumOf { it.toLong() }
        }
}

fun pt2(): Long {
    return readToString("/input.data")
        .split(System.lineSeparator() + System.lineSeparator())
        .map { elf ->
            elf.split(System.lineSeparator()).sumOf { it.toLong() }
        }
        .sortedDescending()
        .subList(0, 3)
        .sum()
}

fun readToString(file: String): String {
    return {}::class.java.getResourceAsStream(file)!!
        .bufferedReader()
        .readText()
}
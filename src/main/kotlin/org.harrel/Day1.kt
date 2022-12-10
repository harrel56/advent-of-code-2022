package org.harrel

fun main() {
    fun pt1(): Long? {
        return readToString("/day1.data")
            .split(System.lineSeparator() + System.lineSeparator())
            .maxOfOrNull { elf ->
                elf.split(System.lineSeparator()).sumOf { it.toLong() }
            }
    }

    fun pt2(): Long {
        return readToString("/day1.data")
            .split(System.lineSeparator() + System.lineSeparator())
            .map { elf ->
                elf.split(System.lineSeparator()).sumOf { it.toLong() }
            }
            .sortedDescending()
            .subList(0, 3)
            .sum()
    }

    println(pt1())
    println(pt2())
}
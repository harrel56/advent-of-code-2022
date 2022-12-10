package org.harrel

import java.util.stream.Collectors

fun main() {
    fun toPriority(item: Int): Int {
        return item - if (item.toChar().isLowerCase()) 96 else 38
    }
    fun pt1(): Any {
        return readToString("/day3.data")
            .split(System.lineSeparator())
            .map { it.substring(0, it.length / 2) to it.substring(it.length / 2) }
            .sumOf { pair ->
                val first = pair.first.chars().boxed().collect(Collectors.toSet())
                val duplicate = pair.second.chars().filter { first.contains(it) }.findFirst().orElseThrow()
                toPriority(duplicate)
            }
    }
    fun pt2(): Any {
        return readToString("/day3.data")
            .split(System.lineSeparator())
            .fold(ArrayList<ArrayList<String>>()) { list, item ->
                if (list.isEmpty()) {
                    list.add(ArrayList())
                }

                if (list.last().size < 3) {
                    list.last().add(item)
                } else {
                    val group = ArrayList<String>(3)
                    group.add(item)
                    list.add(group)
                }
                list
            }.sumOf { group ->
                val first = group[0].chars().boxed().collect(Collectors.toSet())
                val second = group[1].chars().boxed().filter { first.contains(it) }.collect(Collectors.toSet())
                val badge = group[2].chars().boxed().filter { second.contains(it) }.findFirst().orElseThrow()
                toPriority(badge)
            }
    }
    println(pt1())
    println(pt2())
}
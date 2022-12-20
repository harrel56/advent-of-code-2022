package org.harrel

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

fun main() {
    fun compare(left: JsonElement, right: JsonElement): Int {
        if (left is JsonPrimitive && right is JsonPrimitive) {
            return left.content.toInt().compareTo(right.content.toInt())
        } else if (left is JsonArray && right is JsonArray) {
            val minSize = left.size.coerceAtMost(right.size)
            val firstNotEqual = (0 until minSize).map { idx ->
                compare(left[idx], right[idx])
            }.firstOrNull { it != 0 }
            if (firstNotEqual == null) {
                return left.size.compareTo(right.size)
            } else {
                return firstNotEqual
            }
        } else if (left !is JsonArray) {
            return compare(JsonArray(listOf(left)), right)
        } else {
            return compare(left, JsonArray(listOf(right)))
        }
    }

    fun pt1(): Any? {
        return readToString("/day13.data")
            .split(System.lineSeparator() + System.lineSeparator())
            .map { it.split(System.lineSeparator()) }
            .map {
                Json.decodeFromString<JsonArray>(it[0]) to Json.decodeFromString<JsonArray>(it[1])
            }
            .mapIndexed { idx, it ->
                if (compare(it.first, it.second) == -1) idx + 1 else 0
            }
            .sum()
    }

    fun pt2(): Any? {
        val allPackets = mutableListOf("[[2]]", "[[6]]")
        val packets = readToString("/day13.data")
            .split(System.lineSeparator())
            .filter { it.isNotBlank() }
        allPackets.addAll(packets)

        val sorted = allPackets
            .map { Json.decodeFromString<JsonElement>(it) }
            .sortedWith(::compare)
        return (sorted.indexOf(Json.decodeFromString("[[2]]")) + 1) *
                (sorted.indexOf(Json.decodeFromString("[[6]]")) + 1)
    }

    println(pt1())
    println(pt2())
}
package org.harrel

fun main() {
    println(pt1())
    println(pt2())
}

fun pt1(): String {
    return moveCrates(false)
}

fun pt2(): String {
    return moveCrates(true)
}

fun moveCrates(multiGrab: Boolean): String {
    val (stacksInput, instructionsInput) = readToString("/input.data")
        .split(System.lineSeparator() + System.lineSeparator())
    val stacks: Array<MutableList<String>> = stacksInput.split(System.lineSeparator())
        .flatMap {
            "[a-zA-Z]".toRegex().findAll(it)
        }
        .fold(Array(9) { ArrayDeque() }) { acc, matchResult ->
            acc[(matchResult.range.first - 1) / 4].add(matchResult.value)
            acc
        }

    instructionsInput.split(System.lineSeparator())
        .map {
            val matches = "\\d+".toRegex().findAll(it).toList()
            Triple(matches[0].value.toInt(), matches[1].value.toInt() - 1, matches[2].value.toInt() - 1)
        }
        .forEach {
            val fromStack = stacks[it.second]
            val removed = fromStack.subList(0, it.first)
            stacks[it.second] = fromStack.subList(it.first, fromStack.size)
            stacks[it.third].addAll(0, if (multiGrab) removed else removed.reversed())
        }

    return stacks.joinToString("") { it.first() }
}

fun readToString(file: String): String {
    return {}::class.java.getResourceAsStream(file)!!
        .bufferedReader()
        .readText()
}
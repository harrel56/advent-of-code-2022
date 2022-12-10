package org.harrel

fun main() {
    fun computeState(): List<Int> {
        return readToString("/day10.data")
            .split(System.lineSeparator())
            .map { it.split(" ") }
            .fold(mutableListOf(1)) { acc, it ->
                when (it[0]) {
                    "noop" -> acc.add(acc.last())
                    "addx" -> {
                        acc.add(acc.last())
                        acc.add(acc.last() + it[1].toInt())
                    }
                }
                acc
            }
    }

    fun pt1(): Any {
        val state = computeState()
        return 20 * state[19] +
                60 * state[59] +
                100 * state[99] +
                140 * state[139] +
                180 * state[179] +
                220 * state[219]
    }

    fun pt2(): Any {
        val state = computeState()
        val screen = StringBuilder()
        state.forEachIndexed { idx, it ->
            val pos = idx % 40
            val sprite = (it - 1)..(it + 1)
            screen.append(if (sprite.contains(pos)) "#" else ".")
        }
        return screen.chunked(40).joinToString(System.lineSeparator())
    }

    println(pt1())
    println(pt2())
}
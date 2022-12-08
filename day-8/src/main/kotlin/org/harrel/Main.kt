package org.harrel

const val TABLE_SIZE = 99

data class Point(val x: Int, val y: Int)

fun main() {
    println(pt1())
    println(pt2())
}

fun pt1(): Any {
    val table = readToString("/input.data")
        .split(System.lineSeparator())
        .map { it.toList().map {char -> char.toString().toInt()}.toIntArray() }
        .toTypedArray()

    val visible = HashSet<Point>()
    table.forEachIndexed { y, row ->
        row.foldIndexed(-1) { x, acc, tree ->
            if (tree > acc) {
                visible.add(Point(x, y))
            }
            acc.coerceAtLeast(tree)
        }
    }

    table.forEachIndexed { y, row ->
        row.reversed().foldIndexed(-1) { x, acc, tree ->
            if (tree > acc) {
                visible.add(Point(TABLE_SIZE - 1 - x, y))
            }
            acc.coerceAtLeast(tree)
        }
    }

    val rotated = Array(TABLE_SIZE) { IntArray(TABLE_SIZE) }
    for (x in 0 until TABLE_SIZE) {
        for (y in 0 until TABLE_SIZE) {
            rotated[x][y] = table[y][x]
        }
    }

    rotated.forEachIndexed { x, row ->
        row.foldIndexed(-1) { y, acc, tree ->
            if (tree > acc) {
                visible.add(Point(x, y))
            }
            acc.coerceAtLeast(tree)
        }
    }

    rotated.forEachIndexed { x, row ->
        row.reversed().foldIndexed(-1) { y, acc, tree ->
            if (tree > acc) {
                visible.add(Point(x, TABLE_SIZE - 1 - y))
            }
            acc.coerceAtLeast(tree)
        }
    }

    return visible.size
}

fun pt2(): Any {
    val table = readToString("/input.data")
        .split(System.lineSeparator())
        .map { it.toList().map {char -> char.toString().toInt()}.toIntArray() }
        .toTypedArray()

    val values = ArrayList<Int>(99 * 99)
    for (y in 0 until TABLE_SIZE) {
        for (x in 0 until TABLE_SIZE) {
            val point = Point(x, y)
            values.add(
                countUp(table, point) * countDown(table, point) * countLeft(table, point) * countRight(table, point)
            )
        }
    }

    return values.max()
}

fun countUp(table: Array<IntArray>, start: Point): Int {
    for(y in (start.y - 1) downTo 0) {
        if (table[y][start.x] >= table[start.y][start.x]) {
            return start.y - y
        }
    }
    return start.y
}

fun countDown(table: Array<IntArray>, start: Point): Int {
    for(y in (start.y + 1) until  TABLE_SIZE) {
        if (table[y][start.x] >= table[start.y][start.x]) {
            return y - start.y
        }
    }
    return TABLE_SIZE - 1 - start.y
}

fun countLeft(table: Array<IntArray>, start: Point): Int {
    for(x in (start.x - 1) downTo 0) {
        if (table[start.y][x] >= table[start.y][start.x]) {
            return start.x - x
        }
    }
    return start.x
}

fun countRight(table: Array<IntArray>, start: Point): Int {
    for(x in (start.x + 1) until  TABLE_SIZE) {
        if (table[start.y][x] >= table[start.y][start.x]) {
            return x - start.x
        }
    }
    return TABLE_SIZE - 1 - start.x
}

fun readToString(file: String): String {
    return {}::class.java.getResourceAsStream(file)!!
        .bufferedReader()
        .readText()
}
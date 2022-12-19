package org.harrel

import kotlin.math.sign

fun readToString(file: String): String {
    return {}::class.java.getResourceAsStream(file)!!
        .bufferedReader()
        .readText()
}

data class Point(val x: Int, val y: Int) {
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    fun toUnaryPoint() = Point(x.sign, y.sign)
}

data class MapPoint(val x: Int, val y: Int, val z: Int) {
    var g: Double = 0.0
    var h: Double = 0.0

    fun f() = g + h

    fun getSuccessors(map: Map<Point, MapPoint>): List<MapPoint> {
        return listOfNotNull(
            map[Point(x, y - 1)], map[Point(x, y + 1)], map[Point(x - 1, y)], map[Point(x + 1, y)]
        ).filter { z - it.z >= -1 }


    }
}
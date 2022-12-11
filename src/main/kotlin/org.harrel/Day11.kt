package org.harrel

import java.math.BigInteger

data class Monkey(val items: MutableList<BigInteger>, val operation: (BigInteger) -> BigInteger, val test: (BigInteger) -> Int)


fun main() {
    operator fun BigInteger.times(other: Int) = this * other.toBigInteger()
    operator fun BigInteger.plus(other: Int) = this + other.toBigInteger()
    operator fun BigInteger.rem(other: Int) = (this % other.toBigInteger()).toInt()
    fun bigIntegerList(vararg li: Int): MutableList<BigInteger> {
        return li.map { it.toBigInteger() }.toMutableList()
    }
    val monkeys = listOf(
        Monkey(bigIntegerList(63, 57), { v -> v * 11 }, { v -> if (v % 7 == 0) 6 else 2 }),
        Monkey(bigIntegerList(82, 66, 87, 78, 77, 92, 83), { v -> v + 1 }, { v -> if (v % 11 == 0) 5 else 0 }),
        Monkey(bigIntegerList(97, 53, 53, 85, 58, 54), { v -> v * 7 }, { v -> if (v % 13 == 0) 4 else 3 }),
        Monkey(bigIntegerList(50), { v -> v + 3 }, { v -> if (v % 3 == 0) 1 else 7 }),
        Monkey(bigIntegerList(64, 69, 52, 65, 73), { v -> v + 6 }, { v -> if (v % 17 == 0) 3 else 7 }),
        Monkey(bigIntegerList(57, 91, 65), { v -> v + 5 }, { v -> if (v % 2 == 0) 0 else 6 }),
        Monkey(bigIntegerList(67, 91, 84, 78, 60, 69, 99, 83), { v -> v * v }, { v -> if (v % 5 == 0) 2 else 4 }),
        Monkey(bigIntegerList(58, 78, 69, 65), { v -> v + 7 }, { v -> if (v % 19 == 0) 5 else 1 })
    )

    fun monkeyBusiness(round: Int, worryDecrease: (BigInteger) -> BigInteger): Any {
        val counts = LongArray(8)
        (0 until round).forEach { _ ->
            monkeys.forEachIndexed { idx, monkey ->
                monkey.items.forEach { item ->
                    val newItem = worryDecrease(monkey.operation(item))
                    val toMonkey = monkey.test(newItem)
                    monkeys[toMonkey].items.add(newItem)
                }
                counts[idx] = counts[idx] + monkey.items.size
                monkey.items.clear()
            }
        }
        return counts.sortedDescending().subList(0, 2).fold(1L) { acc, it -> acc * it }
    }

    fun pt1() = monkeyBusiness(20) { stress -> stress / 3.toBigInteger() }
    fun pt2() = monkeyBusiness(10000) { stress -> stress % (7*11*13*3*17*2*5*19).toBigInteger() }
    println(pt1())
    println(pt2())
}
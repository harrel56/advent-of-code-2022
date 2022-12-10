package org.harrel


fun main() {
    class Node(val parent: Node?, val name: String, val dir: Boolean, var size: Long = 0) {
        val children: MutableList<Node> = ArrayList()

        fun getRoot(): Node = parent?.getRoot() ?: this
        fun calcSize(): Long {
            if (dir) {
                this.size = children.fold(0L) { acc, it -> acc + it.calcSize() }
            }
            return size
        }

        fun flatten(): MutableList<Node> {
            if (dir) {
                val res = children.fold(ArrayList<Node>()) { acc, it ->
                    acc.addAll(it.flatten())
                    acc
                }
                res.add(this)
                return res
            } else {
                return mutableListOf(this)
            }
        }

        override fun toString(): String {
            return "$name($size): $children"
        }
    }

    abstract class Command {
        abstract fun execute(ctx: Node): Node
        abstract fun addOutputLine(line: String)
    }

    class CommandCd(private val arg: String) : Command() {
        override fun execute(ctx: Node): Node {
            if (arg == "/") {
                return ctx.getRoot()
            }

            if (arg == "..") {
                return ctx.parent!!
            }

            val child = ctx.children.firstOrNull { it.name == arg }
            if (child != null) {
                return child
            } else {
                val newChild = Node(ctx, arg, true)
                ctx.children.add(newChild)
                return newChild
            }
        }

        override fun addOutputLine(line: String) {
            throw UnsupportedOperationException()
        }
    }

    class CommandLs : Command() {
        private val output: MutableList<String> = ArrayList()

        override fun execute(ctx: Node): Node {
            val nameSet = ctx.children.map { it.name }.toSet()
            output.map {
                val (type, name) = it.split(" ")
                if (type == "dir") {
                    Node(ctx, name, true)
                } else {
                    Node(ctx, name, false, type.toLong())
                }
            }
                .filter { !nameSet.contains(it.name) }
                .forEach { ctx.children.add(it) }

            return ctx
        }

        override fun addOutputLine(line: String) {
            output.add(line)
        }
    }

    fun createCommand(line: String): Command =
        if (line.startsWith("cd"))
            CommandCd(line.substring(3))
        else
            CommandLs()

    fun createRootOfFileSystem(): Node {
        return readToString("/day7.data")
            .split(System.lineSeparator())
            .fold(ArrayList<Command>()) { acc, it ->
                if (it.startsWith("$")) {
                    acc.add(createCommand(it.substring(1).trim()))
                } else {
                    acc.last().addOutputLine(it)
                }
                acc
            }
            .fold(Node(null, "/", true)) { acc, it ->
                it.execute(acc)
            }
            .getRoot()
            .also { it.calcSize() }
    }

    fun pt1(): Any {
        return createRootOfFileSystem().flatten()
            .filter { it.dir }
            .filter { it.size <= 100_000 }
            .sumOf { it.size }
    }

    fun pt2(): Any {
        val root = createRootOfFileSystem()
        val spaceToFree = root.size - 40_000_000
        return root.flatten()
            .filter { it.dir }
            .filter { it.size >= spaceToFree }
            .minBy { it.size }
            .size
    }
    println(pt1())
    println(pt2())
}


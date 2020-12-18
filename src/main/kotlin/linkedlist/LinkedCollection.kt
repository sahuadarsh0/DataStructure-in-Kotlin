package linkedlist

import java.lang.IllegalArgumentException


class LinkedCollection<T> private constructor() : LinkedList<T>, MutableLinkedList<T> {

    private constructor(list: LinkedList<T>) : this() {
        _first = list.firstOrNull?.copy()
    }

    private constructor(first: Linked.Node<T>) : this() {
        _first = first.copy()
    }

    private var _first: Linked.Node<T>? = null

    override val firstOrNull: Linked.Node<T>?
        get() = _first

    override val lastOrNull: Linked.Node<T>?
        get() {
            return when (size()) {
                0 -> null
                1 -> _first
                else -> {
                    var current = _first
                    while (current?.next != null) {
                        current = current.next
                    }
                    current
                }
            }
        }

    override fun toString(): String {
        var current = _first
        val builder = StringBuilder("")
        while (current != null) {
            val value = current.value ?: ""
            builder.append(" $value")
            current = current.next
        }
        return builder.toString()
    }

    override fun size(): Int {
        var current = _first
        var size = 0
        while (current != null) {
            ++size
            current = current.next
        }
        return size
    }

    override fun insertFirst(data: T) {
        val node = Linked.Node(data)
        node.next = _first
        _first = node
    }

    override fun insertLast(data: T) {
        val node = Linked.Node(data)

        if (size() == 0) {
            _first = node
            return
        }

        var current = _first
        while (current?.next != null) {
            current = current.next
        }
        current?.next = node
    }

    override fun insertAt(index: Int, data: T) {
        val size = size()
        if (index > size) {
            throw IndexOutOfBoundsException("Invalid index for Insert")
        }

        val node = Linked.Node(data)

        if (index == 0) {
            node.next = _first
            _first = node
            return
        }

        var current = _first
        var tailing = _first
        var count = 0
        while (count != index) {
            tailing = current
            current = current?.next
            ++count
        }
        tailing?.next = node
        node.next = current
        current = node
    }


    override fun deleteFirst() {
        val current = _first ?: return
        val next = current.next
        _first = next
    }

    override fun deleteLast() {
        when (size()) {
            0, 1 -> deleteFirst()
            else -> {
                var current = _first
                var prev = _first
                while (current?.next != null) {
                    prev = current
                    current = current.next
                }
                prev?.next = null
            }
        }
    }

    override fun deleteAt(index: Int) {
        val size = size()
        when {
            index > size -> throw IllegalArgumentException("Invalid index for Deletion")
            index == 0 -> deleteFirst()
            index == size -> deleteLast()
            else -> {
                var current = _first
                var prev = _first
                var position = 0
                while (position != index) {
                    ++position
                    prev = current
                    current = current?.next
                }
                val next = current?.next
                prev?.next = next
                current = null
            }
        }

    }

    override fun delete(element: T) {
        TODO("Not yet implemented")
    }

    companion object {

        fun <T> from(first: Linked.Node<T>): LinkedList<T> {
            return LinkedCollection(first)
        }

        fun <T> from(mutableList: MutableLinkedList<T>): LinkedList<T> {
            return LinkedCollection(mutableList)
        }

        fun <T> newInstance(): LinkedList<T> {
            return LinkedCollection()
        }

        fun <T> newMutableInstance(): MutableLinkedList<T> {
            return LinkedCollection()
        }

        fun <T> mutableFrom(list: LinkedList<T>): MutableLinkedList<T> {
            return LinkedCollection(list)
        }

        fun <T> mutableFrom(first: Linked.Node<T>): MutableLinkedList<T> {
            return LinkedCollection(first)
        }

    }

}


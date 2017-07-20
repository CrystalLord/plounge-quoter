package org.crystal.struct

/**
 * A FIFO data structure that allows placing and removing elements in constant
 * time.
 */
class Queue<T> {
    private var front: Element<T>?
    private var back: Element<T>?
    public var size: Int; private set

    private data class Element<T>(
            var value: T,
            var next: Element<T>?,
            var prev: Element<T>?
    )

    /**
     * Construct an empty queue
     */
    constructor() {
        this.front = null
        this.back = null
        this.size = 0
    }


    /** Is the Queue empty? */
    fun isEmpty(): Boolean = (this.front == null && this.back == null)

    /**
     * Return the number of elements in the Queue
     *
     * Complexity is O(1)
     */
    fun size(): Int {
        return this.size
    }


    override fun toString(): String {
        var output: String = "Queue("
        throw NotImplementedError()
        return ""
    }

    /**
     * Place an element at the back of the queue.
     *
     * Complexity is O(1)
     *
     * @param[value]
     */
    fun enqueue(value: T) {
        val elem: Element<T> = Element<T>(value, null, this.back)
        // If there's something in the back, set it's next to point to us.
        if (this.back != null) {
            this.back?.next = elem
        }

        // Change the back of the queue to our new element.
        this.back = elem

        // If the queue doesn't have a front, set this object to the front.
        if (this.front == null) {
            this.front = elem
        }

        this.size++
    }

    /**
     * Remove an element from the front of the queue, and return it.
     *
     * Complexity is O(1)
     */
    fun dequeue(): T? {
        val output: T? = this.front?.value
        if (this.front != null) {
            this.front = this.front?.next
        }
        // Now that we've changed the front, is it null?
        if (this.front == null) {
            this.back = null
        }

        this.size--
        return output
    }

    /**
     * Return the element at the front of the queue without removing it.
     *
     * Complexity is O(1)
     */
    fun peek(): T? = this.front?.value
}

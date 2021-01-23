package com.giant_giraffe.core


data class ComparablePair<A: Comparable<A>, B: Comparable<B>>(
    val first: A,
    val second: B
): Comparable<ComparablePair<A, B>> {

    override fun toString(): String = "($first, $second)"

    override fun compareTo(other: ComparablePair<A, B>): Int {
        val firstResult = first.compareTo(other.first)
        if (firstResult != 0) {
            return firstResult
        }
        return second.compareTo(other.second)
    }

}
class Square(val side: Int): Figure {

    override fun area(): Int = side * side

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Square

        return side == other.side
    }

    override fun hashCode(): Int {
        return side
    }

    override fun toString(): String {
        return "Square($side)"
    }
}
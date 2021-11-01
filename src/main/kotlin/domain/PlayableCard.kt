package domain

import domain.cards.*

abstract class PlayableCard(val value: Int, val text: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayableCard

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value
    }

    companion object {
        fun fromCardValue(cardValue : Int) : PlayableCard {
            return when(cardValue) {
                1 -> Soldier()
                2 -> Clown()
                3 -> Knight()
                4 -> Priestess()
                5 -> Wizard()
                6 -> General()
                7 -> Minister()
                8 -> Princess()
                else -> Soldier()
            }
        }
    }
}


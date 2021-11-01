package game

import domain.PlayableCard

data class Hand(private val cards: MutableList<PlayableCard> = mutableListOf()) {
    fun add(card: PlayableCard) = cards.add(card)

    fun remove(card: PlayableCard) = cards.remove(card)

    fun show() = cards.firstOrNull()

    fun countValue() = cards.sumOf { it.value }

    fun contains(card: PlayableCard): Boolean {
        return cards.contains(card)
    }

    fun discard() : PlayableCard ? {
        val card = cards.firstOrNull()
        cards.clear()
        return card
    }

    fun allCards(): List<PlayableCard> {
        return cards
    }
}

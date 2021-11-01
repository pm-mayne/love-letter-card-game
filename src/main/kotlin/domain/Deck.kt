package domain

import java.util.*

class Deck(private val cards: Stack<PlayableCard>) {

    fun isEmpty() = cards.isEmpty()

    fun topDeck() : PlayableCard = cards.pop()
}
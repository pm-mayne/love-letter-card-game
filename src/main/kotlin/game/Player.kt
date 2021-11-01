package game

import domain.PlayableCard

data class Player(
    val name: String,
    private var hand: Hand = Hand(),
    var isEliminated: Boolean = false,
    var isTargetable: Boolean = true
) {

    fun draw(card: PlayableCard) {
        hand.add(card)
    }

    fun discardOrPlay(card: PlayableCard) {
        hand.remove(card)
    }

    fun showHand() = hand.show()

    fun handValue() = hand.countValue()
    fun eliminate() {
        isEliminated = true
    }

    fun has(card: PlayableCard): Boolean {
        return hand.contains(card)
    }

    fun discardHand() = hand.discard()

    fun exchangesHandsWith(player: Player) {
        val hand = this.hand
        this.hand = player.hand
        player.hand = hand
    }

    fun getHand(): List<PlayableCard> {
        return hand.allCards()
    }
}

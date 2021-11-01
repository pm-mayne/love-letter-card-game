package game

import domain.PlayableCard
import domain.cards.Soldier

interface BoardManager {
    fun eliminatePlayer(targetPlayer: Player)
    fun hasPlayed(player: Player, playedCard: PlayableCard)
    fun endGame(winner: Player)
    fun beginTurn(player: Player)
    fun nothing()
    fun logPlayEvent(player: Player, playedCard: String, targetCard: String?, targetPlayer: Player?)
    fun logDiscard(targetPlayer: Player, discardedCard: PlayableCard?)
    fun logExchangeHandEvent(targetPlayer: Player, player: Player)

}

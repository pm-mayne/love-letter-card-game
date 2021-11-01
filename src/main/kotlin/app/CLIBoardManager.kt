package app

import domain.PlayableCard
import game.BoardManager
import game.Player

class CLIBoardManager : BoardManager {
    override fun eliminatePlayer(player: Player) {
        println("Player " + player.name + " has been eliminated. Aaarrrrgggh!")
        println("Their hand was:  " + player.getHand().toPrettyString())
    }

    override fun hasPlayed(player: Player, playedCard: PlayableCard) {

    }

    override fun endGame(winner: Player) {
        println("Player " + winner.name + " is the WINNER!!!")
    }

    override fun beginTurn(player: Player) {
        println("New turn - Player " + player.name)
    }

    override fun nothing() {
        println("Nothing happens...")
    }

    override fun logPlayEvent(player: Player, playedCard: String, targetCard: String?, targetPlayer: Player?) {
        print("Player " + player.name + " plays a " + playedCard)
        if(targetCard != null) {
            print(" naming $targetCard")
        }
        if(targetPlayer != null) {
            print(" targeting " + targetPlayer.name)
        }
        println()
    }

    override fun logDiscard(targetPlayer: Player, discardedCard: PlayableCard?) {
        println("Player " + targetPlayer.name + " has discarded " + discardedCard?.value + ":" + discardedCard?.text)
    }

    override fun logExchangeHandEvent(targetPlayer: Player, player: Player) {
        println("Player " + targetPlayer.name + " and player " + player.name + " have exchanged their hands.")
    }

    private fun List<PlayableCard>.toPrettyString() = this.joinToString(" ") { "" + it.value + ":" + it.text }

}
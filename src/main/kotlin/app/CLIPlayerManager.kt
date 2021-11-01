package app

import domain.PlayableCard
import game.Player
import game.PlayerManager

class CLIPlayerManager(private val players: Map<Int, Player>) : PlayerManager {

    private var currentPlayer = -1

    override fun eliminate(player: Player) {
        player.isEliminated = true
    }

    override fun show(shownCard: PlayableCard?, player: Player) {
        println("Shown the card: " + shownCard?.text + " to player " + player.name)
    }

    override fun getPlayedCard(player: Player): PlayableCard {
        println("Your hand: " + player.getHand().toPrettyString())
        println("Please play a card, player " + player.name)
        val cardValue = processResponse()
        return player.getHand().first { it.value == cardValue }
    }


    override fun getAlivePlayers(): Map<Int, Player> {
        return players.filter { !it.value.isEliminated }
    }

    override fun nextPlayer(): Player {
        currentPlayer = (currentPlayer + 1) % players.size
        while (players[currentPlayer]!!.isEliminated) {
            currentPlayer = (currentPlayer + 1) % players.size
        }
        return players[currentPlayer]!!
    }

    private fun processResponse(): Int {
        return readLine()!!.toInt()
    }

    private fun List<PlayableCard>.toPrettyString() = this.joinToString(" ") { "" + it.value + ":" + it.text }
}
package app

import domain.PlayableCard
import game.Player
import game.PlayerManager
import game.TargetManager

class CLITargetManager(private val playerManager: PlayerManager) : TargetManager {

    override fun getPlayerTarget(player: Player): Player {
        println("Please select a target Player: ")
        println(getEligiblePlayersAsString(player))
        val playerIndex = processResponse()
        return playerManager.getAlivePlayers()[playerIndex]!!
    }

    private fun processResponse(): Int {
        return readLine()!!.toInt()
    }

    private fun getEligiblePlayersAsString(player: Player): String {
        return playerManager.getAlivePlayers()
            .filter { it.value != player }
            .filter { it.value.isTargetable }
            .map { "" + it.key + ":" + it.value.name }
            .joinToString(" ")
    }

    override fun getCardTarget(player: Player): PlayableCard {
        println("Please name a Card")
        println(getEligibleCardsAsString())
        val cardValue = processResponse()
        return PlayableCard.fromCardValue(cardValue)!!
    }

    private fun getEligibleCardsAsString(): Any? {
       return ("2:Clown 3:Knight 4:Priestess 5:Wizard 6:General 7:Minister 8:Princess")
    }
}
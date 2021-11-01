package game

import domain.Deck
import domain.PlayableCard
import domain.cards.*

class Game(
    private val deck: Deck,
    private val targetManager: TargetManager,
    private val playerManager: PlayerManager,
    private val boardManager: BoardManager
) {

    private fun Soldier.play(player: Player) {
        val targetPlayer = targetManager.getPlayerTarget(player)
        val targetCard = targetManager.getCardTarget(player)
        boardManager.logPlayEvent(player, this.text, targetCard.text, targetPlayer)
        if (targetPlayer.has(targetCard)) {
            eliminate(targetPlayer)
        }
        else {
            boardManager.nothing()
        }
    }

    private fun Clown.play(player: Player) {
        val targetPlayer = targetManager.getPlayerTarget(player)
        boardManager.logPlayEvent(player, this.text, null, targetPlayer)
        lookAtHand(player, targetPlayer)
    }

    private fun Knight.play(player: Player) {
        val targetPlayer = targetManager.getPlayerTarget(player)
        boardManager.logPlayEvent(player, this.text, null, targetPlayer)
        lookAtHand(player, targetPlayer)
        lookAtHand(targetPlayer, player)
        when {
            targetPlayer.handValue() < player.handValue() -> {
                eliminate(targetPlayer)
            }
            targetPlayer.handValue() > player.handValue() -> {
                eliminate(player)
            }
            else -> {
                boardManager.nothing()
            }
        }
    }

    private fun Priestess.play(player: Player) {
        boardManager.logPlayEvent(player, this.text, null, null)
        player.isTargetable = false
    }

    private fun Wizard.play(player: Player) {
        val targetPlayer = targetManager.getPlayerTarget(player)
        boardManager.logPlayEvent(player, this.text, null, targetPlayer)
        discardHand(targetPlayer)
    }

    private fun General.play(player: Player) {
        val targetPlayer = targetManager.getPlayerTarget(player)
        boardManager.logPlayEvent(player, this.text, null, targetPlayer)
        exchangeHands(targetPlayer, player)
    }

    private fun exchangeHands(targetPlayer: Player, player: Player) {
        boardManager.logExchangeHandEvent(targetPlayer, player)
        targetPlayer.exchangesHandsWith(player)
    }

    private fun Minister.play(player: Player) {
        boardManager.logPlayEvent(player, this.text, null, null)
    }

    private fun Princess.play(player: Player) {
        boardManager.logPlayEvent(player, this.text, null, null)
        eliminate(player)
    }

    private fun turn(player: Player) {
        boardManager.beginTurn(player)
        removePriestessStatus(player)
        player.draw(deck.topDeck())
        checkMinisterRule(player)
        if (!player.isEliminated) {
            val playedCard: PlayableCard = playerManager.getPlayedCard(player)
            play(player, playedCard)
            when (playedCard) {
                is Soldier -> playedCard.play(player)
                is Clown -> playedCard.play(player)
                is Knight -> playedCard.play(player)
                is Priestess -> playedCard.play(player)
                is Wizard -> playedCard.play(player)
                is General -> playedCard.play(player)
                is Minister -> playedCard.play(player)
                is Princess -> playedCard.play(player)
            }
        }
    }

    private fun play(player: Player, playedCard: PlayableCard) {
        player.discardOrPlay(playedCard)
        boardManager.hasPlayed(player, playedCard)
    }


    private fun eliminate(targetPlayer: Player) {
        playerManager.eliminate(targetPlayer)
        boardManager.eliminatePlayer(targetPlayer)
    }

    private fun removePriestessStatus(player: Player) {
        player.isTargetable = true
    }

    private fun checkMinisterRule(player: Player) {
        if (player.has(Minister()) && player.handValue() >= 12) {
            eliminate(player)
        }
    }

    private fun lookAtHand(player: Player, playerTarget: Player) {
        val shownCard = playerTarget.showHand()
        playerManager.show(shownCard, player)
    }


    private fun discardHand(targetPlayer: Player) {
        val discardedCard = targetPlayer.discardHand()
        boardManager.logDiscard(targetPlayer, discardedCard)
        if (discardedCard == Princess()) {
            eliminate(targetPlayer)
        }
    }


    fun play() {
        playerManager.getAlivePlayers().map { it.value.draw(deck.topDeck()) }
        while (!deck.isEmpty() || playerManager.getAlivePlayers().size > 1) {
            val nextPlayer = playerManager.nextPlayer()
            turn(nextPlayer)
        }
        boardManager.endGame(playerManager.getAlivePlayers().values.maxByOrNull { it.handValue() }!!)
    }
}
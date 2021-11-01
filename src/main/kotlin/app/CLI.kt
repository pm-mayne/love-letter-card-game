package app

import domain.Deck
import domain.PlayableCard
import domain.cards.*
import game.Game
import game.Player
import java.util.*

fun main() {

    val cardList = listOf(
        Soldier(),
        Soldier(),
        Soldier(),
        Soldier(),
        Soldier(),
        Clown(),
        Clown(),
        Knight(),
        Knight(),
        Priestess(),
        Priestess(),
        Wizard(),
        Wizard(),
        General(),
        Minister(),
        Princess()
    )
        .shuffled()
        .subList(0, 15)

    val stack = Stack<PlayableCard>()
    stack.addAll(cardList)
    val deck = Deck(stack)

    val players = mapOf(
        0 to Player("Moggow"),
        1 to Player("Tahuri"),
        2 to Player("Kennouf"),
        3 to Player("Marsoupe")
    )
    println("Let's play!")
    val playerManager = CLIPlayerManager(
        players
    )

    val targetManager = CLITargetManager(playerManager)

    val boardManager = CLIBoardManager()

    val game = Game(deck, targetManager, playerManager, boardManager)
    game.play()
}
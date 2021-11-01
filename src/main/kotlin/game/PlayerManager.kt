package game

import domain.PlayableCard

interface PlayerManager {

    fun eliminate(player: Player)

    fun show(shownCard: PlayableCard?, player: Player)
    fun getPlayedCard(player: Player): PlayableCard
    fun getAlivePlayers(): Map<Int, Player>
    fun nextPlayer() : Player

}

package game

import domain.PlayableCard

interface TargetManager {
    fun getPlayerTarget(player: Player): Player
    fun getCardTarget(player: Player): PlayableCard
}

package net.pvprealms.guilds.service.economy

import java.util.*

class PlayerValuationTracker {

    private val lastSeenValue = mutableMapOf<UUID, Double>()

    fun update(playerId: UUID, newValuation: Double) {
        lastSeenValue[playerId] = newValuation
    }

    fun getLastSeen(playerId: UUID): Double? {
        return lastSeenValue[playerId]
    }

    fun getIncrease(playerId: UUID, current: Double): Double? {
        val last = getLastSeen(playerId) ?: return null
        return (current - last).takeIf { it > 0 }
    }
}
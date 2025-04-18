package net.pvprealms.guilds.service.core

import java.util.UUID

class GuildBanService {

    private val bannedPlayers = mutableMapOf<UUID, MutableSet<String>>()

    fun ban(playerId: UUID, guildId: String) {
        bannedPlayers.computeIfAbsent(playerId) { mutableSetOf() }.add(guildId.lowercase())
    }

    fun unban(playerId: UUID, guildId: String) {
        bannedPlayers[playerId]?.remove(guildId.lowercase())
    }

    fun isBanned(playerId: UUID, guildId: String): Boolean {
        return bannedPlayers[playerId]?.contains(guildId.lowercase()) ?: false
    }

}
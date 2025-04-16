package net.pvprealms.guilds.service

import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.model.Guild
import org.bukkit.Bukkit

class GuildAssignmentService(
    private val storage: GuildStorageService,
    private val registry: GuildRegistry
) {

    fun assignPlayer(): Guild {
        val ignoreInactive = ConfigManager.shouldIgnoreInactive()
        val inactiveThreshold = ConfigManager.getInactiveThresholdMs()
        val now = System.currentTimeMillis()

        val counts = storage.getPlayerGuilds()
            .filter { (uuid, _) ->
                if (!ignoreInactive) return@filter true
                val lastSeen = Bukkit.getOfflinePlayer(uuid).lastLogin
                (now - lastSeen) <= inactiveThreshold
            }
            .values
            .groupingBy { it.id }
            .eachCount()

        val least = counts.minByOrNull { it.value }?.value ?: 0
        val candidates = registry.getGuilds().filter { (counts[it.id] ?: 0) == least }

        return candidates.random()
    }
}
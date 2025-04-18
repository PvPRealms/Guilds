package net.pvprealms.guilds.service.assignment

import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.core.GuildServices
import net.pvprealms.guilds.model.Guild
import org.bukkit.Bukkit

class GuildAssignmentService(private val services: GuildServices) {
    fun assignPlayerGuild(): Guild {
        val ignoreInactive = ConfigManager.shouldIgnoreInactive()
        val inactiveThreshold = ConfigManager.getInactiveThresholdMs()
        val now = System.currentTimeMillis()

        val counts = services.guildStorageService.getPlayerGuilds()
            .filter { (uuid, _) ->
                if (!ignoreInactive) return@filter true
                val lastSeen = Bukkit.getOfflinePlayer(uuid).lastLogin
                (now - lastSeen) <= inactiveThreshold
            }
            .values
            .groupingBy { it.id }
            .eachCount()

        val least = counts.minByOrNull { it.value }?.value ?: 0
        val candidates = services.guildService.getGuilds().filter { (counts[it.id] ?: 0) == least }

        return candidates.random()
    }
}
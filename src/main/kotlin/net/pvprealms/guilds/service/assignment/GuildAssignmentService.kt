package net.pvprealms.guilds.service.assignment

import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.model.Guild
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.GuildStorageService
import org.bukkit.Bukkit

class GuildAssignmentService(
    private val guildService: GuildService,
    private val storageService: GuildStorageService
) {
    fun assignPlayerGuild(): Guild {
        val ignoreInactive = ConfigManager.shouldIgnoreInactive()
        val inactiveThreshold = ConfigManager.getInactiveThresholdMs()
        val now = System.currentTimeMillis()

        val counts = storageService.getPlayerGuilds()
            .filter { (uuid, _) ->
                if (!ignoreInactive) return@filter true
                val lastSeen = Bukkit.getOfflinePlayer(uuid).lastLogin
                (now - lastSeen) <= inactiveThreshold
            }
            .values
            .groupingBy { it.id }
            .eachCount()

        val least = counts.minByOrNull { it.value }?.value ?: 0
        val candidates = guildService.getGuilds().filter { (counts[it.id] ?: 0) == least }

        return candidates.random()
    }
}
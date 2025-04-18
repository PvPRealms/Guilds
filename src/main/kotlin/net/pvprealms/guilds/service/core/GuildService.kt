package net.pvprealms.guilds.service.core

import net.pvprealms.guilds.model.Guild
import net.pvprealms.guilds.service.GuildServices
import org.bukkit.entity.Player
import java.util.*

class GuildService(
    private val services: GuildServices
) {

    fun load() {
        services.guildStorageService.loadPlayerGuilds()
    }

    fun getGuild(id: String): Guild? = services.guildRegistry.getGuild(id)

    fun getGuilds(): Collection<Guild> = services.guildRegistry.getGuilds()

    fun getPlayerGuild(player: Player): Guild {
        return services.guildStorageService.getPlayerGuild(player.uniqueId) ?: assignPlayerGuild(player)
    }

    fun getPlayersInGuild(guild: Guild): List<UUID> {
        return services.guildStorageService.getPlayerGuilds().filterValues { it.id == guild.id }.keys.toList()
    }

    fun assignPlayerGuild(player: Player): Guild {
        val guild = services.guildAssignmentService.assignPlayerGuild()
        services.guildStorageService.savePlayerGuilds(player.uniqueId, guild)
        return guild
    }

    fun setPlayerGuild(player: Player, guildId: String): Boolean {
        val guild = getGuild(guildId) ?: return false
        services.guildStorageService.savePlayerGuilds(player.uniqueId, guild)
        return true
    }

}
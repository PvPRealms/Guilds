package net.pvprealms.guilds.service

import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.model.Guild
import net.pvprealms.guilds.service.assignment.GuildAssignmentService
import net.pvprealms.guilds.service.economy.GuildEconomyStorageService
import org.bukkit.entity.Player
import java.util.*

class GuildService(
    private val storageService: GuildStorageService,
    private val assignmentService: GuildAssignmentService,
    private val economyStorageService: GuildEconomyStorageService
) {

    private var guilds: Map<String, Guild> =
        ConfigManager.getGuildMap().mapValues { Guild(it.key.lowercase(), it.value) }

    fun loadServices() {
        storageService.loadPlayerGuilds()
        economyStorageService.loadGuildValuation()
    }

    /* Return Guild by ID */
    fun getGuild(guildId: String): Guild? {
        return guilds[guildId.lowercase()]
    }

    /* Return all Guilds */
    fun getGuilds(): Collection<Guild> {
        return guilds.values
    }

    /* Return a player's Guild. If they are not assigned to one, trigger assignment. */
    fun getPlayerGuild(player: Player): Guild {
        return storageService.getPlayerGuild(player.uniqueId) ?: assignPlayerGuild(player)
    }

    /* Return all player's and their Guild(s) */
    fun getPlayerGuilds(): Map<UUID, Guild> {
        return storageService.getPlayerGuilds()
    }

    /* Return all player's in a Guild */
    fun getPlayersInGuild(guild: Guild): List<UUID> {
        return storageService.getPlayerGuilds().filterValues { it.id == guild.id }.keys.toList()
    }

    /* Set a player's Guild on first join */
    fun assignPlayerGuild(player: Player): Guild {
        // TODO: Maybe check if hasGuild first?
        val guild = assignmentService.assignPlayerGuild()
        storageService.savePlayerGuilds(player.uniqueId, guild)
        return guild
    }

    /* Set a player's Guild */
    fun setPlayerGuild(player: Player, guildId: String): Boolean {
        val guild = getGuild(guildId) ?: return false
        storageService.savePlayerGuilds(player.uniqueId, guild)
        return true
    }

}
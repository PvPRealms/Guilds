package net.pvprealms.guilds.service

import net.pvprealms.guilds.model.Guild
import org.bukkit.entity.Player
import java.util.*


class GuildService(
    private val registry: GuildRegistry,
    private val storage: GuildStorageService,
    private val assignment: GuildAssignmentService
) {

    fun load() {
        registry.load()
        storage.load()
    }

    fun getPlayerGuild(player: Player): Guild {
        return storage.getPlayerGuild(player.uniqueId) ?: assignGuildAndSave(player)
    }

    fun assignGuild(player: Player) {
        getPlayerGuild(player)
    }

    fun assignGuildAndSave(player: Player): Guild {
        val guild = assignment.assignPlayer()
        storage.save(player.uniqueId, guild)
        return guild
    }

    fun setGuild(player: Player, guildId: String): Boolean {
        val guild = registry.getGuildById(guildId) ?: return false
        storage.save(player.uniqueId, guild)
        return true
    }

    fun getGuildMembers(guild: Guild): List<UUID> {
        return storage.getPlayerGuilds().filterValues { it.id == guild.id }.keys.toList()
    }

    fun getGuilds(): Collection<Guild> = registry.getGuilds()
}
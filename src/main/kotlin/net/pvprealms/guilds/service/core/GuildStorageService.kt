package net.pvprealms.guilds.service.core

import net.pvprealms.guilds.model.Guild
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class GuildStorageService(
    private val plugin: JavaPlugin,
    private val guildRegistry: GuildRegistry
) {

    private val playerGuilds = mutableMapOf<UUID, Guild>()

    fun getPlayerGuild(uuid: UUID): Guild? = playerGuilds[uuid]

    fun getPlayerGuilds(): Map<UUID, Guild> = playerGuilds.toMap()

    fun hasGuild(uuid: UUID): Boolean = playerGuilds.containsKey(uuid)

    fun setGuild(uuid: UUID, guild: Guild) {
        playerGuilds[uuid] = guild
    }

    fun loadFromConfig(file: File, config: YamlConfiguration) {
        config.getKeys(false).forEach { key ->
            val uuid = UUID.fromString(key)
            val guildId = config.getString(key) ?: return@forEach
            val guild = guildRegistry.getGuild(guildId) ?: return@forEach
            playerGuilds[uuid] = guild
        }
    }

    fun writeToConfig(config: YamlConfiguration) {
        playerGuilds.forEach { (uuid, guild) ->
            config.set(uuid.toString(), guild.id)
        }
    }
}
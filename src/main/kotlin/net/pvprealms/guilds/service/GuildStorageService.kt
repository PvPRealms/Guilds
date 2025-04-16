package net.pvprealms.guilds.service

import net.pvprealms.guilds.model.Guild
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class GuildStorageService(
    private val plugin: JavaPlugin,
    private val guildService: GuildService
) {

    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    private val playerGuilds = mutableMapOf<UUID, Guild>()

    /* Return a player's Guild */
    fun getPlayerGuild(uuid: UUID): Guild? {
        return playerGuilds[uuid]
    }

    /* Return all player's and their Guild(s) */
    fun getPlayerGuilds(): Map<UUID, Guild> {
        return playerGuilds
    }

    /* Return whether a player is in a Guild */
    fun hasGuild(player: Player): Boolean {
        return playerGuilds.containsKey(player.uniqueId)
    }

    /* Load player-guilds.yml file */
    fun loadPlayerGuilds() {
        file = File(plugin.dataFolder, "player-guilds.yml")

        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        config = YamlConfiguration.loadConfiguration(file)

        for (key in config.getKeys(false)) {
            val uuid = UUID.fromString(key)
            val guildId = config.getString(key) ?: continue
            val guild = guildService.getGuild(guildId) ?: continue

            playerGuilds[uuid] = guild
        }
    }

    /* Save information to player-guilds.yml file */
    fun savePlayerGuilds(uuid: UUID, guild: Guild) {
        playerGuilds[uuid] = guild
        config.set(uuid.toString(), guild.id)
        config.save(file)
    }

}
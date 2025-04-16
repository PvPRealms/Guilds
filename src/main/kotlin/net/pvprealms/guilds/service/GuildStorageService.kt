package net.pvprealms.guilds.service

import net.pvprealms.guilds.model.Guild
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class GuildStorageService(
    private val plugin: JavaPlugin,
    private val registry: GuildRegistry
) {

    private lateinit var dataFile: File
    private lateinit var config: YamlConfiguration
    private val playerGuilds = mutableMapOf<UUID, Guild>()

    fun load() {
        dataFile = File(plugin.dataFolder, "player-guilds.yml")
        if (!dataFile.exists()) {
            dataFile.parentFile.mkdirs()
            dataFile.createNewFile()
        }

        config = YamlConfiguration.loadConfiguration(dataFile)

        for (key in config.getKeys(false)) {
            val uuid = UUID.fromString(key)
            val guildId = config.getString(key) ?: continue
            val guild = registry.getGuildById(guildId) ?: continue

            playerGuilds[uuid] = guild
        }
    }

    fun save(uuid: UUID, guild: Guild) {
        playerGuilds[uuid] = guild
        config.set(uuid.toString(), guild.id)
        config.save(dataFile)
    }

    /* Return the player's Guild */
    fun getPlayerGuild(uuid: UUID): Guild? = playerGuilds[uuid]

    /* Return all player's and their Guild(s) */
    fun getPlayerGuilds(): Map<UUID, Guild> = playerGuilds

    /* Check whether a player belongs to a Guild */
    fun hasGuild(player: Player): Boolean = playerGuilds.containsKey(player.uniqueId)
}
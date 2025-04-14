package net.pvprealms.guilds.core

import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.model.Guild
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.LinkedHashMap
import java.util.UUID

class GuildManager(
    private val plugin: JavaPlugin
) {

    private val guilds: Map<String, Guild> = ConfigManager.getGuildMap()
        .mapValues { Guild(it.key.lowercase(), it.value) }

    private val playerGuilds = mutableMapOf<UUID, Guild>()
    private lateinit var dataFile: File
    private lateinit var dataConfig: YamlConfiguration

    init {
        loadData()
    }

    /* Return a collection of all Guilds. */
    fun getGuilds(): Collection<Guild> = guilds.values

    /* Return the Guild of a specified player. */
    fun getGuildOf(player: Player): Guild {
        return playerGuilds.computeIfAbsent(player.uniqueId) {
            val assigned = assignBalancedGuild()
            savePlayer(player.uniqueId, assigned.id)
            assigned
        }
    }

    /* If a player is not in a Guild, force them to join one. */
    fun assignIfMissing(player: Player) {
        getGuildOf(player)
    }

    /* Set the player's Guild */
    fun setGuild(player: Player, guildId: String): Boolean {
        val guild = guilds[guildId.lowercase()] ?: return false
        playerGuilds[player.uniqueId] = guild
        savePlayer(player.uniqueId, guild.id)
        return true
    }

    /* Assign a player to a Guild with the least amount of people in. */
    private fun assignBalancedGuild(): Guild {
        val counts = playerGuilds.values.groupingBy { it.id }.eachCount()
        val leastCount = counts.minByOrNull { it.value }?.value ?: 0
        val candidates = guilds.values.filter { (counts[it.id] ?: 0) == leastCount }
        return candidates.random()
    }

    private fun savePlayer(uuid: UUID, guildId: String) {
        dataConfig.set(uuid.toString(), guildId)
        try {
            dataConfig.save(dataFile)
        } catch (e: Exception) {
            plugin.logger.warning("Failed to save player-guilds.yml: ${e.message}")
        }
    }

    private fun loadData() {
        dataFile = File(plugin.dataFolder, "player-guilds.yml")
        if (!dataFile.exists()) {
            dataFile.parentFile.mkdirs()
            dataFile.createNewFile()
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile)

        for (key in dataConfig.getKeys(false)) {
            val uuid = try {
                UUID.fromString(key)
            } catch (e: IllegalArgumentException) {
                continue
            }

            val guildId = dataConfig.getString(key)?.lowercase() ?: continue
            val guild = guilds[guildId] ?: continue

            playerGuilds[uuid] = guild
        }

        plugin.logger.info("Loaded ${playerGuilds.size} player guilds.")
    }
}
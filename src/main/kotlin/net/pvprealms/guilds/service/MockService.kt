package net.pvprealms.guilds.service

import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.model.Guild
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.UUID

// new registry service
// new main service
class MockGuildService(
    private val storageService: MockStorageService,
    private val assignmentService: MockAssignmentService
) {

    private var guilds: Map<String, Guild> =
        ConfigManager.getGuildMap().mapValues { Guild(it.key.lowercase(), it.value) }

    fun loadServices() {
        storageService.loadPlayerGuilds()
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

class MockStorageService(private val plugin: JavaPlugin, private val guildService: MockGuildService) {

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
            val guild = guildService.getGuild(guildId)

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

class MockAssignmentService(
    private val guildService: MockGuildService,
    private val storageService: MockStorageService
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
package net.pvprealms.guilds.service.economy

import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class GuildEconomyStorageService(private val plugin: JavaPlugin) {

    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    private val guildValuations = mutableMapOf<String, Double>()

    private val lock = Any()

    /* Return a Guild's valuation */
    fun getValuation(guildId: String): Double {
        synchronized(lock) {
            return guildValuations.getOrDefault(guildId, 0.0)
        }
    }

    /* Returns a map of all Guild valuations */
    fun getValuations(): Map<String, Double> {
        synchronized(lock) {
            return guildValuations.toMap()
        }
    }

    /* Set a Guild's value to a static amount */
    fun setValuation(guildId: String, value: Double) {
        synchronized(lock) {
            guildValuations[guildId] = value.coerceAtLeast(0.0)
        }
    }

    fun increaseGuildValue(guildId: String, amount: Double) {
        synchronized(lock) {
            val current = getValuation(guildId)
            setValuation(guildId, current + amount)
        }
    }

    fun decreaseGuildValue(guildId: String, amount: Double) {
        val current = getValuation(guildId)
        setValuation(guildId, current - amount)
    }

    /* Load guild-valuations.yml file */
    fun loadGuildValuation() {
        file = File(plugin.dataFolder, "guild-valuation.yml")

        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        config = YamlConfiguration.loadConfiguration(file)

        /* Access valuations based on a Guild's ID of at least $0.0. */
        config.getKeys(false).forEach { guildId ->
            guildValuations[guildId] = config.getDouble(guildId, 0.0)
                .coerceAtLeast(0.0)
        }
    }

    /* Save information to guild-valuations.yml file */
    fun saveGuildValuation(guildId: String) {
        if (!::config.isInitialized) return

        config.set(guildId, guildValuations[guildId])
        config.save(file)
    }

    /* Save all available information to guild-valuations.yml file */
    fun saveAllGuildValuation() {
        synchronized(lock) {
            if (!::config.isInitialized) return

            guildValuations.forEach { (id, value) ->
                config.set(id, value)
            }

            config.save(file)
        }

        plugin.logger.info("[Guilds] Saved Guild valuations.")
    }

    fun startAutoSaveTask(intervalTicks: Long = 20 * 60L) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            saveAllGuildValuation()
        }, intervalTicks, intervalTicks)
    }

}
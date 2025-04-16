package net.pvprealms.guilds.service.economy

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class GuildEconomyStorageService(private val plugin: JavaPlugin) {

    private lateinit var config: YamlConfiguration
    private lateinit var file: File

    private val valuations = mutableMapOf<String, Double>()

    fun load() {
        file = File(plugin.dataFolder, "guild-valuation.yml")
        if (!file.exists()) {
            file.parentFile.mkdirs()
            plugin.saveResource("guild-valuation.yml", false)
        }

        config = YamlConfiguration.loadConfiguration(file)

        config.getKeys(false).forEach { guildId ->
            valuations[guildId] = config.getDouble(guildId, 0.0).coerceAtLeast(0.0)
        }
    }

    fun save(guildId: String) {
        if (!::config.isInitialized) {
            plugin.logger.warning("[Guilds] Tried to save config before it was loaded.")
            return
        }
        config.set(guildId, valuations[guildId])
        config.save(file)
    }

    fun getValuation(guildId: String): Double {
        return valuations.getOrDefault(guildId, 0.0)
    }

    fun setValuation(guildId: String, value: Double) {
        valuations[guildId] = value.coerceAtLeast(0.0)
        save(guildId)
    }

    fun getValuations(): Map<String, Double> = valuations
}
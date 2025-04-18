package net.pvprealms.guilds.service.contribution

import net.pvprealms.guilds.model.ContributionSource
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class GuildContributionRegistry(private val plugin: JavaPlugin) {

    private val values = mutableMapOf<String, ContributionSource>()

    fun loadConfig() {
        val file = File(plugin.dataFolder, "values.yml")
        if (!file.exists()) {
            plugin.saveResource("values.yml", false)
        }

        val config = YamlConfiguration.loadConfiguration(file)

        for (category in listOf("blocks", "mobs", "players", "crops", "fishing")) {
            val section = config.getConfigurationSection(category) ?: continue

            for (key in section.getKeys(false)) {
                val value = section.getDouble("$key.value")
                val contributionRate = section.getDouble("$key.contribution-rate")
                values["$category:$key"] = ContributionSource(value, contributionRate)
            }
        }

        plugin.logger.info("[Guilds] Loaded ${values.size} contribution entries.")
    }

    fun getValue(category: String, key: String): ContributionSource? {
        return values["$category:${key.uppercase()}"]
    }
}
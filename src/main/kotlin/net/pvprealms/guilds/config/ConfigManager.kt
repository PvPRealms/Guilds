package net.pvprealms.guilds.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

object ConfigManager {

    private lateinit var config: YamlConfiguration

    fun load(plugin: JavaPlugin) {
        val file = File(plugin.dataFolder, "config.yml")
        if (!file.exists()) plugin.saveResource("config.yml", false)
        config = YamlConfiguration.loadConfiguration(file)
    }

    fun getGuildMap(): Map<String, String> {
        val section = config.getConfigurationSection("guilds") ?: return emptyMap()
        return section.getKeys(false).associateWith { key ->
            section.getConfigurationSection(key)?.getString("name") ?: key
        }
    }
}
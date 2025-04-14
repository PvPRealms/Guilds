package net.pvprealms.guilds.config

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

object Messages {

    private lateinit var config: YamlConfiguration
    private var prefix: String = ""

    fun load(plugin: JavaPlugin) {
        val file = File(plugin.dataFolder, "messages.yml")
        if (!file.exists()) plugin.saveResource("messages.yml", false)
        config = YamlConfiguration.loadConfiguration(file)

        prefix = config.getString("prefix") ?: ""
    }

    fun format(path: String, replacements: Map<String, String> = emptyMap()): Component {
        var raw = config.getString(path) ?: ""
        raw = raw.replace("<prefix>", prefix)
        replacements.forEach { (k, v) -> raw = raw.replace("{$k}", v) }
        return MiniMessage.miniMessage().deserialize(raw)
    }
}
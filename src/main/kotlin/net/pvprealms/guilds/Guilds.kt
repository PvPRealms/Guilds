package net.pvprealms.guilds

import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.config.Messages
import net.pvprealms.guilds.core.CommandRegistrar
import net.pvprealms.guilds.core.GuildManager
import net.pvprealms.guilds.core.ListenerRegistrar
import org.bukkit.plugin.java.JavaPlugin

class Guilds : JavaPlugin() {

    lateinit var guildManager: GuildManager

    override fun onEnable() {
        ConfigManager.load(this)
        Messages.load(this)

        guildManager = GuildManager(this)

        CommandRegistrar.register(this, guildManager)
        ListenerRegistrar.register(this, guildManager)

        logger.info("[Guilds] Plugin is enabled.")
    }

    override fun onDisable() {
        logger.info("[Guilds] Plugin is disabled.")
    }
}

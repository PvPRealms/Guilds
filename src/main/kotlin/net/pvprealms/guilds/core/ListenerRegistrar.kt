package net.pvprealms.guilds.core

import net.pvprealms.guilds.listener.FirstJoinListener
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

object ListenerRegistrar {

    fun register(plugin: JavaPlugin, guildManager: GuildManager) {
        val pluginManager: PluginManager = plugin.server.pluginManager

        /* Register Events */
        pluginManager.registerEvents(FirstJoinListener(guildManager), plugin)
    }
}
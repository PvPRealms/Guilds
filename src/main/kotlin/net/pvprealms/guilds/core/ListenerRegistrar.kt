package net.pvprealms.guilds.core

import net.pvprealms.guilds.listener.*
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

object ListenerRegistrar {
    fun register(plugin: JavaPlugin, services: GuildServices) {

        val pluginManager: PluginManager = plugin.server.pluginManager

        /* Register Events */
        pluginManager.registerEvents(FirstJoinListener(services), plugin)
        pluginManager.registerEvents(BlockBreakListener(services), plugin)
        pluginManager.registerEvents(CropHarvestListener(services), plugin)
        pluginManager.registerEvents(EntityKillListener(services), plugin)
        pluginManager.registerEvents(FishingListener(services), plugin)
        pluginManager.registerEvents(PlayerDeathListener(services), plugin)
    }
}
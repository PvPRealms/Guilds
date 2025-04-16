package net.pvprealms.guilds.core

import net.pvprealms.guilds.listener.FirstJoinListener
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import net.pvprealms.guilds.service.economy.PlayerValuationTracker
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

object ListenerRegistrar {

    fun register(
        plugin: JavaPlugin,
        service: GuildService,
        economyService: GuildEconomyService,
        valuationTracker: PlayerValuationTracker
    ) {
        val pluginManager: PluginManager = plugin.server.pluginManager

        /* Register Events */
        pluginManager.registerEvents(FirstJoinListener(service, economyService, valuationTracker), plugin)
    }
}
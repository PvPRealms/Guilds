package net.pvprealms.guilds.core

import co.aikar.commands.PaperCommandManager
import net.pvprealms.guilds.command.*
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object CommandRegistrar {

    fun register(
        plugin: JavaPlugin,
        service: GuildService,
        economyService: GuildEconomyService,
    ) {
        val commandManager = PaperCommandManager(plugin)
        commandManager.enableUnstableAPI("help")

        /* Custom Tab Completions */
        commandManager.commandCompletions.registerCompletion("guilds") {
            service.getGuilds().map { it.id }
        }

        commandManager.commandCompletions.registerCompletion("players") {
            Bukkit.getOnlinePlayers().mapNotNull { it.name }
        }

        commandManager.commandCompletions.registerCompletion("offlinePlayers") {
            Bukkit.getOfflinePlayers().mapNotNull { it.name }
        }

        /* Register Commands */
        commandManager.registerCommand(GuildInfoCommand(service))
        commandManager.registerCommand(GuildSetCommand(service))
        commandManager.registerCommand(GuildReloadCommand(plugin))
        commandManager.registerCommand(GuildDepositCommand(service, economyService))
        commandManager.registerCommand(GuildValuationCommand(service, economyService))
        commandManager.registerCommand(GuildCommand(service))
    }
}
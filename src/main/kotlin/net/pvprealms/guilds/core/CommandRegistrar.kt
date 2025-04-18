package net.pvprealms.guilds.core

import co.aikar.commands.PaperCommandManager
import net.pvprealms.guilds.command.*
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object CommandRegistrar {

    fun register(plugin: JavaPlugin, services: GuildServices) {

        val commandManager = PaperCommandManager(plugin)
        commandManager.enableUnstableAPI("help")

        /* Custom Tab Completions */
        commandManager.commandCompletions.registerCompletion("guilds") {
            services.guildService.getGuilds().map { it.id }
        }

        commandManager.commandCompletions.registerAsyncCompletion("players") {
            Bukkit.getOnlinePlayers().map { it.name }
        }

        commandManager.commandCompletions.registerCompletion("offlinePlayers") {
            Bukkit.getOfflinePlayers().mapNotNull { it.name }
        }

        /* Register Commands */
        commandManager.registerCommand(GuildInfoCommand(services))
        commandManager.registerCommand(GuildSetCommand(services))
        commandManager.registerCommand(GuildReloadCommand(plugin))
        commandManager.registerCommand(GuildDepositCommand(services))
        commandManager.registerCommand(GuildValuationCommand(services))
        commandManager.registerCommand(GuildCommand(services))
    }
}
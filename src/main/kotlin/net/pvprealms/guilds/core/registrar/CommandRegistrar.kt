package net.pvprealms.guilds.core.registrar

import co.aikar.commands.PaperCommandManager
import net.pvprealms.guilds.command.*
import net.pvprealms.guilds.command.admin.*
import net.pvprealms.guilds.service.GuildServices
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
        commandManager.registerCommand(GuildReloadCommand(plugin))
        commandManager.registerCommand(GuildDepositCommand(services))
        commandManager.registerCommand(GuildValueCommand(services))
        commandManager.registerCommand(GuildCommand(services))

        commandManager.registerCommand(GuildAdminCommand())
        commandManager.registerCommand(GuildAdminSetCommand(services))
        commandManager.registerCommand(GuildAdminContributionCommand(services))
        commandManager.registerCommand(GuildAdminBanCommand(services))
        commandManager.registerCommand(GuildAdminUnbanCommand(services))
    }
}
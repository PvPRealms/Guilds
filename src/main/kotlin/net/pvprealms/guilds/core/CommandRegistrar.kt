package net.pvprealms.guilds.core

import co.aikar.commands.PaperCommandManager
import net.pvprealms.guilds.command.GuildInfoCommand
import net.pvprealms.guilds.command.GuildReloadCommand
import net.pvprealms.guilds.command.GuildSetCommand
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object CommandRegistrar {

    fun register(plugin: JavaPlugin, guildManager: GuildManager) {
        val commandManager = PaperCommandManager(plugin)
        commandManager.enableUnstableAPI("help")

        /* Custom Tab Completions */
        commandManager.commandCompletions.registerCompletion("guilds") {
            guildManager.getGuilds().map { it.id }
        }

        commandManager.commandCompletions.registerCompletion("offlinePlayers") {
            Bukkit.getOfflinePlayers().mapNotNull { it.name }
        }

        /* Register Commands */
        commandManager.registerCommand(GuildInfoCommand(guildManager))
        commandManager.registerCommand(GuildSetCommand(guildManager, plugin))
        commandManager.registerCommand(GuildReloadCommand(plugin, guildManager))
    }
}
package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.config.Messages
import net.pvprealms.guilds.core.GuildManager
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@CommandAlias("g|guild")
@Subcommand("reload")
@Description("Reloads the Guild plugin configuration")
@CommandPermission("guilds.admin")
class GuildReloadCommand(
    private val plugin: JavaPlugin,
    private val guildManager: GuildManager
): BaseCommand() {

    @Default
    fun onGuildReload(sender: Player) {
        ConfigManager.load(plugin)
        Messages.load(plugin)

        sender.sendMessage(Messages.format("reload"))
    }
}
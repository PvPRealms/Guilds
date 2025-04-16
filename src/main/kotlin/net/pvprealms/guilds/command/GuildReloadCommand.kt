package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.config.MessageManager
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@CommandAlias("g|guild")
@Subcommand("reload")
@Description("Reloads the Guild plugin configuration")
@CommandPermission("guilds.admin")
class GuildReloadCommand(
    private val plugin: JavaPlugin,
): BaseCommand() {
    @Default
    fun onReload(sender: Player) {
        ConfigManager.load(plugin)
        MessageManager.load(plugin)

        sender.sendMessage(MessageManager.format("plugin.reload"))
    }
}
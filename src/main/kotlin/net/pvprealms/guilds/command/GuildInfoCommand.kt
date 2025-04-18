package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import net.pvprealms.guilds.core.GuildServices
import net.pvprealms.guilds.gui.GuildInfoGui
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@Subcommand("info")
@Description("View players in your current Guild")
@CommandPermission("guilds.player")
class GuildInfoCommand(private val services: GuildServices): BaseCommand() {
    @Default
    fun onInfo(player: Player) {
        GuildInfoGui.open(player, services)
    }
}
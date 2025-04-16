package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import net.pvprealms.guilds.gui.GuildGui
import net.pvprealms.guilds.service.GuildService
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@Subcommand("view")
@Description("View details about your Guild")
@CommandPermission("guilds.player")
class GuildCommand(
    private val service: GuildService
): BaseCommand() {
    @Default
    fun onGuild(player: Player) {
        GuildGui.open(player, service)
    }
}
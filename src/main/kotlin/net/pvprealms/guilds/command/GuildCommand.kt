package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import net.pvprealms.guilds.service.GuildServices
import net.pvprealms.guilds.gui.GuildGui
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@Description("View details about your Guild")
@CommandPermission("guilds.player")
class GuildCommand(private val services: GuildServices): BaseCommand() {
    @Default
    fun onGuild(player: Player) {
        GuildGui.open(player, services)
    }
}
package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import net.pvprealms.guilds.config.Messages
import net.pvprealms.guilds.model.Guild
import net.pvprealms.guilds.core.GuildManager
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@Subcommand("info")
@Description("View your current Guild")
@CommandPermission("guilds.player")
class GuildInfoCommand(
    private val guildManager: GuildManager
): BaseCommand() {

    @Default
    fun onGuildInfo(player: Player) {
        val guild = guildManager.getGuildOf(player)
        infoMessage(player, guild)
    }

    private fun infoMessage(player: Player, guild: Guild) {
        player.sendMessage(Messages.format("info", mapOf("guild" to guild.displayName)))
    }
}
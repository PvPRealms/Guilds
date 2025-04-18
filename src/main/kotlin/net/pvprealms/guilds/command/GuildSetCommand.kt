package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.core.GuildServices
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@CommandPermission("guilds.admin")
class GuildSetCommand(private val services: GuildServices): BaseCommand() {
    @Subcommand("set")
    @CommandCompletion("@players @guilds")
    @Syntax("<player> <guild>")
    @Description("Sets a player's Guild")
    fun onSetCommand(
        sender: Player,
        @Name("player") player: Player,
        @Name("guild") guildId: String
    ) {
        val success = services.guildService.setPlayerGuild(player, guildId)
        val guild = services.guildService.getPlayerGuild(player)

        if (success) {
            sender.sendMessage(MessageManager.format(
                "assignment.sender-set-message",
                mapOf("player" to player.name, "guild" to guild.displayName)
            ))
            player.sendMessage(MessageManager.format(
                "assignment.target-set-message",
                mapOf("player" to player.name, "guild" to guild.displayName)
            ))
        } else {
            sender.sendMessage(MessageManager.format(
                "plugin.guild-not-exist",
                mapOf("guild" to guildId)
            ))
        }
    }
}

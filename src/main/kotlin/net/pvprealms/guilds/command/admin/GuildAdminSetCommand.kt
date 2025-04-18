package net.pvprealms.guilds.command.admin

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.core.GuildLogger
import net.pvprealms.guilds.service.GuildServices
import net.pvprealms.guilds.core.LogType
import org.bukkit.entity.Player

@Subcommand("admin set")
class GuildAdminSetCommand(
    private val services: GuildServices
): BaseCommand() {
    @CommandCompletion("@players @guilds")
    @Syntax("<player> <guild>")
    @Description("Assign a player to a guild")
    fun onSet(sender: Player, @Name("target") target: Player, @Name("guild") guildId: String) {
        val success = services.guildService.setPlayerGuild(target, guildId)
        val guild = services.guildService.getPlayerGuild(target)

        if (success) {
            sender.sendMessage(MessageManager.format("assignment.sender-set-message", mapOf("player" to target.name, "guild" to guild.displayName)))
            target.sendMessage(MessageManager.format("assignment.target-set-message", mapOf("player" to target.name, "guild" to guild.displayName)))

            GuildLogger.log(
                type = LogType.ADMIN_COMMAND,
                actor = sender.name,
                target = target.name,
                message = "Assigned to Guild ${guild.displayName}"
            )
        } else {
            sender.sendMessage(MessageManager.format("plugin.guild-not-exist", mapOf("guild" to guildId)))
        }
    }
}
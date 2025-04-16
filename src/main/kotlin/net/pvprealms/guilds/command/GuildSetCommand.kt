package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.service.GuildService
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@Subcommand("set")
@CommandPermission("guilds.admin")
class GuildSetCommand(
    private val service: GuildService
): BaseCommand() {
    @Description("Sets a player's Guild")
    @CommandCompletion("@players @guilds")
    @Syntax("<target> <guild>")
    @Default
    fun onSet(sender: Player, @Name("target") target: Player, @Name("guild") guildId: String) {
        val success = service.setGuild(target, guildId)
        val guild = service.getPlayerGuild(target)

        if (success) {
            sender.sendMessage(MessageManager.format(
                "assignment.sender-set-message",
                mapOf("player" to target.name, "guild" to guild.displayName)
            ))
            target.sendMessage(MessageManager.format(
                "assignment.target-set-message",
                mapOf("player" to target.name, "guild" to guild.displayName)
            ))
        } else {
            sender.sendMessage(MessageManager.format(
                "plugin.guild-not-exist",
                mapOf("guild" to guildId)
            ))
        }
    }
}
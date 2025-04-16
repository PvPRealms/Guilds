package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.service.GuildService
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@CommandPermission("guilds.admin")
class GuildSetCommand(
    private val service: GuildService
): BaseCommand() {
    @Subcommand("set")
    @Description("Sets a player's Guild")
    @CommandCompletion("@players @guilds")
    @Syntax("<target> <guild>")
    fun onSet(sender: Player, @Name("target") target: Player, @Name("guild") guildId: String) {
        val success = service.setPlayerGuild(target, guildId)
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
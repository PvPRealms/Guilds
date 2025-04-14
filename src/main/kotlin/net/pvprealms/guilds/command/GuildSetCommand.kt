package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import net.pvprealms.guilds.config.Messages
import net.pvprealms.guilds.core.GuildManager
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@CommandAlias("g|guild")
class GuildSetCommand(
    private val guildManager: GuildManager,
    private val plugin: JavaPlugin
): BaseCommand() {

    @Subcommand("set")
    @Description("Sets a player's Guild")
    @CommandPermission("guilds.admin")
    @CommandCompletion("@players @guilds")
    @Syntax("<player> <guild>")
    fun onGuildSet(
        sender: Player,
        @Name("target") target: Player,
        @Name("guild") guildId: String,
    ) {
        val success = guildManager.setGuild(target, guildId)
        if (success) {
            target.sendMessage(Messages.format("set", mapOf(
                "player" to target.name,
                "guild" to guildId
            )))
        } else {
            sender.sendMessage(Messages.format("guild-not-exist", mapOf("guild" to guildId)))
        }
    }
}
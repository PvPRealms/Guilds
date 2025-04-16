package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import net.pvprealms.guilds.config.MessageManager
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@Subcommand("value")
@CommandPermission("guilds.player")
class GuildValuationCommand(
    private val registry: GuildRegistry,
    private val economyService: GuildEconomyService
): BaseCommand() {
    @Description("Displays valuation of a player's Guild")
    @CommandCompletion("@guilds")
    @Syntax("<guild>")
    @Default
    fun onValuation(sender: Player, guildId: String) {
        val guild = registry.getGuildById(guildId)
        if (guild == null) {
            sender.sendMessage(MessageManager.format(
                "plugin.guild-not-exist",
                mapOf("guild" to guildId)
            ))
            return
        }

        val value = economyService.getValuation(guild).toString()
        sender.sendMessage(MessageManager.format(
            "economy.guild-valuation",
            mapOf("value" to value)
        ))
    }
}
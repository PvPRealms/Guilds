package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@CommandPermission("guilds.player")
class GuildValuationCommand(
    private val guildService: GuildService,
    private val economyService: GuildEconomyService
): BaseCommand() {
    @Subcommand("value")
    @Description("Displays valuation of a player's Guild")
    fun onValuation(sender: Player) {
        val guild = guildService.getPlayerGuild(sender)
        val value = economyService.getValuation(guild.id)

        sender.sendMessage(MessageManager.format(
            "economy.guild-valuation",
            mapOf("value" to "%.2f".format(value))
        ))
    }

    @Subcommand("value")
    @Description("Displays valuation of a player's Guild")
    @CommandCompletion("@guilds")
    @Syntax("<guild>")
    fun onValuation(sender: Player, guildId: String) {
        val guild = guildService.getGuild(guildId)
        if (guild == null) {
            sender.sendMessage(MessageManager.format(
                "plugin.guild-not-exist",
                mapOf("guild" to guildId)
            ))
            return
        }

        val value = economyService.getValuation(guild.id)
        sender.sendMessage(MessageManager.format(
            "economy.guild-valuation",
            mapOf("value" to "%.2f".format(value))
        ))
    }
}
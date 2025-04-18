package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.service.GuildServices
import net.pvprealms.guilds.gui.GuildDepositGui
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@CommandPermission("guilds.player")
class GuildDepositCommand(private val services: GuildServices): BaseCommand() {
    @Description("Deposit balance into Guild valuation")
    @Subcommand("deposit")
    @Syntax("<amount>")
    fun onDeposit(player: Player, @Name("amount") amountString: String) {
        val amount = amountString.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            player.sendMessage(MessageManager.format("economy.greater-than-zero"))
            return
        }

        val economy = services.vaultEconomyService
        if (!economy.has(player, amount)) {
            player.sendMessage(MessageManager.format("economy.insufficient-funds"))
            return
        }

        val guild = services.guildService.getPlayerGuild(player)
        economy.withdrawPlayer(player, amount)
        services.guildEconomyService.addToGuildValue(guild, amount)

        player.sendMessage(MessageManager.format(
            "economy.deposit",
            mapOf("amount" to "%.2f".format(amount))
        ))
    }

    @Description("Deposit balance into Guild valuation")
    @Subcommand("deposit")
    fun onDepositGui(player: Player) {
        GuildDepositGui.open(player, services)
    }
}
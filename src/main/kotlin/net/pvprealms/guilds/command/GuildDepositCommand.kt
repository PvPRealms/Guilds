package net.pvprealms.guilds.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import net.pvprealms.guilds.Guilds
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.service.GuildService
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@Subcommand("deposit")
@CommandPermission("guilds.player")
class GuildDepositCommand(
    private val service: GuildService,
    private val economyService: GuildEconomyService
): BaseCommand() {
    @Description("Deposit balance into Guild valuation")
    @Syntax("<amount>")
    @Default
    fun onDeposit(player: Player, @Name("amount") amountString: String) {
        val amount = amountString.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            player.sendMessage(MessageManager.format("economy.greater-than-zero"))
            return
        }

        val economy = Guilds.services.economy
        if (!economy.has(player, amount)) {
            player.sendMessage(MessageManager.format("economy.insufficient-funds"))
            return
        }

        val guild = service.getPlayerGuild(player)
        economy.withdrawPlayer(player, amount)
        economyService.deposit(guild, amount)

        player.sendMessage(MessageManager.format(
            "economy.deposit",
            mapOf("amount" to "%.2f".format(amount))
        ))
    }
}
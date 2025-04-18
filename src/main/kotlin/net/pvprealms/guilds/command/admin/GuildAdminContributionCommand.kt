package net.pvprealms.guilds.command.admin

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.service.GuildServices
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@Subcommand("admin contribution")
@CommandPermission("guilds.admin")
class GuildAdminContributionCommand(
    private val services: GuildServices
): BaseCommand() {
    @Subcommand("add")
    @Description("Add contribution value to a Guild's valuation")
    @Syntax("<amount> <target>")
    fun onAddContribution(
        sender: Player,
        @Name("guild") guildId: String,
        @Name("amount") amountStr: String
    ) {
        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            sender.sendMessage(MessageManager.format("economy.greater-than-zero"))
            return
        }

        val guild = services.guildService.getGuild(guildId)
        if (guild == null) {
            sender.sendMessage(MessageManager.format("plugin.guild-not-exist", mapOf("guild" to guildId)))
            return
        }

        services.guildEconomyService.addToGuildValue(guild, amount)

        sender.sendMessage(
            MessageManager.format(
                "admin.contribution-added",
                mapOf("guild" to guild.displayName, "amount" to "%.2f".format(amount))
            )
        )
    }

    @Subcommand("remove")
    @Description("Remove contribution value from a Guild's valuation")
    @Syntax("<guild> <amount>")
    fun onRemoveContribution(
        sender: Player,
        @Name("guild") guildId: String,
        @Name("amount") amountStr: String
    ) {
        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            sender.sendMessage(MessageManager.format("economy.greater-than-zero"))
            return
        }

        val guild = services.guildService.getGuild(guildId)
        if (guild == null) {
            sender.sendMessage(MessageManager.format("plugin.guild-not-exist", mapOf("guild" to guildId)))
            return
        }

        val current = services.guildEconomyService.getValuation(guild.id)
        val newValuation = (current - amount).coerceAtLeast(0.0)

        services.guildEconomyStorageService.setValuation(guild.id, newValuation)

        sender.sendMessage(
            MessageManager.format(
                "admin.contribution-removed",
                mapOf("guild" to guild.displayName, "amount" to "%.2f".format(amount))
            )
        )
    }
}
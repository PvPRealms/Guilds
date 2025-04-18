package net.pvprealms.guilds.service.contribution

import net.pvprealms.guilds.service.GuildServices

class GuildContributionService(private val services: GuildServices) {
    fun contribute(adapter: GuildContributionAdapter) {
        val source = services.guildContributionRegistry.getValue(adapter.getCategory(), adapter.getKey()) ?: return
        val player = adapter.getPlayer()
        val guild = services.guildService.getPlayerGuild(player)

        val total = source.baseValue
        val guildCut = total * source.contributionRate
        val playerCut = total - guildCut

        services.vaultEconomyService.depositPlayer(player, playerCut)
        services.guildEconomyService.addToGuildValue(guild, guildCut)
    }
}
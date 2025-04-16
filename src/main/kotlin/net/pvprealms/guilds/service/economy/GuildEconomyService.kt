package net.pvprealms.guilds.service.economy

import net.pvprealms.guilds.model.Guild

class GuildEconomyService(
    private val economyStorageService: GuildEconomyStorageService
) {

    fun deposit(guild: Guild, amount: Double) {
        require(amount > 0) { "Must be positive" }
        economyStorageService.increaseGuildValue(guild.id, amount)
    }

    fun getValuation(guildId: String): Double {
        return economyStorageService.getValuation(guildId)
    }

    fun getValuations(): Map<String, Double> {
        return economyStorageService.getValuations()
    }

}
package net.pvprealms.guilds.service.economy

import net.pvprealms.guilds.service.GuildServices
import net.pvprealms.guilds.model.Guild

class GuildEconomyService(private val services: GuildServices) {

    fun addToGuildValue(guild: Guild, amount: Double) {
        require(amount > 0) { "Must be positive" }
        services.guildEconomyStorageService.increaseGuildValue(guild.id, amount)
    }

    // TODO: Add decreaseValue function.

    fun getValuation(guildId: String): Double {
        return services.guildEconomyStorageService.getValuation(guildId)
    }

    fun getValuations(): Map<String, Double> {
        return services.guildEconomyStorageService.getValuations()
    }

}
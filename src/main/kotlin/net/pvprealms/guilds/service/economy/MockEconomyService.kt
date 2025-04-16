package net.pvprealms.guilds.service.economy

import net.pvprealms.guilds.model.Guild
import net.pvprealms.guilds.service.MockGuildService

class MockEconomyService(
    private val guildService: MockGuildService,
    private val economyStorageService: MockEconomyStorageService
) {

    fun deposit(guild: Guild, amount: Double) {
        require(amount > 0) { "Must be positive" }
        increaseGuildValue(guild.id, amount)
    }

    @Synchronized
    fun increaseGuildValue(guildId: String, amount: Double) {
        val current = economyStorageService.getValuation(guildId)
        economyStorageService.setValuation(guildId, current + amount)
    }

    @Synchronized
    fun decreaseGuildValue(guildId: String, amount: Double) {
        val current = economyStorageService.getValuation(guildId)
        economyStorageService.setValuation(guildId, current - amount)
    }

    fun getValuation(guildId: String): Double {
        return economyStorageService.getValuation(guildId)
    }

    fun getValuations(): Map<String, Double> {
        return economyStorageService.getValuations()
    }

}
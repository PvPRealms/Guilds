package net.pvprealms.guilds.service.economy

import net.pvprealms.guilds.model.Guild

class GuildEconomyService(
    private val storage: GuildEconomyStorageService
) {

    fun getValuation(guild: Guild): Double {
        return storage.getValuation(guild.id)
    }

    fun deposit(guild: Guild, amount: Double) {
        require(amount > 0) { "Amount must be positive" }

        synchronized(storage) {
            val current = storage.getValuation(guild.id)
            storage.setValuation(guild.id, current + amount)
        }
    }

    fun getValuations(): Map<String, Double> = storage.getValuations()
}

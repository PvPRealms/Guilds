package net.pvprealms.guilds.core

import net.pvprealms.guilds.Guilds
import net.pvprealms.guilds.service.GuildAssignmentService
import net.pvprealms.guilds.service.GuildRegistry
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.GuildStorageService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import net.pvprealms.guilds.service.economy.GuildEconomyStorageService
import net.pvprealms.guilds.service.economy.PlayerValuationTracker

object ServiceRegistrar {
    fun register(plugin: Guilds, container: GuildsServices): Boolean {
        val registry = GuildRegistry()
        val storage = GuildStorageService(plugin, registry)
        val assignment = GuildAssignmentService(storage, registry)
        val economyStorage = GuildEconomyStorageService(plugin)
        val economyService = GuildEconomyService(economyStorage)
        val tracker = PlayerValuationTracker()
        val guildService = GuildService(registry, storage, assignment)

        guildService.load()
        economyStorage.load()

        container.guildRegistry = registry
        container.guildStorage = storage
        container.guildAssignment = assignment
        container.guildService = guildService
        container.guildEconomyStorage = economyStorage
        container.guildEconomyService = economyService
        container.valuationTracker = tracker

        return true
    }
}
package net.pvprealms.guilds.core

import net.pvprealms.guilds.Guilds
import net.pvprealms.guilds.service.assignment.GuildAssignmentService
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.GuildStorageService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import net.pvprealms.guilds.service.economy.GuildEconomyStorageService
import net.pvprealms.guilds.service.economy.PlayerValuationTracker

object ServiceRegistrar {
    fun register(plugin: Guilds, container: GuildsServices): Boolean {
        val storageService = GuildStorageService(plugin, container.guildService)
        val assignmentService = GuildAssignmentService(container.guildService, storageService)
        val economyStorageService = GuildEconomyStorageService(plugin)
        val economyService = GuildEconomyService(economyStorageService)
        val tracker = PlayerValuationTracker()
        val guildService = GuildService(storageService, assignmentService, economyStorageService)

        guildService.loadServices()

        container.guildStorage = storageService
        container.guildAssignment = assignmentService
        container.guildService = guildService
        container.guildEconomyStorage = economyStorageService
        container.guildEconomyService = economyService
        container.valuationTracker = tracker

        return true
    }
}
package net.pvprealms.guilds.core

import net.pvprealms.guilds.Guilds
import net.pvprealms.guilds.service.assignment.GuildAssignmentService
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.GuildStorageService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import net.pvprealms.guilds.service.economy.GuildEconomyStorageService
import net.pvprealms.guilds.service.economy.PlayerValuationTracker

object ServiceRegistrar {
    fun register(plugin: Guilds, container: GuildServices): Boolean {
        try {
            val guildService = GuildService()

            val storageService = GuildStorageService(plugin, guildService)
            val assignmentService = GuildAssignmentService(guildService, storageService)

            guildService.init(storageService, assignmentService)
            guildService.loadServices()

            val economyStorage = GuildEconomyStorageService(plugin)
            economyStorage.loadGuildValuation()

            val economyService = GuildEconomyService(economyStorage)
            val tracker = PlayerValuationTracker()

            container.guildService = guildService
            container.guildStorage = storageService
            container.guildAssignment = assignmentService
            container.guildEconomyStorage = economyStorage
            container.guildEconomyService = economyService
            container.valuationTracker = tracker

            return true

        } catch (ex: Exception) {
            plugin.logger.severe("[Guilds] Failed to register services: ${ex.message}")
            ex.printStackTrace()
            return false
        }
    }
}
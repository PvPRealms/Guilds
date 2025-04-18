package net.pvprealms.guilds.core

import net.pvprealms.guilds.Guilds
import net.pvprealms.guilds.service.*
import net.pvprealms.guilds.service.assignment.GuildAssignmentService
import net.pvprealms.guilds.service.contribution.GuildContributionRegistry
import net.pvprealms.guilds.service.contribution.GuildContributionService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import net.pvprealms.guilds.service.economy.GuildEconomyStorageService
import net.pvprealms.guilds.service.economy.PlayerValuationTracker

object ServiceRegistrar {

    fun register(plugin: Guilds, services: GuildServices): Boolean {
        return try {
            // Core Services
            val guildService = createGuildService(services, plugin)
            services.guildService = guildService

            // Economy Services
            val economyStorage = createEconomyStorage(plugin)
            val economyService = GuildEconomyService(services)
            services.guildEconomyStorageService = economyStorage
            services.guildEconomyService = economyService
            services.guildValuationTracker = PlayerValuationTracker()

            // Contribution System
            val contributionRegistry = createContributionRegistry(plugin)
            services.guildContributionRegistry = contributionRegistry

            val contributionService = GuildContributionService(services)
            services.guildContributionService = contributionService

            true
        } catch (ex: Exception) {
            plugin.logger.severe("[Guilds] Failed to register services: ${ex.message}")
            ex.printStackTrace()
            false
        }
    }

    private fun createGuildService(services: GuildServices, plugin: Guilds): GuildService {
        val guildService = GuildService()
        val storage = GuildStorageService(plugin, guildService)
        val assignment = GuildAssignmentService(services)
        guildService.init(storage, assignment)
        guildService.loadServices()

        Guilds.services.guildStorageService = storage
        Guilds.services.guildAssignmentService = assignment

        return guildService
    }

    private fun createEconomyStorage(plugin: Guilds): GuildEconomyStorageService {
        val economyStorage = GuildEconomyStorageService(plugin)
        economyStorage.loadGuildValuation()
        return economyStorage
    }

    private fun createContributionRegistry(plugin: Guilds): GuildContributionRegistry {
        val registry = GuildContributionRegistry(plugin)
        registry.loadConfig()
        return registry
    }
}

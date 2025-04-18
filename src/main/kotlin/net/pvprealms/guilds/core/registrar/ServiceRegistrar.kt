package net.pvprealms.guilds.core.registrar

import net.pvprealms.guilds.Guilds
import net.pvprealms.guilds.service.GuildServices
import net.pvprealms.guilds.service.core.GuildAssignmentService
import net.pvprealms.guilds.service.contribution.GuildContributionRegistry
import net.pvprealms.guilds.service.contribution.GuildContributionService
import net.pvprealms.guilds.service.core.GuildRegistry
import net.pvprealms.guilds.service.core.GuildService
import net.pvprealms.guilds.service.core.GuildStorageService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import net.pvprealms.guilds.service.economy.GuildEconomyStorageService
import net.pvprealms.guilds.service.economy.PlayerValuationTracker
import net.pvprealms.guilds.service.core.GuildBanService

object ServiceRegistrar {

    fun register(plugin: Guilds, services: GuildServices): Boolean {
        return try {
            // === GUILD CORE SERVICES ===
            val guildRegistry = GuildRegistry()
            val storageService = GuildStorageService(plugin, services.guildRegistry)
            val banService = GuildBanService()
            val assignmentService = GuildAssignmentService(services)
            val guildService = GuildService(services)

            guildService.load()

            services.guildRegistry = guildRegistry
            services.guildStorageService = storageService
            services.guildAssignmentService = assignmentService
            services.guildBanService = banService
            services.guildService = guildService

            // === ECONOMY SERVICES ===
            val economyStorage = GuildEconomyStorageService(plugin)
            economyStorage.loadGuildValuation()

            val economyService = GuildEconomyService(services)
            val valuationTracker = PlayerValuationTracker()

            services.guildEconomyStorageService = economyStorage
            services.guildEconomyService = economyService
            services.guildValuationTracker = valuationTracker

            // === CONTRIBUTION SERVICES ===
            val contributionRegistry = GuildContributionRegistry(plugin).apply { loadConfig() }
            val contributionService = GuildContributionService(services)

            services.guildContributionRegistry = contributionRegistry
            services.guildContributionService = contributionService

            true

        } catch (ex: Exception) {
            plugin.logger.severe("[Guilds] Failed to register services: ${ex.message}")
            ex.printStackTrace()
            false
        }
    }
}
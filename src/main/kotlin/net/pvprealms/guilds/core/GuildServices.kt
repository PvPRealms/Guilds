package net.pvprealms.guilds.core

import net.milkbowl.vault.economy.Economy
import net.pvprealms.guilds.service.assignment.GuildAssignmentService
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.GuildStorageService
import net.pvprealms.guilds.service.contribution.GuildContributionRegistry
import net.pvprealms.guilds.service.contribution.GuildContributionService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import net.pvprealms.guilds.service.economy.GuildEconomyStorageService
import net.pvprealms.guilds.service.economy.PlayerValuationTracker

class GuildServices {

    lateinit var vaultEconomyService: Economy
    lateinit var guildService: GuildService
    lateinit var guildStorageService: GuildStorageService
    lateinit var guildAssignmentService: GuildAssignmentService
    lateinit var guildEconomyService: GuildEconomyService
    lateinit var guildEconomyStorageService: GuildEconomyStorageService
    lateinit var guildValuationTracker: PlayerValuationTracker
    lateinit var guildContributionRegistry: GuildContributionRegistry
    lateinit var guildContributionService: GuildContributionService
}
package net.pvprealms.guilds.service

import net.milkbowl.vault.economy.Economy
import net.pvprealms.guilds.service.core.GuildAssignmentService
import net.pvprealms.guilds.service.core.GuildStorageService
import net.pvprealms.guilds.service.contribution.GuildContributionRegistry
import net.pvprealms.guilds.service.contribution.GuildContributionService
import net.pvprealms.guilds.service.core.GuildRegistry
import net.pvprealms.guilds.service.core.GuildService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import net.pvprealms.guilds.service.economy.GuildEconomyStorageService
import net.pvprealms.guilds.service.economy.PlayerValuationTracker
import net.pvprealms.guilds.service.core.GuildBanService

class GuildServices {

    lateinit var guildService: GuildService
    lateinit var guildRegistry: GuildRegistry
    lateinit var guildStorageService: GuildStorageService
    lateinit var vaultEconomyService: Economy
    lateinit var guildAssignmentService: GuildAssignmentService
    lateinit var guildBanService: GuildBanService
    lateinit var guildEconomyService: GuildEconomyService
    lateinit var guildEconomyStorageService: GuildEconomyStorageService
    lateinit var guildValuationTracker: PlayerValuationTracker
    lateinit var guildContributionRegistry: GuildContributionRegistry
    lateinit var guildContributionService: GuildContributionService

}
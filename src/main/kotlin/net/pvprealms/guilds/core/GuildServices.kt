package net.pvprealms.guilds.core

import net.milkbowl.vault.economy.Economy
import net.pvprealms.guilds.service.GuildAssignmentService
import net.pvprealms.guilds.service.GuildRegistry
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.GuildStorageService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import net.pvprealms.guilds.service.economy.GuildEconomyStorageService
import net.pvprealms.guilds.service.economy.PlayerValuationTracker

class GuildsServices {

    lateinit var economy: Economy

    lateinit var guildService: GuildService
    lateinit var guildRegistry: GuildRegistry
    lateinit var guildStorage: GuildStorageService
    lateinit var guildAssignment: GuildAssignmentService

    lateinit var guildEconomyService: GuildEconomyService
    lateinit var guildEconomyStorage: GuildEconomyStorageService
    lateinit var valuationTracker: PlayerValuationTracker
}
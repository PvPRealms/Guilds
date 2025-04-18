package net.pvprealms.guilds.service.contribution

import org.bukkit.entity.Player

interface GuildContributionAdapter {
    fun getCategory(): String
    fun getKey(): String
    fun getPlayer(): Player
}
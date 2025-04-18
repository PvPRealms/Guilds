package net.pvprealms.guilds.service.contribution.adapter

import net.pvprealms.guilds.service.contribution.GuildContributionAdapter
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerKillAdapter(private val event: PlayerDeathEvent): GuildContributionAdapter {

    override fun getCategory(): String = "players"

    override fun getKey(): String = event.entity.name

    override fun getPlayer(): Player = event.entity.killer ?: throw IllegalStateException("No killer for player death")

}
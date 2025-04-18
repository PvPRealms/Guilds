package net.pvprealms.guilds.listener

import net.pvprealms.guilds.core.GuildServices
import net.pvprealms.guilds.service.contribution.adapter.PlayerKillAdapter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeathListener(private val services: GuildServices): Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        if (event.player.killer == null) return
        services.guildContributionService.contribute(PlayerKillAdapter(event))
    }
}
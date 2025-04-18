package net.pvprealms.guilds.listener

import net.pvprealms.guilds.service.GuildServices
import net.pvprealms.guilds.service.contribution.adapter.EntityKillAdapter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class EntityKillListener(private val services: GuildServices): Listener {

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entity.killer == null) return
        services.guildContributionService.contribute(EntityKillAdapter(event))
    }
}
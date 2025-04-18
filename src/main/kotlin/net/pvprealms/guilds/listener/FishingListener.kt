package net.pvprealms.guilds.listener

import net.pvprealms.guilds.core.GuildServices
import net.pvprealms.guilds.service.contribution.adapter.FishingAdapter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent

class FishingListener(private val services: GuildServices): Listener {
    @EventHandler
    fun onFish(event: PlayerFishEvent) {
         services.guildContributionService.contribute(FishingAdapter(event))
    }
}
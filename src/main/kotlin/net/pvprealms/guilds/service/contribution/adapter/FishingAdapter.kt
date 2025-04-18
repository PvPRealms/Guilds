package net.pvprealms.guilds.service.contribution.adapter

import net.pvprealms.guilds.service.contribution.GuildContributionAdapter
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerFishEvent

class FishingAdapter(private val event: PlayerFishEvent): GuildContributionAdapter {

    override fun getCategory(): String = "fishing"

    override fun getKey(): String = event.caught?.type?.name ?: "UNKNOWN"

    override fun getPlayer(): Player = event.player

}
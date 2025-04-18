package net.pvprealms.guilds.listener

import net.pvprealms.guilds.service.GuildServices
import net.pvprealms.guilds.service.contribution.adapter.BlockBreakAdapter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener(private val services: GuildServices): Listener {
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        services.guildContributionService.contribute(BlockBreakAdapter(event))
    }
}
package net.pvprealms.guilds.listener

import net.pvprealms.guilds.service.GuildServices
import net.pvprealms.guilds.service.contribution.adapter.CropHarvestAdapter
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class CropHarvestListener(private val services: GuildServices): Listener {

    @EventHandler
    fun onCropHarvest(event: BlockBreakEvent) {
        val cropTypes = setOf(
            Material.WHEAT, Material.CARROTS, Material.POTATOES,
            Material.BEETROOTS, Material.NETHER_WART
        )

        val block = event.block
        if (block.type in cropTypes) {
            services.guildContributionService.contribute(CropHarvestAdapter(event.player, block))
        }
    }
}
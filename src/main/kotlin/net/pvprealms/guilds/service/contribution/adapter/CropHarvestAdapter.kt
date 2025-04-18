package net.pvprealms.guilds.service.contribution.adapter

import net.pvprealms.guilds.service.contribution.GuildContributionAdapter
import org.bukkit.block.Block
import org.bukkit.entity.Player

class CropHarvestAdapter(
    private val player: Player,
    private val block: Block
): GuildContributionAdapter {

    override fun getCategory(): String = "crops"

    override fun getKey(): String = block.type.name

    override fun getPlayer(): Player = player

}
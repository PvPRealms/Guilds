package net.pvprealms.guilds.service.contribution.adapter

import net.pvprealms.guilds.service.contribution.GuildContributionAdapter
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakAdapter(private val event: BlockBreakEvent): GuildContributionAdapter {

    override fun getCategory(): String = "blocks"

    override fun getKey(): String = event.block.type.name

    override fun getPlayer(): Player = event.player

}
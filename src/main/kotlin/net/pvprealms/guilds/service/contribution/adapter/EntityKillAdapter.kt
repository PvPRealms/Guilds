package net.pvprealms.guilds.service.contribution.adapter

import net.pvprealms.guilds.service.contribution.GuildContributionAdapter
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDeathEvent

class EntityKillAdapter(private val event: EntityDeathEvent): GuildContributionAdapter {

    override fun getCategory(): String = "mobs"

    override fun getKey(): String = event.entity.type.name

    override fun getPlayer(): Player = event.entity.killer ?: throw IllegalStateException("Entity has no killer")

}
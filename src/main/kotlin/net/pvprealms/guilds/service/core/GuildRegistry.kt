package net.pvprealms.guilds.service.core

import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.model.Guild

class GuildRegistry {

    private val guilds: Map<String, Guild> =
        ConfigManager.getGuildMap().mapValues {
            Guild(it.key.lowercase(), it.value)
        }

    fun getGuild(id: String): Guild? = guilds[id.lowercase()]

    fun getGuilds(): Collection<Guild> = guilds.values

}
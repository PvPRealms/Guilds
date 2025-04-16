package net.pvprealms.guilds.service

import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.model.Guild

class GuildRegistry {

    private var guilds: Map<String, Guild> = emptyMap()

    fun load() {
        guilds = ConfigManager.getGuildMap().mapValues {
            Guild(it.key.lowercase(), it.value)
        }
    }

    fun getGuilds(): Collection<Guild> = guilds.values

    fun getGuildById(id: String): Guild? = guilds[id.lowercase()]

}
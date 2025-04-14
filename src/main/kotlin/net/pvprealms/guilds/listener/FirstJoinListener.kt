package net.pvprealms.guilds.listener

import net.pvprealms.guilds.config.Messages
import net.pvprealms.guilds.core.GuildManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class FirstJoinListener(
    private val guildManager: GuildManager
): Listener {

    @EventHandler
    fun onFirstJoin(event: PlayerJoinEvent) {
        val player = event.player

        guildManager.assignIfMissing(player)

        if (!player.hasPlayedBefore()) {
            player.sendMessage(Messages.format("first-join"))
        }
    }
}
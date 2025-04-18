package net.pvprealms.guilds.listener

import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.service.GuildServices
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class FirstJoinListener(private val services: GuildServices): Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val guild = services.guildService.getPlayerGuild(player)

        if (!player.hasPlayedBefore()) {
            services.guildService.assignPlayerGuild(player)
            player.sendMessage(MessageManager.format(
                "assignment.assigned-first-join",
                mapOf("guild" to guild.displayName)
            ))
        }

        val currentVal = services.guildEconomyService.getValuation(guild.id)
        val difference = services.guildValuationTracker.getIncrease(player.uniqueId, currentVal)

        if (difference != null && difference > 0) {
            player.sendMessage(MessageManager.format(
                "economy.login-valuation-change",
                mapOf("increase" to "%.2f".format(difference)))
            )
        }

        services.guildValuationTracker.update(player.uniqueId, currentVal)
    }
}
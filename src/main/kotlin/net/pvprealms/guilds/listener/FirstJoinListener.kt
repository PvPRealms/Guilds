package net.pvprealms.guilds.listener

import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import net.pvprealms.guilds.service.economy.PlayerValuationTracker
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class FirstJoinListener(
    private val service: GuildService,
    private val economyService: GuildEconomyService,
    private val valuationTracker: PlayerValuationTracker
): Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val guild = service.getPlayerGuild(player)

        if (!player.hasPlayedBefore()) {
            service.assignPlayerGuild(player)
            player.sendMessage(MessageManager.format(
                "assignment.assigned-first-join",
                mapOf("guild" to guild.displayName)
            ))
        }

        val currentVal = economyService.getValuation(guild.id)
        val difference = valuationTracker.getIncrease(player.uniqueId, currentVal)

        if (difference != null && difference > 0) {
            player.sendMessage(MessageManager.format(
                "economy.login-valuation-change",
                mapOf("increase" to "%.2f".format(difference)))
            )
        }

        valuationTracker.update(player.uniqueId, currentVal)
    }
}
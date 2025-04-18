package net.pvprealms.guilds.gui

import dev.triumphteam.gui.guis.Gui
import net.pvprealms.guilds.service.GuildServices
import net.pvprealms.guilds.helper.formatDuration
import net.pvprealms.guilds.helper.guiPlayerHead
import net.pvprealms.guilds.helper.mm
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

object GuildInfoGui {
    fun open(player: Player, services: GuildServices) {
        val guild = services.guildService.getPlayerGuild(player)
        val members = services.guildService.getPlayersInGuild(guild)

        val gui = buildGui(members)
        gui.open(player)
    }

    private fun buildGui(memberUUIDs: List<UUID>): Gui {
        val gui = Gui.gui()
            .title(mm("Showing ${memberUUIDs.size} Member(s)"))
            .rows(6)
            .disableAllInteractions()
            .create()

        val sorted = memberUUIDs.sortedByDescending { uuid ->
            Bukkit.getOfflinePlayer(uuid).isOnline
        }

        for ((index, uuid) in sorted.withIndex()) {
            val offlinePlayer = Bukkit.getOfflinePlayer(uuid)
            val name = offlinePlayer.name ?: continue
            val isOnline = offlinePlayer.isOnline

            val displayName = if (isOnline) "<green>[• Online] $name</green>" else "<red>[• Offline] $name</red>"
            val hoverText = if (isOnline) {
                val session = System.currentTimeMillis() - Bukkit.getPlayer(uuid)!!.lastLogin
                "Online for <white>${formatDuration(session)}</white>"
            } else {
                val lastSeen = System.currentTimeMillis() - offlinePlayer.lastPlayed
                "Offline for <white>${formatDuration(lastSeen)}</white>"
            }

            val item = guiPlayerHead(name)
                .name(mm(displayName))
                .lore(listOf(mm("<gray>$hoverText</gray>")))
                .asGuiItem()

            gui.setItem(index, item)
        }

        return gui
    }
}
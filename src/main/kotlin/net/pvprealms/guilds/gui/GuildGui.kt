package net.pvprealms.guilds.gui

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import net.pvprealms.guilds.helper.mm
import net.pvprealms.guilds.model.Guild
import net.pvprealms.guilds.service.GuildService
import org.bukkit.Material
import org.bukkit.entity.Player

object GuildGui {
    fun open(player: Player, service: GuildService) {
        val guild = service.getPlayerGuild(player)

        val gui = buildGui(guild, player, service)
        gui.open(player)
    }

    private fun buildGui(guild: Guild, player: Player, service: GuildService): Gui {
        val gui = Gui.gui()
            .title(mm(guild.displayName))
            .rows(3)
            .disableAllInteractions()
            .create()

        val guildInformationItem = ItemBuilder.from(Material.EMERALD)
            .name(mm("<green>${guild.displayName}</green>"))
            .lore(listOf(
                mm("<gray>Members: <white>${service.getPlayersInGuild(guild).size}")
            ))
            .asGuiItem()

        gui.setItem(2, 5, guildInformationItem)

        return gui
    }
}
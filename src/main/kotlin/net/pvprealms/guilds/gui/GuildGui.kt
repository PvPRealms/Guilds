package net.pvprealms.guilds.gui

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import net.pvprealms.guilds.core.GuildServices
import net.pvprealms.guilds.helper.mm
import net.pvprealms.guilds.model.Guild
import net.pvprealms.guilds.service.GuildService
import net.pvprealms.guilds.service.economy.GuildEconomyService
import org.bukkit.Material
import org.bukkit.entity.Player

object GuildGui {
    fun open(player: Player, services: GuildServices) {
        val guild = services.guildService.getPlayerGuild(player)

        val gui = buildGui(guild, services)
        gui.open(player)
    }

    private fun buildGui(guild: Guild, services: GuildServices): Gui {
        val gui = Gui.gui()
            .title(mm(guild.displayName))
            .rows(6)
            .disableAllInteractions()
            .create()

        val guildInformationItem = ItemBuilder.from(Material.EMERALD)
            .name(mm("<green>${guild.displayName}</green>"))
            .lore(listOf(
                mm("<gray>Members: <white>${services.guildService.getPlayersInGuild(guild).size}"),
                mm("<gray>Valuation: <white>$${services.guildEconomyService.getValuation(guild.id)}")
            ))
            .asGuiItem()

        val guildDepositItem = ItemBuilder.from(Material.GOLD_INGOT)
            .name(mm("<green>Deposit Funds</green>"))
            .lore(mm("<gray>Click to deposit into your Guild valuation</gray>"))
            .asGuiItem { event ->
                val player = event.whoClicked as Player
                GuildDepositGui.open(player, services)
            }

        val guildInfoItem = ItemBuilder.from(Material.BOOK)
            .name(mm("<green>View Players</green>"))
            .lore(mm("<gray>Click to view players in your Guild</gray>"))
            .asGuiItem { event ->
                val player = event.whoClicked as Player
                GuildInfoGui.open(player, services)
            }

        gui.setItem(2, 5, guildInformationItem)
        gui.setItem(3, 2, guildDepositItem)
        gui.setItem(3, 3, guildInfoItem)

        return gui
    }
}
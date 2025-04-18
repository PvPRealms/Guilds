package net.pvprealms.guilds.gui

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import net.pvprealms.guilds.Guilds
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.core.GuildServices
import net.pvprealms.guilds.helper.mm
import net.pvprealms.guilds.model.Guild
import org.bukkit.Material
import org.bukkit.entity.Player

object GuildDepositGui {

    private val depositAmounts = listOf(100.0, 1_000.0, 10_000.0, 100_000.0, 1_000_000.0)

    fun open(player: Player, services: GuildServices) {
        val guild = services.guildService.getPlayerGuild(player)

        val gui = buildGui(player, guild, services)
        gui.open(player)
    }

    private fun buildGui(player: Player, guild: Guild, services: GuildServices): Gui {
        val gui = Gui.gui()
            .title(mm(guild.displayName))
            .rows(3)
            .disableAllInteractions()
            .create()

        depositAmounts.forEachIndexed { index, amount ->
            val item = ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE)
                .name(mm("<gold>Deposit $${"%,.0f".format(amount)}</gold>"))
                .lore(mm("<gray>Click to deposit this amount to your guild.</gray>"))
                .asGuiItem {
                    val eco = Guilds.services.vaultEconomyService
                    if (!eco.has(player,amount)) {
                        player.sendMessage(MessageManager.format("economy.insufficient-funds"))
                        return@asGuiItem
                    }

                    eco.withdrawPlayer(player, amount)
                    services.guildEconomyService.deposit(guild, amount)

                    player.sendMessage(MessageManager.format(
                        "economy.deposit",
                        mapOf("amount" to "%.2f".format(amount))
                    ))
                    player.closeInventory()
                }

            gui.setItem(1, 1 + index, item)
        }

        return gui
    }
}
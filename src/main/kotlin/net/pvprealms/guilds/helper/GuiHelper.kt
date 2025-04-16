package net.pvprealms.guilds.helper

import dev.triumphteam.gui.builder.item.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

fun guiPlayerHead(name: String): ItemBuilder {
    val player: OfflinePlayer = Bukkit.getOfflinePlayer(name)
    val skull = ItemStack(Material.PLAYER_HEAD)

    val meta = skull.itemMeta
    if (meta is SkullMeta) {
        meta.owningPlayer = player
        skull.itemMeta = meta
    }

    return ItemBuilder.from(skull)
}
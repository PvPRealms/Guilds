package net.pvprealms.guilds.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

val mm = MiniMessage.miniMessage()

fun mm(text: String): Component = mm.deserialize(text)
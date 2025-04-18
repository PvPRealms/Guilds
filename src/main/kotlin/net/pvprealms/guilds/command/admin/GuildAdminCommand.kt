package net.pvprealms.guilds.command.admin

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import net.pvprealms.guilds.helper.mm
import org.bukkit.entity.Player

@CommandAlias("g|guild")
@Subcommand("admin")
@CommandPermission("guilds.admin")
@Description("Guild administration commands")
class GuildAdminCommand: BaseCommand() {
    @Default
    fun onAdmin(sender: Player) {
        sender.sendMessage(mm("<purple>Guild Admin Commands:</purple><gray>"))
        sender.sendMessage(mm("<white>/g admin set <player> <guild></white>"))
        sender.sendMessage(mm("<white>/g admin contribution add <guild> <amount></white>"))
        sender.sendMessage(mm("<white>/g admin contribution remove <guild> <amount></white>"))
        sender.sendMessage(mm("<white>/g admin ban <player> <guild></white>"))
        sender.sendMessage(mm("<white>/g admin unban <player> <guild></white>"))
    }
}
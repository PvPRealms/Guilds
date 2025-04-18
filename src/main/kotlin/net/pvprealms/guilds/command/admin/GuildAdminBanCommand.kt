package net.pvprealms.guilds.command.admin

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.core.GuildLogger
import net.pvprealms.guilds.core.LogType
import net.pvprealms.guilds.service.GuildServices
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@Subcommand("admin ban")
@CommandPermission("guilds.admin")
class GuildAdminBanCommand(private val services: GuildServices): BaseCommand() {
    @CommandCompletion("@players @guilds")
    @Syntax("<player> <guild>")
    @Description("Bans a player from a Guild")
    fun onBan(sender: Player, @Name("player") target: String, @Name("guild") guildId: String) {
        val uuid = Bukkit.getOfflinePlayer(target).uniqueId
        val guild = services.guildService.getGuild(guildId)

        if (guild == null) {
            sender.sendMessage(MessageManager.format("plugin.guild-not-exist", mapOf("guild" to guildId)))
            return
        }

        services.guildBanService.ban(uuid, guild.id)
        sender.sendMessage(MessageManager.format("admin.banned-player-admin", mapOf("player" to sender.name, "guild" to guild.displayName)))
        GuildLogger.log(LogType.ADMIN_COMMAND, sender.name, target, "Banned from ${guild.displayName}")
    }
}
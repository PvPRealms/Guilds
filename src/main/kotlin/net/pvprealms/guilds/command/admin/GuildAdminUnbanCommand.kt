package net.pvprealms.guilds.command.admin

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.core.GuildLogger
import net.pvprealms.guilds.core.LogType
import net.pvprealms.guilds.service.GuildServices
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@Subcommand("admin unban")
@CommandPermission("guilds.admin")
class GuildAdminUnbanCommand(private val services: GuildServices) : BaseCommand() {
    @CommandCompletion("@players @guilds")
    @Syntax("<player> <guild>")
    @Description("Unbans a player from a Guild")
    fun onUnban(sender: Player, @Name("player") target: String, @Name("guild") guildId: String) {
        val uuid = Bukkit.getOfflinePlayer(target).uniqueId
        val guild = services.guildService.getGuild(guildId)

        if (guild == null) {
            sender.sendMessage(MessageManager.format("plugin.guild-not-exist", mapOf("guild" to guildId)))
            return
        }

        services.guildBanService.unban(uuid, guild.id)
        sender.sendMessage(MessageManager.format("admin.unbanned-player-admin", mapOf("player" to sender.name, "guild" to guild.displayName)))
        GuildLogger.log(LogType.ADMIN_COMMAND, sender.name, target, "Unbanned from ${guild.displayName}")
    }
}
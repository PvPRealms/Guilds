package net.pvprealms.guilds

import net.milkbowl.vault.economy.Economy
import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.core.registrar.CommandRegistrar
import net.pvprealms.guilds.core.registrar.ListenerRegistrar
import net.pvprealms.guilds.core.registrar.ServiceRegistrar
import net.pvprealms.guilds.service.GuildServices
import org.bukkit.plugin.java.JavaPlugin

class Guilds: JavaPlugin() {

    companion object {
        lateinit var instance: Guilds
        lateinit var services: GuildServices
    }

    override fun onEnable() {
        instance = this
        services = GuildServices()

        ConfigManager.load(this)
        MessageManager.load(this)

        server.scheduler.runTaskLater(this, Runnable {
            val vault = server.servicesManager.getRegistration(Economy::class.java)
            if (vault == null) {
                logger.severe("[Guilds] Vault not found. Economy features will be unavailable.")
                server.pluginManager.disablePlugin(this)
                return@Runnable
            }

            services.vaultEconomyService = vault.provider
            logger.info("[Guilds] Hooked into economy: ${vault.provider.name}")

            val success = ServiceRegistrar.register(this, services)
            if (!success) {
                logger.severe("[Guilds] Failed to register internal services. Disabling plugin.")
                server.pluginManager.disablePlugin(this)
                return@Runnable
            }

            CommandRegistrar.register(this, services)
            ListenerRegistrar.register(this, services)

            services.guildEconomyStorageService.startAutoSaveTask()

            logger.info("[Guilds] Plugin is enabled.")
        }, 1L)
    }

    override fun onDisable() {
        services.guildEconomyStorageService.saveAllGuildValuation()

        logger.info("[Guilds] Plugin is disabled.")
    }
}

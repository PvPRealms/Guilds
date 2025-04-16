package net.pvprealms.guilds

import net.milkbowl.vault.economy.Economy
import net.pvprealms.guilds.config.ConfigManager
import net.pvprealms.guilds.config.MessageManager
import net.pvprealms.guilds.core.CommandRegistrar
import net.pvprealms.guilds.core.GuildsServices
import net.pvprealms.guilds.core.ListenerRegistrar
import net.pvprealms.guilds.core.ServiceRegistrar
import org.bukkit.plugin.java.JavaPlugin

class Guilds: JavaPlugin() {

    companion object {
        lateinit var instance: Guilds
        lateinit var services: GuildsServices
    }

    override fun onEnable() {
        instance = this
        services = GuildsServices()

        ConfigManager.load(this)
        MessageManager.load(this)

        server.scheduler.runTaskLater(this, Runnable {
            val vault = server.servicesManager.getRegistration(Economy::class.java)
            if (vault == null) {
                logger.severe("[Guilds] Vault not found. Economy features will be unavailable.")
                server.pluginManager.disablePlugin(this)
                return@Runnable
            }

            services.economy = vault.provider
            logger.info("[Guilds] Hooked into economy: ${vault.provider.name}")

            val success = ServiceRegistrar.register(this, services)
            if (!success) {
                logger.severe("[Guilds] Failed to register internal services. Disabling plugin.")
                server.pluginManager.disablePlugin(this)
                return@Runnable
            }

            CommandRegistrar.register(this, services.guildService, services.guildEconomyService, services.guildRegistry)
            ListenerRegistrar.register(this, services.guildService, services.guildEconomyService, services.valuationTracker)

            logger.info("[Guilds] Plugin is enabled.")
        }, 1L)
    }

    override fun onDisable() {
        logger.info("[Guilds] Plugin is disabled.")
    }

    private fun setupEconomy(): Boolean {
        val rsp = server.servicesManager.getRegistration(Economy::class.java) ?: return false
        services.economy = rsp.provider
        return true
    }
}

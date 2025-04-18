package net.pvprealms.guilds.core

import com.google.gson.GsonBuilder
import java.io.File

object GuildLogger {

    private val file = File("plugins/Guilds/logs.json")
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val entries = mutableListOf<GuildLogEntry>()

    fun log(type: LogType, actor: String, target: String, message: String) {
        entries.add(GuildLogEntry(type, actor, target, message))
        saveLog()
    }

    private fun saveLog() {
        file.parentFile.mkdirs()
        file.writeText(gson.toJson(entries))
    }

}

data class GuildLogEntry(
    val type: LogType,
    val actor: String,
    val target: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class LogType {
    DEPOSIT, ADMIN_COMMAND, CONTRIBUTION
}
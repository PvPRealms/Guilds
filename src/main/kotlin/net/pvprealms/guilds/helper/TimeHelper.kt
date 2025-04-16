package net.pvprealms.guilds.helper

import java.time.Duration

fun formatDuration(millis: Long): String {
    val duration = Duration.ofMillis(millis)
    val days = duration.toDays()
    val hours = duration.toHours() % 24
    val minutes = duration.toMinutes() % 60
    val seconds = duration.toSeconds() % 60

    return buildString {
        if (days > 0) append("${days}d ")
        if (hours > 0) append("${hours}h ")
        append("${minutes}m ")
        append("${seconds}s")
    }.trim()
}
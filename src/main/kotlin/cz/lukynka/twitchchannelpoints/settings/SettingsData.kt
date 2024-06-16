package cz.lukynka.twitchchannelpoints.settings

import com.intellij.notification.NotificationType
import cz.lukynka.twitchchannelpoints.Main
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.concurrency.runAsync

object Settings {

    lateinit var current: SettingsData

    fun parse(input: String) {

        val new = Json.decodeFromString<SettingsData>(input)
        current = new

        val list = mutableListOf<String>()
        new.rewards.forEach { list.add("${it.name};${it.type};${it.value}") }

        SettingsState.instance.rewardList = list
        SettingsState.instance.twitchAPIToken = new.twitchAPIToken
        SettingsState.instance.twitchClientId = new.twitchClientId
        SettingsState.instance.twitchChannelId = new.twitchChannelId

        Main.projects.forEach {
            Main.showNotification(it, "Configuration Updated!", "Channel Point Rewards configuration updated!", NotificationType.INFORMATION)
        }

        runAsync(Main::load)
    }
}

@Serializable
data class SettingsData(
    val twitchClientId: String,
    val twitchAPIToken: String,
    val twitchChannelId: String,
    val rewards: MutableList<ChannelPointReward>
)

@Serializable
data class ChannelPointReward(
    val name: String,
    val type: String,
    val value: String,
)
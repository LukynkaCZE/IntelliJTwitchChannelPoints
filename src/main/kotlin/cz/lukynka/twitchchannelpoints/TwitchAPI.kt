package cz.lukynka.twitchchannelpoints

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.pubsub.events.RewardRedeemedEvent
import com.intellij.notification.NotificationType
import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import cz.lukynka.twitchchannelpoints.Main.project
import cz.lukynka.twitchchannelpoints.Main.showNotification
import cz.lukynka.twitchchannelpoints.settings.Settings
import cz.lukynka.twitchchannelpoints.settings.events.FontEvent
import cz.lukynka.twitchchannelpoints.settings.events.ThemeEvent

class TwitchAPI(clientId: String, token: String) {

    var oAuth2: OAuth2Credential
    var twitchClient: TwitchClient

    init {
        showNotification(
            project,
            "Twitch Channel Points Integration",
            "Connecting to twitch api..",
            NotificationType.INFORMATION
        )

        oAuth2 = OAuth2Credential(
            "twitch",
            token
        )

        twitchClient = TwitchClientBuilder.builder()
            .withEnableHelix(true)
            .withEnablePubSub(true)
            .withClientId(clientId)
            .withEnableChat(true)
            .withChatAccount(oAuth2)
            .build()

        twitchClient.pubSub.listenForChannelPointsRedemptionEvents(oAuth2, Settings.current.twitchChannelId)
        twitchClient.eventManager.onEvent(RewardRedeemedEvent::class.java) {
            onRewardRedeemedEvent(it)
        }

        showNotification(
            project,
            "Twitch Channel Points Integration",
            "Successfully Connected!",
            NotificationType.INFORMATION
        )
        log("Successfully connected to twitch!", LogType.SUCCESS)
    }

    private fun onRewardRedeemedEvent(event: RewardRedeemedEvent) {
        log("Redeemed ${event.redemption.reward.title}")
        val redeemEvent = Settings.current.rewards.firstOrNull { it.name == event.redemption.reward.title } ?: return

        val value = if(redeemEvent.value == "_prompt") event.redemption.userInput else redeemEvent.value

        when (redeemEvent.type) {
            "SET_THEME" -> {
                if (redeemEvent.value == "_random") {
                    ThemeEvent.setRandom(event.redemption.user.displayName)
                } else {
                    ThemeEvent.setTheme(value, event.redemption.user.displayName)
                }
            }

            "SET_FONT" -> {
                if (redeemEvent.value == "_random") {
                    FontEvent.setFontRandom(event.redemption.user.displayName)
                } else {
                    FontEvent.setFont(value, event.redemption.user.displayName)
                }
            }

            "SHOW_EXCEPTION" -> {
                showNotification(project, "io.kotlin.${event.redemption.user.displayName}SubmittedException:", value, NotificationType.ERROR)
            }

            else -> return
        }
    }
}
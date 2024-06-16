package cz.lukynka.twitchchannelpoints

import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.notification.NotificationGroupManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import cz.lukynka.twitchchannelpoints.settings.Settings

object Main {

    var projects = ProjectManager.getInstance().openProjects
    lateinit var project: Project
    lateinit var twitchAPI: TwitchAPI

    fun showNotification(project: Project?, title: String, content: String, type: NotificationType, persistent: Boolean = false) {
        val notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("Custom Notifications")

        val notification = notificationGroup.createNotification(title, content, type)
        if(persistent) {
            Notifications.Bus.notify(notification, project)
        } else {
            Notifications.Bus.notifyAndHide(notification, project)
        }
    }

    fun load(reload: Boolean = false) {

        log("Project (Re)Loaded", LogType.SUCCESS)
        val settings = Settings.current

        if(settings.twitchAPIToken.isEmpty() || settings.twitchClientId.isEmpty()) {
            showNotification(project, "Twitch Channel Points Integration", "You have not setup your integration yet! Set your twitch client id and twitch api token in settings of the plugin!", NotificationType.WARNING)
            return
        }

        if(this::twitchAPI.isInitialized && !reload) return


        twitchAPI = TwitchAPI(Settings.current.twitchClientId, Settings.current.twitchAPIToken)
    }
}
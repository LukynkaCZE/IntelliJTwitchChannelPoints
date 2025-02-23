package cz.lukynka.twitchchannelpoints.settings.events

import com.intellij.ide.actions.QuickChangeLookAndFeel
import com.intellij.ide.ui.LafManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.colors.EditorColorsManager
import cz.lukynka.twitchchannelpoints.Main
import javax.swing.UIManager.LookAndFeelInfo

object ThemeEvent {

    fun setTheme(theme: String, user: String) {
        ApplicationManager.getApplication().invokeLater {
            val list = LafManager.getInstance().installedLookAndFeels
            val newTheme = list.firstOrNull { it.name == theme }
            if(newTheme == null) {
                Main.showNotification(Main.project, "Theme changed by $user!", "Theme was changed by $user but theme with name $theme was not found!", NotificationType.ERROR)
                setTheme("Dark", "System")
                return@invokeLater
            }
            setTheme(newTheme, user)
        }
    }

    fun setTheme(theme: LookAndFeelInfo, user: String, show: Boolean = true) {
        FontEvent.setFont("Jetbrains Mono", "_", false)
        ApplicationManager.getApplication().invokeLater {
            QuickChangeLookAndFeel.switchLafAndUpdateUI(
                LafManager.getInstance(),
                theme,
                true
            )
            EditorColorsManager.getInstance().setGlobalScheme(EditorColorsManager.getInstance().schemeForCurrentUITheme)
            if(show) Main.showNotification(Main.project, "Theme changed by $user!", theme.name, NotificationType.INFORMATION)
            FontEvent.setFont("Jetbrains Mono", "System", false)
        }
    }

    fun setRandom(user: String) {
        ApplicationManager.getApplication().invokeLater {
            val list = LafManager.getInstance().installedLookAndFeels.filter { !it.name.lowercase().contains("light") && LafManager.getInstance().currentLookAndFeel != it }
            val random = list.random()
            setTheme(random, user)
        }
    }
}

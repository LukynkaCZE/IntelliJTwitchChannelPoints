package cz.lukynka.twitchchannelpoints.settings

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import cz.lukynka.twitchchannelpoints.settings.events.FontEvent
import cz.lukynka.twitchchannelpoints.settings.events.ThemeEvent

class ResetEditorStuffAction: AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        FontEvent.setFont("Jetbrains Mono", "", false)
        ThemeEvent.setTheme("ppy dark", "System")
        FontEvent.setFont("Jetbrains Mono", "", false)
    }
}
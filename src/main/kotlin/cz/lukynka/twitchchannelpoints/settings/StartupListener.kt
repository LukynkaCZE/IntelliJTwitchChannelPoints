package cz.lukynka.twitchchannelpoints.settings

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import cz.lukynka.twitchchannelpoints.Main
import org.jetbrains.concurrency.runAsync

class StartupListener: ProjectActivity {
    override suspend fun execute(project: Project) {
        Main.project = project
        SettingsState.instance

        runAsync {
            Main.load()
        }
    }
}
package cz.lukynka.twitchchannelpoints.settings.events

import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import cz.lukynka.twitchchannelpoints.Main

object FontEvent {

    private val fonts = mutableListOf<String>()

    init {
        fonts.add("ChevyRay - Pinch")
        fonts.add("Comic Sans MS")
        fonts.add("Impact")
        fonts.add("Times New Roman")
        fonts.add("MingLiU-ExtB")
        fonts.add("Fira Code")
        fonts.add("Segoe Script")
        fonts.add("Monocraft")
        fonts.add("Arial")
    }

    fun setFont(font: String, user: String, show: Boolean = true) {
        val editorColorsManager = EditorColorsManager.getInstance()

        editorColorsManager.globalScheme.editorFontName = font
        refreshOpenEditors()
        if(show) Main.showNotification(Main.project, "Font changed by $user!", font, NotificationType.INFORMATION)
    }

    fun setFontRandom(user: String) {
        setFont(fonts.random(), user)
    }

    private fun refreshOpenEditors() {
        ApplicationManager.getApplication().invokeLater {
            val projects: Array<Project> = ProjectManager.getInstance().openProjects
            for (project in projects) {
                val fileEditorManager = FileEditorManager.getInstance(project)
                if (fileEditorManager != null) {
                    val editorFactory: EditorFactory = EditorFactory.getInstance()
                    editorFactory.refreshAllEditors()
                }
            }
        }
    }
}
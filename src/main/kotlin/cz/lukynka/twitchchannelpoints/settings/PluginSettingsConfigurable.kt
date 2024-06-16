package cz.lukynka.twitchchannelpoints.settings
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.Messages
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JFileChooser
import javax.swing.JPanel

class PluginSettingsConfigurable : Configurable {
    private val panel = JPanel(BorderLayout())
    private val uploadButton = JButton("Upload JSON Configuration file")

    init {
        uploadButton.addActionListener(UploadButtonListener())
        panel.add(uploadButton, BorderLayout.NORTH)
    }

    override fun getDisplayName(): String = "Twitch Settings"

    override fun createComponent(): JComponent = panel

    override fun isModified(): Boolean = true

    override fun apply() {
        val settings = SettingsState.instance
    }

    override fun reset() {
        val settings = SettingsState.instance
    }

    override fun disposeUIResources() {}
}

class UploadButtonListener : ActionListener {

    override fun actionPerformed(e: ActionEvent?) {
        val fileChooser = JFileChooser()
        val result = fileChooser.showOpenDialog(null)

        if (result == JFileChooser.APPROVE_OPTION) {
            val file = fileChooser.selectedFile
            parseJsonFile(file)
        }
    }

    private fun parseJsonFile(file: File) {
        try {
            Settings.parse(file.readText())
        } catch (ex: Exception) {
            Messages.showMessageDialog("Failed to load configuration: ${ex.message}", "Error", Messages.getErrorIcon())
        }
    }
}
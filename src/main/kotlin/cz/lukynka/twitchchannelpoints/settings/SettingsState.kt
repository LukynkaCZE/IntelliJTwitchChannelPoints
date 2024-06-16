package cz.lukynka.twitchchannelpoints.settings

import com.intellij.openapi.components.*
import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log

@Service
@State(
    name = "cz.lukynka.twitchchannelpoints.SettingsState",
    storages = [Storage("PluginSettings.xml")])
class SettingsState : PersistentStateComponent<SettingsState> {

    var twitchClientId: String = ""
    var twitchAPIToken: String = ""
    var rewardList: MutableList<String> = mutableListOf()
    var twitchChannelId: String = ""

    override fun getState(): SettingsState {
        return this
    }

    override fun loadState(state: SettingsState) {
        log("Loaded settings state: ${state.twitchAPIToken} | ${state.twitchClientId}", LogType.SUCCESS)

        twitchClientId = state.twitchAPIToken
        twitchAPIToken = state.twitchClientId
        twitchChannelId = state.twitchChannelId
        rewardList = state.rewardList
        val list = mutableListOf<ChannelPointReward>()
        rewardList.forEach {
            val split = it.split(";")
            val name = split[0]
            val type = split[1]
            val value = split[2]
            list.add(ChannelPointReward(name, type, value))
        }
        log(list.toString(), LogType.DEBUG)

        Settings.current = SettingsData(twitchClientId, twitchAPIToken, twitchChannelId, list)
    }

    companion object {
        val instance: SettingsState
            get() = service()
    }
}
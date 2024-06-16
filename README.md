# IntelliJ Twitch Channel Points Integration

IntelliJ plugin that allows you to link your twitch channel point rewards to actions like changing theme, changing font, sending fake errors and etc 

**Example:**

![example_gif](https://github.com/LukynkaCZE/IntelliJTwitchChannelPoints/assets/48604271/4e70f680-1dc4-4766-9cb3-883a86efd780)

---

## Installation

You can download the plugin from the JetBrains Marketplace

<iframe width="245px" height="48px" src="https://plugins.jetbrains.com/embeddable/install/24641"></iframe>

---

## Usage

Make a JSON config in the following style:

```json
{
    "twitchClientId": "<your twitch client id>",
    "twitchAPIToken": "<your twitch api token>",
    "twitchChannelId": "<your twitch channel id>",
    "rewards": [
        {
            "name": "Randomize IDE theme",
            "type": "SET_THEME",
            "value": "_random"
        },
        {
            "name": "Switch IDE theme to Light Theme",
            "type": "SET_THEME",
            "value": "IntelliJ Light"
        },
        {
            "name": "Randomize IDE font",
            "type": "SET_FONT",
            "value": "_random"
        },
        {
            "name": "Send Exception",
            "type": "SHOW_EXCEPTION",
            "value": "_prompt"
        }
    ]
}
```

The field `name` in reward object _**__needs__**_ to be the same as name of the reward on twitch

For the type, you can choose from the following:
- `SET_THEME`
- `SET_FONT`
- `SHOW_EXCEPTION`

When using `SET_THEME` you need to pass the name of the theme in the `value` field, otherwise it will default to `IntelliJ Dark`

When using `SET_FONT` you need to pass the name of the font in the `value` field, otherwise it will default to `Jetbrains Mono`

When using `SHOW_EXCEPTION` you need to pass the text of the "exception" in the `value` field, otherwise it will default to an empty string

You can also use following variables prefixed with "_" in the value field:
- `_random` will pick random theme or font. Doesn't work on `SHOW_EXCEPTION`
- `_prompt` will use the viewer submitted text. Requires "Require Viewer to Enter Text" to be enabled in the reward setting on twitch

Then you can go to the settings of the plugin and click "Upload JSON Configuration file". Select the file you made and everything should automatically reload and work!

---

If you have more ideas to add, feel free to either reach out via [Twitter](https://twitter.com/LukynkaCze), [Discord](https://discord.gg/ptM8JSQZ) or open an issue on this repo

---

If you want to contribute with either new reward ideas or bug fixes, feel free to open a pull request

---

If you like this project, consider supporting me by [buying me a coffee](https://ko-fi.com/lukynkacze) <3
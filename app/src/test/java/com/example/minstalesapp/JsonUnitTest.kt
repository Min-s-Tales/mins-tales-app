package com.example.minstalesapp

import org.json.JSONObject
import org.junit.jupiter.api.Test

class JsonUnitTest {

    private val TAG = "JsonUnitTest"
    private val jsonString = "{\n" +
        "\t\"start\": {\n" +
        "\t\t\"start-sound\": {\n" +
        "\t\t\t\"0\": {\n" +
        "\t\t\t\t\"src\": \"velia.mp3\",\n" +
        "\t\t\t\t\"out\": \"music\",\n" +
        "\t\t\t\t\"loop\": true\n" +
        "\t\t\t},\n" +
        "\t\t\t\"1\": {\n" +
        "\t\t\t\t\"src\": \"context.wav\",\n" +
        "\t\t\t\t\"out\": \"narrator\"\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\n" +
        "\t\t\"actions\": {\n" +
        "\t\t\t\"paths\": {\n" +
        "\t\t\t\t\"left\": \"gauche\",\n" +
        "\t\t\t\t\"right\": \"droite\",\n" +
        "\t\t\t\t\"help\": \"aide\"\n" +
        "\t\t\t}\n" +
        "\t\t}\n" +
        "\t},\n" +
        "\n" +
        "\t\"left\": {\n" +
        "\t\t\"stop-sound\": {\n" +
        "\t\t\t\"out\": {\n" +
        "\t\t\t\t\"0\": \"music\",\n" +
        "\t\t\t\t\"1\": \"narrator\"\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\n" +
        "\t\t\"start-sound\": {\n" +
        "\t\t\t\"0\": {\n" +
        "\t\t\t\t\"src\": \"cron_castle.mp3\",\n" +
        "\t\t\t\t\"out\": \"music\",\n" +
        "\t\t\t\t\"loop\": true\n" +
        "\t\t\t},\n" +
        "\t\t\t\"1\": {\n" +
        "\t\t\t\t\"src\": \"narrator_left.mp3\",\n" +
        "\t\t\t\t\"out\": \"narrator\"\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\n" +
        "\t\t\"actions\": {\n" +
        "\t\t\t\"paths\": {\n" +
        "\t\t\t\t\"fight\": \"combattre\",\n" +
        "\t\t\t\t\"flee\": \"fuir\",\n" +
        "\t\t\t\t\"help\": \"aide\"\n" +
        "\t\t\t}\n" +
        "\t\t}\n" +
        "\t},\n" +
        "\n" +
        "\t\"fight\": {\n" +
        "\t\t\"stop-sound\": {\n" +
        "\t\t\t\"out\": {\n" +
        "\t\t\t\t\"0\": \"music\",\n" +
        "\t\t\t\t\"1\": \"narrator\"\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\n" +
        "\t\t\"start-sound\": {\n" +
        "\t\t\t\"0\": {\n" +
        "\t\t\t\t\"src\": \"calpheon.mp3\",\n" +
        "\t\t\t\t\"out\": \"music\",\n" +
        "\t\t\t\t\"loop\": true\n" +
        "\t\t\t},\n" +
        "\t\t\t\"1\": {\n" +
        "\t\t\t\t\"src\": \"narrator_fight.mp3\",\n" +
        "\t\t\t\t\"out\": \"narrator\"\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\n" +
        "\t\t\"actions\": {\n" +
        "\t\t\t\"paths\": {\n" +
        "\t\t\t\t\"help\": \"aide\"\n" +
        "\t\t\t}\n" +
        "\t\t}\n" +
        "\t},\n" +
        "\n" +
        "\t\"flee\": {\n" +
        "\t\t\"stop-sound\": {\n" +
        "\t\t\t\"out\": {\n" +
        "\t\t\t\t\"0\": \"music\",\n" +
        "\t\t\t\t\"1\": \"narrator\"\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\n" +
        "\t\t\"start-sound\": {\n" +
        "\t\t\t\"0\": {\n" +
        "\t\t\t\t\"src\": \"escape.mp3\",\n" +
        "\t\t\t\t\"out\": \"music\",\n" +
        "\t\t\t\t\"loop\": true\n" +
        "\t\t\t},\n" +
        "\t\t\t\"1\": {\n" +
        "\t\t\t\t\"src\": \"narrator_escape.mp3\",\n" +
        "\t\t\t\t\"out\": \"narrator\"\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\n" +
        "\t\t\"actions\": {\n" +
        "\t\t\t\"paths\": {\n" +
        "\t\t\t\t\"help\": \"aide\"\n" +
        "\t\t\t}\n" +
        "\t\t}\n" +
        "\t},\n" +
        "\n" +
        "\t\"right\": {\n" +
        "\t\t\"stop-sound\": {\n" +
        "\t\t\t\"out\": {\n" +
        "\t\t\t\t\"0\": \"music\",\n" +
        "\t\t\t\t\"1\": \"narrator\"\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\n" +
        "\t\t\"start-sound\": {\n" +
        "\t\t\t\"0\": {\n" +
        "\t\t\t\t\"src\": \"sea.mp3\",\n" +
        "\t\t\t\t\"out\": \"music\",\n" +
        "\t\t\t\t\"loop\": true\n" +
        "\t\t\t},\n" +
        "\t\t\t\"1\": {\n" +
        "\t\t\t\t\"src\": \"narrator_right.mp3\",\n" +
        "\t\t\t\t\"out\": \"narrator\"\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\n" +
        "\t\t\"actions\": {\n" +
        "\t\t\t\"paths\": {\n" +
        "\t\t\t\t\"help\": \"aide\"\n" +
        "\t\t\t}\n" +
        "\t\t}\n" +
        "\t},\n" +
        "\n" +
        "\t\"player-actions-global\": {\n" +
        "\t\t\"paths\": {\n" +
        "\t\t\t\"exit\": \"quitter la partie\"\n" +
        "\t\t},\n" +
        "\t\t\"exit\": {\n" +
        "\t\t\t\"stop-sound\": {\n" +
        "\t\t\t\t\"out\": {\n" +
        "\t\t\t\t\t\"0\": \"music\",\n" +
        "\t\t\t\t\t\"1\": \"narrator\"\n" +
        "\t\t\t\t}\n" +
        "\t\t\t},\n" +
        "\t\t\t\"actions\": {\n" +
        "\t\t\t\t\"end-game\": \"type\"\n" +
        "\t\t\t}\n" +
        "\t\t}\n" +
        "\t}\n" +
        "}"

    @Test
    @Throws(Exception::class)
    fun isJsonReadable() {
        val jsonObject = JSONObject(jsonString)
        print(jsonObject)
    }
}

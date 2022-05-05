package com.example.minstalesapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JsonInstrumentedTest {

    private val jsonString = "{\n" +
            "    \"start\": {\n" +
            "        \"start-sound\": {\n" +
            "            \"0\": {\n" +
            "                \"src\": \"velia.mp3\",\n" +
            "                \"out\": \"music\",\n" +
            "                \"loop\": true\n" +
            "            },\n" +
            "            \"1\": {\n" +
            "                \"src\": \"context.wav\",\n" +
            "                \"out\": \"narrator\"\n" +
            "            }\n" +
            "        },\n" +
            "\n" +
            "        \"actions\": {\n" +
            "            \"paths\": {\n" +
            "                \"left\": \"gauche\",\n" +
            "                \"right\": \"droite\",\n" +
            "                \"help\": \"aide\"\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "\n" +
            "    \"left\": {\n" +
            "        \"stop-sound\": {\n" +
            "            \"out\": {\n" +
            "                \"0\": \"music\",\n" +
            "                \"1\": \"narrator\"\n" +
            "            }\n" +
            "        },\n" +
            "\n" +
            "        \"start-sound\": {\n" +
            "            \"0\": {\n" +
            "                \"src\": \"cron_castle.mp3\",\n" +
            "                \"out\": \"music\",\n" +
            "                \"loop\": true\n" +
            "            },\n" +
            "            \"1\": {\n" +
            "                \"src\": \"narrator_left.mp3\",\n" +
            "                \"out\": \"narrator\"\n" +
            "            }\n" +
            "        },\n" +
            "\n" +
            "        \"actions\": {\n" +
            "            \"paths\": {\n" +
            "                \"fight\": \"combattre\",\n" +
            "                \"flee\": \"fuir\",\n" +
            "                \"help\": \"aide\"\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "\n" +
            "    \"fight\": {\n" +
            "        \"stop-sound\": {\n" +
            "            \"out\": {\n" +
            "                \"0\": \"music\",\n" +
            "                \"1\": \"narrator\"\n" +
            "            }\n" +
            "        },\n" +
            "\n" +
            "        \"start-sound\": {\n" +
            "            \"0\": {\n" +
            "                \"src\": \"calpheon.mp3\",\n" +
            "                \"out\": \"music\",\n" +
            "                \"loop\": true\n" +
            "            },\n" +
            "            \"1\": {\n" +
            "                \"src\": \"narrator_fight.mp3\",\n" +
            "                \"out\": \"narrator\"\n" +
            "            }\n" +
            "        },\n" +
            "\n" +
            "        \"actions\": {\n" +
            "            \"paths\": {\n" +
            "                \"help\": \"aide\"\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "\n" +
            "    \"flee\": {\n" +
            "        \"stop-sound\": {\n" +
            "            \"out\": {\n" +
            "                \"0\": \"music\",\n" +
            "                \"1\": \"narrator\"\n" +
            "            }\n" +
            "        },\n" +
            "\n" +
            "        \"start-sound\": {\n" +
            "            \"0\": {\n" +
            "                \"src\": \"escape.mp3\",\n" +
            "                \"out\": \"music\",\n" +
            "                \"loop\": true\n" +
            "            },\n" +
            "            \"1\": {\n" +
            "                \"src\": \"narrator_escape.mp3\",\n" +
            "                \"out\": \"narrator\"\n" +
            "            }\n" +
            "        },\n" +
            "\n" +
            "        \"actions\": {\n" +
            "            \"paths\": {\n" +
            "                \"help\": \"aide\"\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "\n" +
            "    \"right\": {\n" +
            "        \"stop-sound\": {\n" +
            "            \"out\": {\n" +
            "                \"0\": \"music\",\n" +
            "                \"1\": \"narrator\"\n" +
            "            }\n" +
            "        },\n" +
            "\n" +
            "        \"start-sound\": {\n" +
            "            \"0\": {\n" +
            "                \"src\": \"sea.mp3\",\n" +
            "                \"out\": \"music\",\n" +
            "                \"loop\": true\n" +
            "            },\n" +
            "            \"1\": {\n" +
            "                \"src\": \"narrator_right.mp3\",\n" +
            "                \"out\": \"narrator\"\n" +
            "            }\n" +
            "        },\n" +
            "\n" +
            "        \"actions\": {\n" +
            "            \"paths\": {\n" +
            "                \"help\": \"aide\"\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "\n" +
            "    \"player-actions-global\": {\n" +
            "        \"paths\": {\n" +
            "            \"exit\": \"quitter la partie\"\n" +
            "        },\n" +
            "        \"exit\": {\n" +
            "            \"stop-sound\": {\n" +
            "                \"out\": {\n" +
            "                    \"0\": \"music\",\n" +
            "                    \"1\": \"narrator\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"actions\": {\n" +
            "                \"end-game\": \"type\"\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}"

    /**
     * Checks if the jsonString is a readable json String and tests if the "start" value is readable
     * inside.
     */
    @Test
    fun isJsonReadable() {
        val jsonObject = JSONObject(jsonString)
        assert(!jsonObject.isNull("start"))
        jsonObject.getJSONObject("start")
    }
}
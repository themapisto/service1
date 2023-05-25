package com.example.service1.RestTemplateTest.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IpExtractor {

        public static JsonArray extractValues(String jsonString) {
            JsonElement element = JsonParser.parseString(jsonString);
            JsonObject jsonObject = element.getAsJsonObject();
            JsonObject arrayObject = jsonObject.get("value").getAsJsonObject().get("array").getAsJsonObject();
            JsonArray jsonArray = arrayObject.get("elements").getAsJsonArray();
            JsonArray values = new JsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject ipObject = jsonArray.get(i).getAsJsonObject().get("string").getAsJsonObject();
                JsonElement value = ipObject.get("value");
                values.add(value);
            }
            return values;
    }
}

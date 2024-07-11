package mygdx.game.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import mygdx.game.model.faction.Faction;
import mygdx.game.model.leader.Leader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class CustomGson {
    static class LeaderTypeAdapter extends TypeAdapter<Leader> {

        @Override
        public void write(JsonWriter jsonWriter, Leader leader) throws IOException {
            if (leader == null) {
                jsonWriter.beginObject();
                jsonWriter.name("className");
                jsonWriter.value("");
                jsonWriter.endObject();
                return;
            }
            jsonWriter.beginObject();
            jsonWriter.name("className");
            jsonWriter.value(leader.getClass().getCanonicalName());
            jsonWriter.endObject();
        }

        @Override
        public Leader read(JsonReader jsonReader) throws IOException {
            jsonReader.beginObject();
            jsonReader.nextName();
            String leaderClassName = jsonReader.nextString();
            if (leaderClassName.isEmpty()) return null;
            else {
                try {
                    Class<?> clazz = Class.forName(leaderClassName);
                    Object obj = clazz.getConstructor().newInstance();
                    jsonReader.endObject();
                    return (Leader) obj;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class FactionTypeAdapter extends TypeAdapter<Faction> {
        @Override
        public void write(JsonWriter writer, Faction faction) throws IOException {
            if (faction == null) return;
            writer.beginObject();
            writer.name("clasName");
            writer.value(faction.getClass().getCanonicalName());
            writer.endObject();
        }

        @Override
        public Faction read(JsonReader reader) throws IOException {
            try {
                reader.beginObject();
                reader.nextName();
                Class<?> clazz = Class.forName(reader.nextString());
                reader.endObject();
                return (Faction) clazz.getConstructor().newInstance();
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Faction.class, new FactionTypeAdapter());
        builder.registerTypeAdapter(Leader.class, new LeaderTypeAdapter());
        return builder.create();
    }
}

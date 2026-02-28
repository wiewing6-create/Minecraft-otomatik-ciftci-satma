package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FarmerSettings {
    public static String command = "/farmer";
    public static String rawConfig = "page:1, slots:23,24,25";
    public static boolean isRunning = false;
    public static int currentStep = 0;
    public static int tickDelay = 0;
    public static List<Integer> targetPages = new ArrayList<>();
    public static int currentPageIndex = 0;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "farmersell.json");

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            SaveData data = new SaveData();
            data.command = command;
            data.rawConfig = rawConfig;
            GSON.toJson(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (!CONFIG_FILE.exists()) return;
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            SaveData data = GSON.fromJson(reader, SaveData.class);
            if (data != null) {
                command = data.command;
                rawConfig = data.rawConfig;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SaveData {
        String command;
        String rawConfig;
    }

    public static List<Integer> getSlotsForPage(int targetPage) {
        List<Integer> slots = new ArrayList<>();
        try {
            String[] parts = rawConfig.split(";");
            for (String part : parts) {
                if (part.toLowerCase().contains("page:" + targetPage)) {
                    String slotPart = part.split("slots:")[1];
                    for (String s : slotPart.split(",")) {
                        slots.add(Integer.parseInt(s.trim()));
                    }
                }
            }
        } catch (Exception ignored) {}
        return slots;
    }

    public static void updatePageList() {
        targetPages.clear();
        try {
            String[] parts = rawConfig.split(";");
            for (String part : parts) {
                if (part.contains("page:")) {
                    String num = part.split("page:")[1].split(",")[0].trim();
                    targetPages.add(Integer.parseInt(num));
                }
            }
        } catch (Exception ignored) {}
    }
}
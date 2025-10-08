package org.ln.java.renamer;

import java.io.*;
import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * Gestione centralizzata delle preferenze del programma.
 * Combina:
 * - un file .properties globale (config comune)
 * - Preferences Java per utente locale
 *
 * Accesso statico ovunque tramite RenamerPreferences.get() o metodi statici di comodo.
 */
public class RenamerPreferences {

    private static final String CONFIG_FILE = "config.properties";
    private static RenamerPreferences instance;

    private final Properties globalConfig;
    private final Preferences userPrefs;

    // ---------------------------
    // SINGLETON
    // ---------------------------
    private RenamerPreferences() {
        globalConfig = new Properties();
        userPrefs = Preferences.userNodeForPackage(RenamerPreferences.class);
        loadGlobalConfig();
    }

    /** Ottieni l'istanza unica (thread-safe lazy init) */
    public static synchronized RenamerPreferences get() {
        if (instance == null) {
            instance = new RenamerPreferences();
        }
        return instance;
    }

    // ---------------------------
    // CONFIGURAZIONE GLOBALE (.properties)
    // ---------------------------
    private void loadGlobalConfig() {
        File file = new File(CONFIG_FILE);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                globalConfig.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // valori di default
            globalConfig.setProperty("language", "it");
            globalConfig.setProperty("theme", "light");
            saveGlobalConfig();
        }
    }

    public void saveGlobalConfig() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            globalConfig.store(fos, "Configurazione globale del programma");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getGlobalProperty(String key, String defaultValue) {
        return globalConfig.getProperty(key, defaultValue);
    }

    public void setGlobalProperty(String key, String value) {
        globalConfig.setProperty(key, value);
    }

    // ---------------------------
    // PREFERENZE UTENTE (Preferences API)
    // ---------------------------
    public void saveWindowSize(int width, int height) {
        userPrefs.putInt("window.width", width);
        userPrefs.putInt("window.height", height);
    }

    public int[] loadWindowSize() {
        int width = userPrefs.getInt("window.width", 800);   // default 800x600
        int height = userPrefs.getInt("window.height", 600);
        return new int[]{width, height};
    }

    public void saveLastFile(String path) {
        userPrefs.put("lastFile", path);
    }

    public String loadLastFile() {
        return userPrefs.get("lastFile", "");
    }

    // ---------------------------
    // METODI STATICI DI COMODO
    // ---------------------------
    public static String getProp(String key, String def) {
        return get().getGlobalProperty(key, def);
    }

    public static void setProp(String key, String value) {
        get().setGlobalProperty(key, value);
    }

    public static void saveAll() {
        get().saveGlobalConfig();
    }

    public static void saveWindow(int w, int h) {
        get().saveWindowSize(w, h);
    }

    public static int[] loadWindow() {
        return get().loadWindowSize();
    }

    public static void saveLast(String path) {
        get().saveLastFile(path);
    }

    public static String loadLast() {
        return get().loadLastFile();
    }
}

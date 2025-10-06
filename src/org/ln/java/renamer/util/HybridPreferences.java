package org.ln.java.renamer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class HybridPreferences {

    private static final String CONFIG_FILE = "config.properties";
    private Properties globalConfig;
    private Preferences userPrefs;

    public HybridPreferences() {
        globalConfig = new Properties();
        userPrefs = Preferences.userNodeForPackage(HybridPreferences.class);

        loadGlobalConfig();
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
    // DEMO
    // ---------------------------
    public static void main(String[] args) {
        HybridPreferences prefs = new HybridPreferences();

        // --- Parte PROPERTIES ---
        System.out.println("Lingua configurata: " + prefs.getGlobalProperty("language", "it"));
        prefs.setGlobalProperty("theme", "dark");
        prefs.saveGlobalConfig();

        // --- Parte PREFERENCES ---
        prefs.saveWindowSize(1024, 768);
        int[] size = prefs.loadWindowSize();
        System.out.println("Ultima finestra: " + size[0] + "x" + size[1]);

        prefs.saveLastFile("C:/temp/data.txt");
        System.out.println("Ultimo file aperto: " + prefs.loadLastFile());

        // Mini GUI di esempio
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hybrid Preferences Demo");
            frame.setSize(size[0], size[1]);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel("Lingua: " + prefs.getGlobalProperty("language", "it")
                    + " | Tema: " + prefs.getGlobalProperty("theme", "light"));
            frame.add(label);

            frame.setVisible(true);

            // quando chiudo la finestra → salvo dimensioni
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    prefs.saveWindowSize(frame.getWidth(), frame.getHeight());
                    prefs.saveGlobalConfig();
                }
            });
        });
    }
}

package org.ln.java.renamer.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class EsempioSalvataggioFile {

    public static void main(String[] args) {
        // Eseguiamo l'interfaccia grafica in un thread sicuro
        SwingUtilities.invokeLater(() -> creaEmostraGUI());
    }

    private static void creaEmostraGUI() {
        // Creiamo la finestra principale
        JFrame frame = new JFrame("Esempio JFileChooser Salva");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Centra la finestra

        // Creiamo un pulsante per avviare il salvataggio
        JButton salvaButton = new JButton("Salva File...");
        salvaButton.addActionListener(e -> apriDialogoSalvataggio());

        // Aggiungiamo il pulsante alla finestra
        frame.getContentPane().add(salvaButton);
        frame.setVisible(true);
    }

    private static void apriDialogoSalvataggio() {
        // 1. Creiamo un'istanza di JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        
        // Impostiamo un titolo per la finestra di dialogo
        fileChooser.setDialogTitle("Scegli dove salvare il file");
        
        // Proponiamo un nome di default per il file
        fileChooser.setSelectedFile(new File("mio_documento.txt"));

        // 2. Mostriamo la finestra di dialogo "Salva"
        // Passiamo 'null' perché non abbiamo un componente genitore specifico
        int userSelection = fileChooser.showSaveDialog(null);

        // 3. Controlliamo se l'utente ha premuto "Salva"
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // 4. Otteniamo il file selezionato
            File fileDaSalvare = fileChooser.getSelectedFile();
            
            // Assicuriamoci che il file abbia l'estensione .txt se non è stata specificata
            if (!fileDaSalvare.getAbsolutePath().endsWith(".txt")) {
                fileDaSalvare = new File(fileDaSalvare.getAbsolutePath() + ".txt");
            }

            System.out.println("Salva come file: " + fileDaSalvare.getAbsolutePath());

            // 5. Scriviamo il contenuto nel file
            try (FileWriter fileWriter = new FileWriter(fileDaSalvare)) {
                fileWriter.write("Questo è il contenuto del mio file.\n");
                fileWriter.write("Scritto tramite JFileChooser!");
                JOptionPane.showMessageDialog(null, "File salvato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante il salvataggio del file.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
package br.edu.ifsp.vtp.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.save.JRPrintSaveContributor;

public class JRCustomViewer extends JRViewer {

    public String nameReport;
    private final JasperPrint jasperPrint;

    public JRCustomViewer(JasperPrint jasperPrint) {
        super(jasperPrint);
        this.jasperPrint = jasperPrint;

        if (lastFolder == null) {
            this.lastFolder = new File(System.getProperty("user.home"));
        }

        for (ActionListener actionListener : this.btnSave.getActionListeners()) {
            this.btnSave.removeActionListener(actionListener);
        }

        this.btnSave.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSave2();
            }
        });

    }

    void btnSave2() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setLocale(getLocale());
        fileChooser.updateUI();
        File file = new File(lastFolder.getPath() + System.getProperty("file.separator") + nameReport);
        fileChooser.setSelectedFile(file);
        JRSaveContributor contributor = null;
        for (int i = 0; i < saveContributors.size(); i++) {
            fileChooser.addChoosableFileFilter((javax.swing.filechooser.FileFilter) saveContributors.get(i));
        }

        if (saveContributors.contains(lastSaveContributor)) {
            fileChooser.setFileFilter(lastSaveContributor);
        } else if (saveContributors.size() > 1) {
            fileChooser.setFileFilter((javax.swing.filechooser.FileFilter) saveContributors.get(1));
        }
        if (lastFolder != null) {
            fileChooser.setCurrentDirectory(lastFolder);
        }
        int retValue = fileChooser.showSaveDialog(this);

        if (retValue == 0) {
            javax.swing.filechooser.FileFilter fileFilter = fileChooser.getFileFilter();
            file = fileChooser.getSelectedFile();
            lastFolder = file.getParentFile();

            if (fileFilter instanceof JRSaveContributor) {
                contributor = (JRSaveContributor) fileFilter;
            } else {
                int i = 0;
                do {
                    if (contributor != null || i >= saveContributors.size()) {
                        break;
                    }
                    contributor = (JRSaveContributor) saveContributors.get(i++);
                    if (!contributor.accept(file)) {
                        contributor = null;
                    }
                } while (true);
                if (contributor == null) {
                    contributor = new JRPrintSaveContributor(jasperReportsContext, getLocale(), null);
                }
            }
            lastSaveContributor = contributor;
            try {
                contributor.save(jasperPrint, file);
            } catch (JRException ex) {
                Logger.getLogger(JRCustomViewer.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "JasperViewer.error.save");
            }
        }
    }
}

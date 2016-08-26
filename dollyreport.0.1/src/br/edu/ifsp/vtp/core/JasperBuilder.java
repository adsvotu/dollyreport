package br.edu.ifsp.vtp.core;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class JasperBuilder {

    private JDialog viewer;
    private InputStream sourceFileName;
    private HashMap params;
    private Connection jdbcConnection;

    public JasperBuilder createViewer(String title) {
        viewer = new JDialog(new javax.swing.JFrame(), title, true);
        viewer.setLocationRelativeTo(null);

        Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int heightsemBarraTarefas = (int) maxBounds.getHeight();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        viewer.setBounds(0, 0, screenSize.width, heightsemBarraTarefas);
        viewer.setLocationRelativeTo(null);

        return this;
    }

    public JasperBuilder setSourceFileName(String fileName) {
        this.sourceFileName = getClass().getResourceAsStream(fileName);
        return this;
    }

    public JasperBuilder setParams(HashMap params) {
        this.params = params;
        return this;
    }

    public JasperBuilder setJdbcConnection(Connection connection) {
        this.jdbcConnection = connection;
        return this;
    }

    public void preview(String name) {
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(this.sourceFileName, this.params, this.jdbcConnection);
            jasperPrint.setName(name);

            JRCustomViewer jrCustomViewer = new JRCustomViewer(jasperPrint);
            jrCustomViewer.nameReport = name;
            viewer.add(jrCustomViewer);
            viewer.setVisible(true);
            
            viewer.dispose();
            viewer = null;
        } catch (JRException ex) {
            Logger.getLogger(JasperBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

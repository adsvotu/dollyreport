package br.edu.ifsp.vtp.core;

public class DollyReportFactory {
    
    public static JasperBuilder create(String title) {
        JasperBuilder jasperBuilder = new JasperBuilder();
        jasperBuilder.createViewer(title);
        return jasperBuilder;
    }
}

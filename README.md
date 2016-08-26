# dollyreport

DollReport is a library to simplify the call JasperReport reports. 

###*Example*

```
        try {
            
            DollyReportFactory
                    .create("Relatório Exemplo")
                    .setSourceFileName("/br/samples/report/RelSample.jasper")
                    .setParams(null)
                    .setJdbcConnection(JDBCConnection.getConnectOracle())
                    .preview("relatorio");
            
        } catch (IOException ex) {
            Logger.getLogger(RelSample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RelSample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RelSample.class.getName()).log(Level.SEVERE, null, ex);
        }
```

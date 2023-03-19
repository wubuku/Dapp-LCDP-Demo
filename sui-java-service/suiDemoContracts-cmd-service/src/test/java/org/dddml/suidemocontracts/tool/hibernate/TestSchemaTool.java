package org.dddml.suidemocontracts.tool.hibernate;

import org.dddml.suidemocontracts.tool.hibernate.SchemaTool;

public class TestSchemaTool {

    //private static String _sqlDirectory = "C:\\Users\\yangjiefeng\\Documents\\GitHub\\wubuku\\StarcoinNSDemo\\sui-java-service\\suiDemoContracts-cmd-service\\src\\generated\\resources\\scripts";
    private static String _sqlDirectory = "/Users/yangjiefeng/Documents/wubuku/StarcoinNSDemo/sui-java-service/suiDemoContracts-cmd-service/src/generated/resources/scripts";
    private static String _connectionString = "jdbc:mysql://127.0.0.1:3306/test2?enabledTLSProtocols=TLSv1.2&characterEncoding=utf8&serverTimezone=GMT%2b0&useLegacyDatetimeCode=false";
    private static String _username = "root";
    private static String _password = "123456";

    // //////////////////////////////////

    public static void main(final String[] args) throws Exception {
        SchemaTool t = new SchemaTool();//todo move SchemaTool to /src/main/java

        t.setSqlDirectory(_sqlDirectory);
        t.setConnectionString(_connectionString);
        if(_username != null && !_username.isEmpty()) {
            t.setDatabaseUsername(_username);
        }
        if(_password != null && !_password.isEmpty()) {
            t.setDatabasePassword(_password);
        }
        t.setUp();

        // ///////////////////////////////////////////////
        //if (args.length > 0 && "-u".equalsIgnoreCase(args[0])) {
        //    t.hbm2DdlUpdate();
        //    System.out.println("hbm2Ddl update ok.");
        //    System.exit(0);
        //    return;
        //}

        // //////////////////////////////////
        t.hbm2DdlOutput();
        System.out.println("hbm2DdlOutput ok.");
        t.copyAndFixHbm2DdlCreateSql();
        System.out.println("copyAndFixHbm2DdlCreateSql ok.");

        // /////////////////////////////////
        t.dropCreateDatabaseAndSeed();
        System.out.println("dropCreateDatabaseAndSeed ok.");

        System.exit(0);
    }

}

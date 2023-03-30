package org.dddml.suidemocontracts;

import org.dddml.suidemocontracts.tool.hibernate.SchemaTool;
import picocli.CommandLine;

public class ServiceCli {

    public static void main(final String[] args) throws Exception {
        /*
        sui-java-service % java -jar ./suiDemoContracts-service-cli/target/suidemocontracts-service-cli-0.0.1-SNAPSHOT.jar ddl -d "./suiDemoContracts-service/src/generated/resources/scripts" -c "jdbc:mysql://127.0.0.1:3306/test2?enabledTLSProtocols=TLSv1.2&characterEncoding=utf8&serverTimezone=GMT%2b0&useLegacyDatetimeCode=false" -u root -p 123456
         */
        // //////////////////////////////////

        // Parse command line arguments using Picocli
        CommandLine commandLine = new CommandLine(new TopLevelCommand());
        CommandLine.ParseResult parseResult = commandLine.parseArgs(args);

        // Call 'ddl' subcommand or 'test' subcommand
        if (parseResult.hasSubcommand()) {
            Object subcommand = parseResult.subcommands().get(0).commandSpec().userObject();
            if (subcommand instanceof DdlSubcommand) {
                ddl((DdlSubcommand) subcommand);
            }
            //else if (subcommand instanceof TestSubcommand) {
            //    test((TestSubcommand) subcommand);
            //}
        } else {
            // Print usage information if no subcommand is provided
            commandLine.usage(System.out);
        }

        System.exit(0);
    }

    // ddl method definition
    public static void ddl(DdlSubcommand ddlSubcommand) {

        SchemaTool t = new SchemaTool();

        t.setSqlDirectory(ddlSubcommand.sqlDirectory);
        t.setConnectionUrl(ddlSubcommand.connectionUrl);
        if (ddlSubcommand.username != null && !ddlSubcommand.username.isEmpty()) {
            t.setConnectionUsername(ddlSubcommand.username);
        }
        if (ddlSubcommand.password != null && !ddlSubcommand.password.isEmpty()) {
            t.setConnectionPassword(ddlSubcommand.password);
        }
        t.setUp();

        // //////////////////////////////////
        t.hbm2DdlOutput();
        System.out.println("hbm2DdlOutput ok.");
        t.copyAndFixHbm2DdlCreateSql();
        System.out.println("copyAndFixHbm2DdlCreateSql ok.");

        // /////////////////////////////////
        t.dropCreateDatabaseAndSeed();
        System.out.println("dropCreateDatabaseAndSeed ok.");
    }

    // Create a top-level command with subcommands 'ddl' and 'test'
    @CommandLine.Command(subcommands = {
            DdlSubcommand.class,
            //TestSubcommand.class
    })
    static class TopLevelCommand {
    }

    // DdlSubcommand class definition
    @CommandLine.Command(name = "ddl", mixinStandardHelpOptions = true, description = "Execute the ddl subcommand with the required parameters.")
    static class DdlSubcommand {
        @CommandLine.Option(names = {"-d", "--sqlDirectory"}, required = true, description = "SQL scripts input/output directory.")
        String sqlDirectory;

        @CommandLine.Option(names = {"-c", "--connectionUrl"}, required = true, description = "Connection URL.")
        String connectionUrl;

        @CommandLine.Option(names = {"-p", "--password"}, required = true, description = "Password.")
        String password;

        @CommandLine.Option(names = {"-u", "--username"}, required = true, description = "Username.")
        String username;
    }

//    // TestSubcommand class definition
//    @CommandLine.Command(name = "test", mixinStandardHelpOptions = true, description = "Execute the test subcommand.")
//    static class TestSubcommand {
//    }

}

package org.dddml.suidemocontracts.tool.hibernate;

import org.dddml.suidemocontracts.sui.contract.MoveObjectIdGeneratorObject;
import org.dddml.suidemocontracts.sui.contract.SuiPackage;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangjiefeng on 2018/2/4.
 */
public class SchemaTool {

    static final String ADD_FK_PATTERN = "alter table(\\s+)(\\w+)(\\s+)add constraint(\\s+)(\\w+)(\\s+)foreign key";
    static final String OLD_DROP_FK_PATTERN = "alter table(\\s+)(\\w+)(\\s+)drop foreign key(\\s+)(\\w+)";
    static final String NEW_DROP_FK_REPLACEMENT =
            "set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE" + "\r\n"
                    + "            CONSTRAINT_SCHEMA = DATABASE() AND" + "\r\n"
                    + "            TABLE_NAME        = '$2' AND" + "\r\n"
                    + "            CONSTRAINT_TYPE   = 'FOREIGN KEY');" + "\r\n"
                    + "set @sqlVar = if(@fkConstraintName is not null, " + "\r\n"
                    + "			concat('ALTER TABLE $2 drop foreign key ', @fkConstraintName)," + "\r\n"
                    + "            'select 1');" + "\r\n"
                    + "prepare stmt from @sqlVar;" + "\r\n"
                    + "execute stmt;" + "\r\n"
                    + "deallocate prepare stmt";

    static final String FILENAME_DROP_RVIEWS = "DropRViews.sql";
    static final String FILENAME_DROP_STATE_ID_FK_CONSTRAINTS = "DropStateIdForeignKeyConstraints.sql";
    static final String FILENAME_HBM2DDL_CREATE_FIX = "hbm2ddl_create_fix.sql";
    static final String FILENAME_HBM2DDL_CREATE = "hbm2ddl_create.sql";
    static final String FILENAME_HBM2DDL_UPDATE = "hbm2ddl_update.sql";
    static final String FILENAME_DROP_RVIEW_NAME_CONFLICTED_TABLES = "DropRViewNameConflictedTables.sql";
    static final String FILENAME_CREATE_RVIEWS = "CreateRViews.sql";
    static final String FILENAME_ADD_STATE_ID_FK_CONSTRAINTS = "AddStateIdForeignKeyConstraints.sql";
    private static final String SQL_DELIMITER = ";";
    private String _sqlDirectory;
    private String _databaseUsername = "root";
    private String _databasePassword = "123456";
    // ///////////////////////////////////
    private String _connectionString;

    private static boolean isSqlEmpty(String sql) {
        String[] lines = sql.split("\\r?\\n", 100);
        for (String line : lines) {
            if (!line.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public final String getSqlDirectory() {
        return _sqlDirectory;
    }

    public final void setSqlDirectory(String value) {
        _sqlDirectory = value;
    }

    public String getDatabaseUsername() {
        return _databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this._databaseUsername = databaseUsername;
    }

    public String getDatabasePassword() {
        return _databasePassword;
    }

    public void setDatabasePassword(String value) {
        _databasePassword = value;
    }

    public String getConnectionString() {
        return _connectionString;
    }

    public void setConnectionString(String connStr) {
        _connectionString = connStr;
    }

    public void setUp() {
    }

    public void dropCreateDatabaseAndSeed() {
        String connString = getConnectionString();
        dropCreateDatabase(connString);
        seedDatabase(connString);
    }

    public void seedDatabase(String connString) {
    }

    public void dropCreateDatabase(String connString) {
        org.hibernate.cfg.Configuration cfg = getHibernateConfiguration(connString);

        String[] fns = getDropCreateFileNames();

        SessionFactory sf = cfg.buildSessionFactory();
        for (String fn : fns) {
            String filePath = Path.combine(getSqlDirectory(), fn);
            if (!FileUtils.fileExists(filePath)) {
                continue;
            }
            String sql = FileUtils.readUtf8TextFile(filePath);
            if (isSqlEmpty(sql)) {
                continue;
            }
            Session session = sf.openSession();
            try {
                session.beginTransaction();
                SQLQuery<?> query = session.createSQLQuery(sql);
                query.executeUpdate();
                session.getTransaction().commit();

                System.out.println("execute sql [" + filePath + "], ok.");
            } finally {
                session.close();
            }
        }
    }

    private String[] getDropCreateFileNames() {
        return new String[]{
                FILENAME_DROP_RVIEWS,
                FILENAME_DROP_STATE_ID_FK_CONSTRAINTS,
                FILENAME_HBM2DDL_CREATE_FIX,
                FILENAME_DROP_RVIEW_NAME_CONFLICTED_TABLES,
                FILENAME_CREATE_RVIEWS,
                FILENAME_ADD_STATE_ID_FK_CONSTRAINTS
        };
    }


    public final void hbm2DdlOutput() {
        String connString = getConnectionString();
        org.hibernate.cfg.Configuration cfg = getHibernateConfiguration(connString);

        // ///////////////////////////////////////////////////////
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        for (String s : getHbmResourceNames()) {
            metadataSources.addResource(s);
        }
        metadataSources.addAnnotatedClass(MoveObjectIdGeneratorObject.class);
        metadataSources.addAnnotatedClass(SuiPackage.class);

        MetadataImplementor metadata = (MetadataImplementor) metadataSources.getMetadataBuilder()
                .applyPhysicalNamingStrategy(new SpringPhysicalNamingStrategy())
                .build();

        // /////////////////// update ///////////////////////////
        // Generate ddl update script
        hbm2DdlOutputUpdate(metadata);
        // //////////////////////////////////////////////////////

        SchemaExport schemaExport = new SchemaExport();
        String dropFilePath = Path.combine(getSqlDirectory(), "hbm2ddl_drop.sql");
        FileUtils.deleteIfExists(dropFilePath);
        schemaExport.setOutputFile(dropFilePath).setDelimiter(SQL_DELIMITER).drop(EnumSet.of(TargetType.SCRIPT), metadata);


        String createFilePath = Path.combine(getSqlDirectory(), FILENAME_HBM2DDL_CREATE);
        FileUtils.deleteIfExists(createFilePath);
        schemaExport.setOutputFile(createFilePath).setDelimiter(SQL_DELIMITER).create(EnumSet.of(TargetType.SCRIPT), metadata);
    }

    private void hbm2DdlOutputUpdate(MetadataImplementor metadata) {
        SchemaUpdate schemaUpdate = new SchemaUpdate();
        String fileName = (new SimpleDateFormat("yyyyMMddHHmm").format(new Date())) + FILENAME_HBM2DDL_UPDATE;
        String updateFilePath = Path.combine(getSqlDirectory(), fileName);
        FileUtils.deleteIfExists(updateFilePath);
        schemaUpdate.setOutputFile(updateFilePath).setDelimiter(SQL_DELIMITER).execute(EnumSet.of(TargetType.SCRIPT), metadata);
    }

    public final void copyAndFixHbm2DdlCreateSql() {
        String srcFilePath = Path.combine(getSqlDirectory(), FILENAME_HBM2DDL_CREATE);
        String dstFilePath = Path.combine(getSqlDirectory(), FILENAME_HBM2DDL_CREATE_FIX);

        String sql = FileUtils.readUtf8TextFile(srcFilePath);

        List<String> tableNames = getFKTableNames(sql);

        StringBuilder sb = new StringBuilder();
        for (String t : tableNames) {
            sb.append(NEW_DROP_FK_REPLACEMENT.replace("$2", t));
            sb.append(SQL_DELIMITER);
            sb.append("\r\n");
        }

        String newSql = sql.replaceAll(OLD_DROP_FK_PATTERN, NEW_DROP_FK_REPLACEMENT);//rgx.Replace(sql, NEW_DROP_FK_REPLACEMENT);
        newSql = newSql.replaceAll(" type=MyISAM", "");
        //System.out.println(newSql);

        sb.append(newSql);

        FileUtils.writeUtf8TextFile(dstFilePath, sb.toString());
    }

    private List<String> getFKTableNames(String sql) {
        Pattern pattern = Pattern.compile(ADD_FK_PATTERN);
        Matcher m = pattern.matcher(sql);

        List<String> tableNames = new ArrayList<String>();
        //System.out.println("============================");
        // Find all matches
        while (m.find()) {
            // Get the matching string
            String tableName = m.group(2);
            tableNames.add(tableName);
            //System.out.println(tableName);
        }
        //System.out.println("============================");
        return tableNames;
    }

    private org.hibernate.cfg.Configuration getHibernateConfiguration(String connString) {
        String connAllowOpt = "allowMultiQueries=true";
        if (!connString.toLowerCase().contains((connAllowOpt).toLowerCase())) {
            connString = connString + (connString.endsWith("&") ? "" : "&") + connAllowOpt;
        }
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        cfg.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        cfg.setProperty("hibernate.connection.url", connString);
        cfg.setProperty("hibernate.connection.username", getDatabaseUsername());
        cfg.setProperty("hibernate.connection.password", getDatabasePassword());
        cfg.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.EhCacheProvider");
        cfg.setProperty("hibernate.cache.use_second_level_cache", "false");

        List<String> resourceNames = getHbmResourceNames();

        for (String s : resourceNames) {
            cfg.addResource(s);
        }
        return cfg;
    }

    private List<String> getHbmResourceNames() {
        String[] locationPatterns = new String[]{
//                "classpath:/org/dddml/suidemocontracts/domain/hbm/*.hbm.xml",
//                "classpath:/org/dddml/suidemocontracts/tool/hibernate/hbm/*.hbm.xml"
                "classpath:/hibernate/*.hbm.xml"};
        List<String> resourceNames = new ArrayList<String>();
        for (String locationPattern : locationPatterns) {
            resourceNames.addAll(getHbmResourceNames(locationPattern));
        }
        return resourceNames;
    }

    private List<String> getHbmResourceNames(String locationPattern) {
        List<String> resourceNames = new ArrayList<String>();
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        Resource[] resources = new Resource[0];
        try {
            resources = resourcePatternResolver.getResources(locationPattern);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // //////////////////////////
        String classpathDirPath = locationPattern.substring("classpath:/".length(), locationPattern.lastIndexOf("*.hbm.xml"));
        //System.out.println(classpathDirPath);

        for (Resource m : resources) {
            try {
                String urlPath = m.getURL().getPath();
                int i = urlPath.lastIndexOf(classpathDirPath);
                resourceNames.add(urlPath.substring(i));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return resourceNames;
    }


    static class FileUtils {

        static final String UTF8_BOM = "\uFEFF";

        static void deleteIfExists(String path) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }

        static boolean fileExists(String path) {
            File file = new File(path);
            return file.exists();
        }

        static String readUtf8TextFile(String path) {

            StringBuilder sb = new StringBuilder();
            try {
                File fileDir = new File(path);

                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), StandardCharsets.UTF_8));

                String str;
                boolean firstLine = true;
                while ((str = in.readLine()) != null) {
                    //System.out.println(str);
                    if (firstLine && str.startsWith(UTF8_BOM)) {
                        str = str.substring(1);
                    }

                    sb.append(str);
                    sb.append("\r\n");
                    firstLine = false;
                }

                in.close();
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return sb.toString();
        }

        static void writeUtf8TextFile(String path, String str) {
            File file = new File(path);

            Writer out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                out.write(str);
                out.flush();
                out.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    static class Path {
        static String combine(String... paths) {
            File file = new File(paths[0]);

            for (int i = 1; i < paths.length; i++) {
                file = new File(file, paths[i]);
            }

            return file.getPath();
        }
    }

}

package io.github.joeypekar.scorekeeper.db;

import io.github.joeypekar.scorekeeper.Scoreboard;
import io.github.joeypekar.scorekeeper.util.Settings;

import java.lang.reflect.Field;
import java.sql.*;

public final class DBUtils {

    public static <C extends Scoreboard> boolean doesTableExist(Class<C> scoreboardClass, Settings settings) {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(settings.getUrl(), settings.getUsername(), settings.getPassword());
            DatabaseMetaData dbm = conn.getMetaData();

            ResultSet results = dbm.getTables(null, null, scoreboardClass.getSimpleName(), null);

            conn.close();

            if (results.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;

    }

    public static <C extends Scoreboard> void createTable(Class<C> scoreboardClass, Settings settings) {

        if (!doesTableExist(scoreboardClass, settings)) {

            String query = "CREATE TABLE " + scoreboardClass.getSimpleName() + " (id varchar(128) primary key not null";

            for (Field f : scoreboardClass.getDeclaredFields()) {

                String typeName = f.getType().getSimpleName();

                if (typeName.equalsIgnoreCase("int")) {

                    query += ", " + f.getName() + " INT";

                } else if (typeName.equalsIgnoreCase("double")) {

                    query += ", " + f.getName() + " DOUBLE";

                }


            }

            query += ");";


            Connection conn = null;
            Statement stmt = null;

            try {

                conn = DriverManager.getConnection(settings.getUrl(), settings.getUsername(), settings.getPassword());
                stmt = conn.createStatement();

                stmt.execute(query);

                System.out.println(scoreboardClass.getSimpleName() + " table created in database");

                stmt.close();
                conn.close();

            } catch (SQLException e) {
                System.out.println(e);

            }

        } else {
            System.out.println("Table already exists!");
        }

    }

    /**
     *
     * @param id ID that serves as the Primary Key in the database. This will commonly be a username or unique id.
     * @param scoreboardClass Class that the scoreboard object is.
     * @param settings Universal settings that detail what the database will be.
     * @param <C> Class extending Scoreboard.java
     * @return returns a new instance of the class.
     */
    public static <C extends Scoreboard> C readFromDatabase(String id, Class<C> scoreboardClass, Settings settings) {
        C user;

        try {
             user = scoreboardClass.getDeclaredConstructor().newInstance();
            user.setId(id);

             Field[] fields = scoreboardClass.getDeclaredFields();

             String query = "select id";

             for (Field f : fields) {

                query += ", " + f.getName();

             }

             query += " from " + scoreboardClass.getSimpleName() + " where id=\"" + id + "\" LIMIT 1;";

            Connection conn = DriverManager.getConnection(settings.getUrl(), settings.getUsername(), settings.getPassword());
            Statement stmt = conn.createStatement();

            ResultSet results = stmt.executeQuery(query);
            results.next();

            for (Field f : fields) {

                f.setAccessible(true);

                if (f.getType().getSimpleName().equals("int")) {

                    f.set(user, results.getInt(f.getName()));

                } else if (f.getType().getSimpleName().equals("double")) {

                    f.set(user, results.getDouble(f.getName()));

                }

                f.setAccessible(false);



            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

        return user;

    }

    public static <C extends Scoreboard> void writeToDatabase(C user, Settings settings) {

        Class<C> scoreboardClass = (Class<C>) user.getClass();

        try {

            Field[] fields = scoreboardClass.getDeclaredFields();

            /* Build Query */
            String query = "INSERT INTO " + scoreboardClass.getSimpleName() + "(id";

            for (Field f : fields) {

                query += ", " + f.getName();

            }

            query += ") VALUES ('" + user.getId() + "'";

            for (Field f : fields) {

                f.setAccessible(true);
                query += ", " + f.get(user);

            }

            query += ") ON DUPLICATE KEY UPDATE id = '" + user.getId() + "'";

            for (Field f : fields) {

                query += ", " + f.getName() + " = " + f.get(user);
                f.setAccessible(false);

            }

            query += ";";


            Connection conn = DriverManager.getConnection(settings.getUrl(), settings.getUsername(), settings.getPassword());
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            stmt.close();
            conn.close();


        } catch (Exception e) {

            System.out.println(e.getMessage());

        }


    }

    public static <C extends Scoreboard> boolean doesRowExist(String id, Class<C> scoreboardClass, Settings settings) {

        try {

            Connection conn = DriverManager.getConnection(settings.getUrl(), settings.getUsername(), settings.getPassword());
            String query = "SELECT 1 FROM " + scoreboardClass.getSimpleName() + " WHERE id = \"" + id + "\";";

            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet results = stmt.executeQuery();

            boolean exists = results.next();

            stmt.close();
            conn.close();

            if (exists) {
                return true;
            }


        } catch(Exception e) {
            System.out.println(e.getMessage());
           }

        return false;

    }

}

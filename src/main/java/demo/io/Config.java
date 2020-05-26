package demo.io;

import io.github.joeypekar.scorekeeper.util.Settings;

public class Config {

    public static Settings loadConfig() {

        Settings settings = new Settings();

        settings.setUrl("jdbc:mysql://127.0.0.1:3306/scorekeeper_test");
        settings.setUsername("root");
        settings.setPassword("");

        return settings;

    }

}

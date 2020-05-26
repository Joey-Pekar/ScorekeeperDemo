package io.github.joeypekar.scorekeeper;

import io.github.joeypekar.scorekeeper.db.DBUtils;
import io.github.joeypekar.scorekeeper.util.Board;
import io.github.joeypekar.scorekeeper.util.Settings;

import java.util.HashMap;

public final class Scorekeeper {

    public final HashMap<String, Board> activeBoards;
    private final Settings settings;

    public Scorekeeper(Settings settings) {

        this.settings = settings;

        activeBoards = new HashMap<String, Board>();

    }

    public <C extends Scoreboard> void addScoreboard(String name, Class<C> scoreboardClass) {

            if (activeBoards.containsKey(name)) {

                System.out.println("Error: Board Name already in use.");
                return;

            }

            Board Scoreboard = new Board(scoreboardClass, settings);

            if (!DBUtils.doesTableExist(scoreboardClass, settings)) {

                DBUtils.createTable(scoreboardClass, settings);

            }

            activeBoards.put(name, Scoreboard);


    }


}

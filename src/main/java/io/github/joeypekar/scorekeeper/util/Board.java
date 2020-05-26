package io.github.joeypekar.scorekeeper.util;

import io.github.joeypekar.scorekeeper.Scoreboard;
import io.github.joeypekar.scorekeeper.db.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board<C extends Scoreboard> {

    Settings settings;

    Class<C> scoreboardClass;
    public HashMap<String, C> members;

    public Board(Class<C> scoreboard, Settings settings) {

        this.scoreboardClass = scoreboard;

        members = new HashMap<String, C>();

        this.settings = settings;

    }

    public void login(String id) {

        // TODO: Create Login
        if (!members.containsKey(id)) {

            if (DBUtils.doesRowExist(id, scoreboardClass, settings)) {

                members.put(id, DBUtils.readFromDatabase(id, scoreboardClass, settings));

            } else {

                try {
                    members.put(id, scoreboardClass.newInstance());
                    members.get(id).setId(id);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

        }



    }

    public void logout(String id) {

        // TODO: Create Logout
        if (members.containsKey(id)) {

            save(id);
            members.remove(id);

        }

    }

    public void save(String id) {

        // TODO: Create Save

        if (members.containsKey(id)) {

            save(members.get(id));

        }


    }

    private void save(C member) {

        DBUtils.writeToDatabase(member, settings);


    }

    public void saveAll() {

        for (C member : members.values() ) {

            save(member);

        }

    }
}

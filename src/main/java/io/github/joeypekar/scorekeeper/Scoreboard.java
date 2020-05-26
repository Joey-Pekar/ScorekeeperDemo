package io.github.joeypekar.scorekeeper;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Scoreboard {

    /**
     * The Primary Key that will be used for the database.
     * */
    private String id;

    public Scoreboard() {


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

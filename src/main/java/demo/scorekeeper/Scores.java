package demo.scorekeeper;

import io.github.joeypekar.scorekeeper.Scoreboard;

public class Scores extends Scoreboard {

    private int blueBlockClick = 0;
    private int redBlockClick = 0;
    private int greenBlockClick = 0;

    public int getBlueBlockClick() {
        return blueBlockClick;
    }

    public int getRedBlockClick() {
        return redBlockClick;
    }

    public int getGreenBlockClick() {
        return greenBlockClick;
    }

    public void addBlueClick() {

        blueBlockClick += 1;

    }

    public void addRedClick() {

        redBlockClick += 1;

    }

    public void addGreenClick() {

        greenBlockClick += 1;

    }
}

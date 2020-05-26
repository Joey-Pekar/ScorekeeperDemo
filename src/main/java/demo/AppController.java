package demo;

import demo.io.Config;
import demo.scorekeeper.Scores;
import io.github.joeypekar.scorekeeper.Scorekeeper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AppController {

    final String BOARD = "Scores";

    Scorekeeper scorekeeper;
    boolean loggedIn = false;
    String user;

    public AppController() {

        scorekeeper = new Scorekeeper(Config.loadConfig());

        scorekeeper.addScoreboard(BOARD, Scores.class);

    }

    @FXML
    private TextField txtUsername;

    @FXML
    private Button btnGreen;
    @FXML
    private Button btnBlue;
    @FXML
    private Button btnRed;

    @FXML
    private Text txtTotal;
    @FXML
    private Text txtGreen;
    @FXML
    private Text txtBlue;
    @FXML
    private Text txtRed;

    @FXML
    private GridPane StatsPane;

    private void update(Scores s) {

        int r, b, g;
        r = s.getRedBlockClick();
        b = s.getBlueBlockClick();
        g = s.getGreenBlockClick();

        txtRed.setText(Integer.toString(r));
        txtBlue.setText(Integer.toString(b));
        txtGreen.setText(Integer.toString(g));

        txtTotal.setText(Integer.toString(r + b + g));


    }

    @FXML
    protected void handleLogin(ActionEvent e) {

        String username = txtUsername.getText().trim();

        if (!username.equals("")) {

            scorekeeper.activeBoards.get(BOARD).login(username);
            loggedIn = true;
            user = username;

            btnGreen.setDisable(false);
            btnBlue.setDisable(false);
            btnRed.setDisable(false);
            update(((Scores) scorekeeper.activeBoards.get(BOARD).members.get(user)));
            StatsPane.setVisible(true);


        }

    }

    @FXML
    protected void handleLogout(ActionEvent e) {

        if (loggedIn) {

            scorekeeper.activeBoards.get(BOARD).logout(user);
            loggedIn = false;

            btnGreen.setDisable(true);
            btnBlue.setDisable(true);
            btnRed.setDisable(true);
            StatsPane.setVisible(false);

        }

    }

    @FXML
    protected void handleGreen(ActionEvent e) {

        if (loggedIn) {

            ((Scores) scorekeeper.activeBoards.get(BOARD).members.get(user)).addGreenClick();
            update(((Scores) scorekeeper.activeBoards.get(BOARD).members.get(user)));

        }

    }

    @FXML
    protected void handleBlue(ActionEvent e) {

        if (loggedIn) {

            ((Scores) scorekeeper.activeBoards.get(BOARD).members.get(user)).addBlueClick();
            update(((Scores) scorekeeper.activeBoards.get(BOARD).members.get(user)));

        }

    }

    @FXML
    protected void handleRed(ActionEvent e) {

        if (loggedIn) {

            ((Scores) scorekeeper.activeBoards.get(BOARD).members.get(user)).addRedClick();
            update(((Scores) scorekeeper.activeBoards.get(BOARD).members.get(user)));

        }

    }

}

package demo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/App.fxml"));

        Parent root = loader.load();
        root.getStylesheets().add("/style.css");

        primaryStage.setScene(new Scene(root));

        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                AppController controller = loader.getController();
                controller.scorekeeper.activeBoards.get(controller.BOARD).saveAll();

            }
        });

    }
}

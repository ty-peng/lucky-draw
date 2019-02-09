package com.pty.view;

import com.pty.controller.MainController;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 主界面
 * 
 * @author pty
 *
 */
public class MainStage extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("抽奖系统");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				event.consume();
				new MainController().quitConfirm();
				System.exit(0);
			}
		});
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}

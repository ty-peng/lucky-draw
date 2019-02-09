package com.pty.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 提示窗口
 * 
 * @author pty
 *
 */
public class MessageDialog {

	public static void createMessageDialog(String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("提示");
		alert.setHeaderText("提示");
		alert.setContentText(message);
		alert.showAndWait();
	}
}

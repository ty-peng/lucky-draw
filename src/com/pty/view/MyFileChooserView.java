package com.pty.view;

import java.io.File;

import javafx.stage.FileChooser;

/**
 * 文件选择器界面
 * 
 * @author pty
 *
 */
public class MyFileChooserView {

	public static File chooseFile() {
		File file = null;
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("文本文件(*.txt)", "*.txt"),
					new FileChooser.ExtensionFilter("二进制文件(*.dat)", "*.dat"));
			fileChooser.setInitialDirectory(
					new File("./")
					);
			file = fileChooser.showOpenDialog(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}
	
	public static File chooseSaveFile(String filename) {
		File file = null;
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName(filename);
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("文本文件 (*.txt)", "*.txt"),
					new FileChooser.ExtensionFilter("二进制文件 (*.dat)",
							"*.dat"));
			fileChooser.setInitialDirectory(
					new File("./")
					);
			file = fileChooser.showSaveDialog(null);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return file;
	}
	
}

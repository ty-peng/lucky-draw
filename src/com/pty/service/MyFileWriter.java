package com.pty.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.pty.model.Person;
import com.pty.view.MessageDialog;


/**
 * 写文件
 * 
 * @author pty
 *
 */
public class MyFileWriter {

	public void saveFile(File file, ArrayList<Person> persons) {
		try {
			if (file.getName().toLowerCase().endsWith(".txt")) {
				BufferedWriter bw = null;
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
				for (Person person : persons) {
					bw.write(person.getPersonNum() + ","
							+ person.getName() + "," + person.getPhone());
					bw.newLine();
				}
				bw.flush();
				bw.close();
			} else if (file.getName().toLowerCase().endsWith(".dat")) {
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(persons);
				oos.flush();
				oos.close();
			} else {
				MessageDialog.createMessageDialog("文件后缀名错误！");
			}
		} catch (FileNotFoundException e) {
			MessageDialog.createMessageDialog("找不到文件！");
			e.printStackTrace();
		} catch (IOException e) {
			MessageDialog.createMessageDialog("保存文件错误！");
			e.printStackTrace();
		}
		MessageDialog.createMessageDialog("保存成功！");
	}
}

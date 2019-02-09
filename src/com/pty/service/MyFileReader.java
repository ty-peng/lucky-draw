package com.pty.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.pty.model.Person;
import com.pty.view.MessageDialog;

/**
 * 读文件
 * 
 * @author pty
 *
 */
public class MyFileReader {

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Person> analyzeFile() {
		ArrayList<Person> persons = new ArrayList<Person>();
		if (file.getName().toLowerCase().endsWith(".txt")) {
			try {
				BufferedReader br = null;
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					String[] obj = line.split(",");
					if (obj.length == 3) {
						Person person = new Person(obj[0], obj[1], obj[2]);
						persons.add(person);
					}
				}
				br.close();
				if (persons.size() == 0)
					MessageDialog.createMessageDialog("读取失败或者文件不符合格式！");
				else
					MessageDialog.createMessageDialog("读取成功！");
			} catch (FileNotFoundException e) {
				MessageDialog.createMessageDialog("找不到文件！");
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				MessageDialog.createMessageDialog("不支持GBK编码错误！");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				MessageDialog.createMessageDialog("txt文件内容格式错误！");
				e.printStackTrace();
			}
			return persons;
		} else if (file.getName().toLowerCase().endsWith(".dat")) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				persons = (ArrayList<Person>) ois.readObject();
				ois.close();
				MessageDialog.createMessageDialog("读取成功！");
			} catch (FileNotFoundException e) {
				MessageDialog.createMessageDialog("找不到文件！");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return persons;
		} else {
			MessageDialog.createMessageDialog("文件后缀名错误！");
		}
		return null;
	}

}

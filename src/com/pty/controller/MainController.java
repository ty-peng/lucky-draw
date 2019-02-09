package com.pty.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.pty.model.Person;
import com.pty.service.MyFileReader;
import com.pty.service.MyFileWriter;
import com.pty.view.MessageDialog;
import com.pty.view.MyFileChooserView;
import com.pty.view.infoCellEditingView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * 主控制器
 * 
 * @author pty
 *
 */
public class MainController implements Initializable {

	String fileName;
	private ArrayList<Person> persons = null;
	private ArrayList<Person> drawedPersons = null;
	private ArrayList<Person> undrawedPersons = null;

	@FXML
	private TextField numOfDraw;
	@FXML
	private TextField projectName;
	@FXML
	private TextArea drawStatus;
	@FXML
	private Text sumOfPeople;
	@FXML
	private Text ioStatus;
	@FXML
	private TabPane tabPane;
	@FXML
	public TableView<Person> drawedPeopleView;
	@FXML
	public TableView<Person> undrawedPeopleView;
	@FXML
	public TableView<Person> totalPeopleView;

	public TableColumn<Person, String> personNum = new TableColumn<Person, String>(" 学号/工号 ");
	public TableColumn<Person, String> name = new TableColumn<Person, String>(" 姓名 ");
	public TableColumn<Person, String> phone = new TableColumn<Person, String>(" 手机号码 ");
	
	public TableColumn<Person, String> drawedPersonNum = new TableColumn<Person, String>(" 学号/工号 ");
	public TableColumn<Person, String> drawedName = new TableColumn<Person, String>(" 姓名 ");
	public TableColumn<Person, String> drawedPhone = new TableColumn<Person, String>(" 手机号码 ");
	
	public TableColumn<Person, String> undrawedPersonNum = new TableColumn<Person, String>(" 学号/工号 ");
	public TableColumn<Person, String> undrawedName = new TableColumn<Person, String>(" 姓名 ");
	public TableColumn<Person, String> undrawedPhone = new TableColumn<Person, String>(" 手机号码 ");
	
	@FXML
	private ObservableList<Person> columns = FXCollections.observableArrayList();

	private boolean continueFlag = true;
	private static boolean saveFlag = true;

	/**
	 * 加载文件处理
	 * 
	 * @param event
	 */
	@FXML
	public void loadFile(ActionEvent event) {
		MyFileReader fileLloader = new MyFileReader();
		fileLloader.setFile(MyFileChooserView.chooseFile());
		if (fileLloader.getFile() != null) {
			try {
				persons = fileLloader.analyzeFile();
			} catch (NumberFormatException e) {
				MessageDialog.createMessageDialog("文件格式错误，请重新编排文件格式！");
				e.printStackTrace();
			}
			if (persons.size() != 0) {
				ObservableList<Person> data = FXCollections.observableArrayList(persons);
				int total = persons.size();
				ioStatus.setText("文件读取成功！");
				sumOfPeople.setText("共 " + total + " 人");
				totalPeopleView.setItems(data);
				// 选择所有名单Tab页 （第三个标签）
				tabPane.getSelectionModel().select(2);
				saveFlag = false;
			}
		} else {
			MessageDialog.createMessageDialog("文件为空！");
		}
		
	}

	/**
	 * 保存文件处理
	 * 
	 * @param event
	 */
	@FXML
	public void saveFile(ActionEvent event) {
		MyFileWriter myFileWriter = new MyFileWriter();
		if (drawedPersons == null || drawedPersons.isEmpty()) {
			MessageDialog.createMessageDialog("抽中名单未载入！");
			return;
		}
		File file = null;
		file = MyFileChooserView
				.chooseSaveFile(projectName.getText().equals("")? "新抽奖项目抽中名单" : projectName.getText() + "抽中名单");
		if (file != null) {
			myFileWriter.saveFile(file, drawedPersons);
		}
		if (undrawedPersons == null || undrawedPersons.isEmpty()) {
			MessageDialog.createMessageDialog("未中名单未载入！");
			return;
		}
		File undrawedfile = null;
		undrawedfile = MyFileChooserView
				.chooseSaveFile(projectName.getText().equals("") ? "新抽奖项目未中名单" : projectName.getText() + "未中名单");
		if (undrawedfile != null) {
			myFileWriter.saveFile(undrawedfile, undrawedPersons);
		}
		saveFlag = true;
		ioStatus.setText("文件保存成功！");
	}

	/**
	 * 清空所有内容
	 * 
	 * @param event
	 */
	@FXML
	public void clearTable(ActionEvent event) {
		ioStatus.setText("");
		drawStatus.setText("");
		numOfDraw.setText("");
		projectName.setText("");
		sumOfPeople.setText("");
		if (persons != null) {
			persons.clear();
			if (drawedPersons != null)
				drawedPersons.clear();
			if (undrawedPersons != null)
				undrawedPersons.clear();
			ObservableList<Person> data = FXCollections.observableArrayList(persons);
			totalPeopleView.setItems(data);
			drawedPeopleView.setItems(data);
			undrawedPeopleView.setItems(data);
		}
	}

	/**
	 * 关闭退出
	 * 
	 * @param event
	 */
	@FXML
	public void closeAll(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * 弹出帮助
	 * 
	 * @param event
	 */
	@FXML
	public void openHelp(ActionEvent event) {
		MessageDialog.createMessageDialog("用电脑来进行摇号、抽签和摇奖，广泛应用于学号摇号、晚会抽奖、出场顺序的抽签等场合。\n"
				+ "1、每个参与对象从文件中读取，每个对象占一行，文件名可以为《参加摇号.txt》。（注意，行与行之间不能有空行）\n" + "2、在上面输入摇号项目的名称和要摇出的人数。\n"
				+ "一、点击  文件->【调入数据】后会在主窗口显示全部参加摇号对象的信息，同时在右边会统计出参加摇号的总人数。\n"
				+ " 二、点击  文件->【开始摇号】后电脑正式进行人工无法干预的随机抽取选中过程。此后再点击【停止滚动】就完成了摇号活动。\n"
				+ " 三、点击【抽中对象】后会在主窗口显示全部摇号时被摇中对象的信息。\n" + " 四、点击【未中对象】后会在主窗口显示剩余没有被摇中对象的信息。\n"
				+ "五、点击  文件->【保存结果】后会以摇号项目名称.txt和未中对象.txt两个文本文件分别保存本次摇中、未中对象。\n" + " 六、点击【帮助】后会在主窗口再次显示本说明。\n");
	}

	@FXML
	private Button stopBtn;
	
	/**
	 * 开始抽取
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@FXML
	public void startDraw(ActionEvent event) {
		drawedPersons = new ArrayList<Person>();
		undrawedPersons = new ArrayList<Person>();
		if (persons != null) {
			int num;
			try {
				num = Integer.parseInt(numOfDraw.getText());
			} catch (Exception e) {
				MessageDialog.createMessageDialog("请正确输入抽取人数！");
				e.printStackTrace();
				return;
			}
			continueFlag = true;
			Person tperson;
			int total = persons.size();
			if(num>total){
				MessageDialog.createMessageDialog("抽取人数不能大于总人数！");
				return;
			}
			stopBtn.setDisable(false);
			undrawedPersons = (ArrayList<Person>) persons.clone();
			for (int i = 0; i < num && continueFlag == true; i++) {
				int index = (int) (Math.round(Math.random() * total));
				tperson = undrawedPersons.get(index>0?index-1:index);
				drawedPersons.add(tperson);
				undrawedPersons.remove(tperson);
				total = undrawedPersons.size();
				
			}
			drawedTableInitialize();
			undrawedTableInitialize();

			ObservableList<Person> draweddata = FXCollections.observableArrayList(drawedPersons);
			drawedPeopleView.setItems(draweddata);
			ObservableList<Person> undraweddata = FXCollections.observableArrayList(undrawedPersons);
			undrawedPeopleView.setItems(undraweddata);

			stopBtn.setDisable(true);
			tabPane.getSelectionModel().select(0);
			MessageDialog.createMessageDialog("抽取成功！");
			drawStatus.setText("\n"
					+ "抽取成功！\n"
					+ "双击手机号码可编辑\n"
					+ "再次点击开始抽取可重新抽取\n"
					+ "保存快捷键Ctrl+S\n");
		} else {
			MessageDialog.createMessageDialog("所有名单列表为空！");
		}

	}

	/**
	 * 抽中名单初始化
	 */
	@SuppressWarnings("unchecked")
	private void drawedTableInitialize() {
		drawedPersonNum.setCellValueFactory(new PropertyValueFactory<Person, String>("personNum"));
		drawedPersonNum.setPrefWidth(130);
		drawedName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		drawedName.setPrefWidth(90);
		drawedPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
		drawedPhone.setPrefWidth(150);
		drawedPeopleView.getColumns().clear();
		drawedPeopleView.setEditable(true);

		drawedPhone.setEditable(true);
		Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactoryNum = new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
			public TableCell<Person, String> call(TableColumn<Person, String> p) {
				return new infoCellEditingView();
			}
		};
		drawedPhone.setCellFactory(cellFactoryNum);
		drawedPhone.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(CellEditEvent<Person, String> drawedPhone) {
				((Person) drawedPhone.getTableView().getItems().get(drawedPhone.getTablePosition().getRow()))
						.setPhone(drawedPhone.getNewValue());
			}
		});

		drawedPeopleView.getColumns().addAll(drawedPersonNum,drawedName, drawedPhone);

	}

	/**
	 * 未抽中列表初始化
	 */
	@SuppressWarnings("unchecked")
	private void undrawedTableInitialize() {
		undrawedPersonNum.setCellValueFactory(new PropertyValueFactory<Person, String>("personNum"));
		undrawedPersonNum.setPrefWidth(130);
		undrawedName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		undrawedName.setPrefWidth(90);
		undrawedPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
		undrawedPhone.setPrefWidth(150);
		undrawedPeopleView.getColumns().clear();
		undrawedPeopleView.setEditable(true);

		undrawedPhone.setEditable(true);
		Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactoryNum = new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
			public TableCell<Person, String> call(TableColumn<Person, String> p) {
				return new infoCellEditingView();
			}
		};
		undrawedPhone.setCellFactory(cellFactoryNum);
		undrawedPhone.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(CellEditEvent<Person, String> undrawedPhone) {
				((Person) undrawedPhone.getTableView().getItems().get(undrawedPhone.getTablePosition().getRow()))
						.setPhone(undrawedPhone.getNewValue());
			}
		});

		undrawedPeopleView.getColumns().addAll(undrawedPersonNum, undrawedName, undrawedPhone);
	}

	/**
	 * 停止抽取
	 * 
	 * @param event
	 */
	@FXML
	public void stopDraw(ActionEvent event) {
		continueFlag = false;
	}

	/**
	 * 初始化
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		personNum.setStyle("-fx-alignment:CENTER;");
		name.setStyle("-fx-alignment:CENTER;");
		phone.setStyle("-fx-alignment:CENTER;");
		drawedPersonNum.setStyle("-fx-alignment:CENTER;");
		drawedName.setStyle("-fx-alignment:CENTER;");
		drawedPhone.setStyle("-fx-alignment:CENTER;");
		undrawedPersonNum.setStyle("-fx-alignment:CENTER;");
		undrawedName.setStyle("-fx-alignment:CENTER;");
		undrawedPhone.setStyle("-fx-alignment:CENTER;");
		tableViewInitialize();
	}

	/**
	 * 所有名单列表初始化
	 */
	@SuppressWarnings("unchecked")
	private void tableViewInitialize() {

		personNum.setCellValueFactory(new PropertyValueFactory<Person, String>("personNum"));
		personNum.setPrefWidth(130);
		name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		name.setPrefWidth(90);
		phone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
		phone.setPrefWidth(150);
		totalPeopleView.getColumns().clear();
		totalPeopleView.setEditable(true);

		phone.setEditable(true);

		Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactoryNum = new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
			public TableCell<Person, String> call(TableColumn<Person, String> p) {
				return new infoCellEditingView();
			}
		};
		phone.setCellFactory(cellFactoryNum);
		phone.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(CellEditEvent<Person, String> phone) {
				((Person) phone.getTableView().getItems().get(phone.getTablePosition().getRow()))
						.setPhone(phone.getNewValue());
			}
		});

		totalPeopleView.getColumns().addAll(personNum, name, phone);
	}

	/**
	 * 退出确认
	 */
	public void quitConfirm() {
		if(!saveFlag){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("确认");
			alert.setHeaderText("退出前是否保存文件？");
			alert.setContentText("是否需要保存文件 ?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				saveFile(null);
			} else {
			    // ... user chose CANCEL or closed the dialog
			}
		}
	}

}

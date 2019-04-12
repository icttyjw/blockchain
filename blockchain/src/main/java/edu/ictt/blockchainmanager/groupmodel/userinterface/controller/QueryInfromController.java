package edu.ictt.blockchainmanager.groupmodel.userinterface.controller;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchain.Block.db.query.service.FindBlockService;
//import edu.ictt.blockchain.Block.block.Block;
//import edu.ictt.blockchain.Block.db.DbStore;
//import edu.ictt.blockchain.Block.db.query.service.BlockInfoService;
//import edu.ictt.blockchain.Block.db.query.simple.ManageBlockMessage;
//import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchainmanager.groupmodel.BlockInfo;
import edu.ictt.blockchainmanager.groupmodel.BlockProperty;
import edu.ictt.blockchainmanager.sql.service.BlockInfoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import edu.ictt.blockchainmanager.sql.service.FindBlockService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javassist.expr.NewArray;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.validation.constraints.Null;

import org.hibernate.usertype.LoggableUserType;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@FXMLController
@Component
public class QueryInfromController {
	
	//跨包问题这个暂时放弃
	//@Resource 
	//DbBlockManager dbBlockManager;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	BlockInfoService blockInfoService;
	
	//@Resource
	//FindBlockService findBlockService;
	
	@FXML
	private TextField schoolName;
	
	@FXML
	private TextField facultyName;
	
	@FXML
	private TextField courseName;
	
	@FXML
	private TextField simpleResult;
	
	@FXML
	private Button queryButton;
	
	//@FXML
	private TableView<BlockProperty> recordTable = new TableView<>();
	
	//暂时存成字符串
	//@FXML
	//private TableView<String> recordTable;
	
	//private TableColumn<BlockProperty, String> schoolNameColumn;
	
	//private ObservableList<BlockProperty> stringResult = FXCollections.observableArrayList(new BlockProperty());
	
	//private ObservableList<TableColumn<BlockProperty, ?>> observableList = recordTable.getColumns();
	
	//RocksDB rocksDB;
	
	@FXML
    void initialize() throws RocksDBException {
		recordTable.setVisible(false);
		//连接到rocksdb
    	//RocksDB.loadLibrary();
    	//Options options = new Options().setCreateIfMissing(true);
    	//rocksDB = RocksDB.open(options, "./rocksDB");
    }
    
    @FXML
    void queryInDB(ActionEvent event) throws RocksDBException {
    	
    	recordTable.setVisible(true);
    	//schoolNameColumn.setCellValueFactory(new PropertyValueFactory<>("schoolName"));
    	
    	//rocksDB.put("1".getBytes(),"TEST".getBytes() );
    	//String queryKey = queryField.getText();
    	//String queryResult =  rocksDB.get(queryKey.getBytes()).toString();
    	//先看看能不能显示
    	//queryField.setText(queryResult);
    	
    	//用非springboot的mysql
    	//String sName = schoolName.getText();
    	//ManageBlockMessage manageBlockMessage = new ManageBlockMessage();
    	//schoolName.setText(manageBlockMessage.queryBySchoolName(sName).toString());
    	
    	//springboot注解形式的mysql
    	anayField();
    	
    	//单学校查询
    	//String sName = schoolName.getText();
    	//System.out.println("您输入的是：" + sName);
    	//List<BlockInfo> blockInfos= blockInfoService.queryBySchoolName(sName);
    	//System.out.println("查询结果：" + blockInfos);
    	//BlockInfo result = blockInfos.get(0);
    	//simpleResult.setText(result.toString());
    	
    	
    
    }
    
    void anayField() throws RocksDBException {
    	
    	String sName = schoolName.getText();
    	String fName = facultyName.getText();
    	String cName = courseName.getText();
    	logger.info("您输入的是" + sName + fName + cName);
    	//logger.info("您输入的是" + sName + fName);
    	//logger.info("您输入的是" + sName);
    	
    	
    	//改进……如果找不到应该给提示
    	if(sName != null) {
    		if(fName != null) {
    			if(cName != null) {
    				//Block block = findBlockService.queryBySchoolNameAndFacultyNameAndCourseName(sName, fName, cName);
    				//simpleResult.setText(block.toString());
    				simpleResult.setText(blockInfoService.queryBySchoolNameAndFacultyNameAndCourseName(sName, fName, cName).toString());
    				//BlockInfo blockInfo = blockInfoService.queryBySchoolNameAndFacultyNameAndCourseName(sName, fName, cName);
    				//暂时把取得的区块存做字符串，后续整个串起来后解析。
    				//String result = rocksDB.get(blockInfo.getBlockHash().getBytes()).toString();
    				//logger.info("表中所有的列："+stringResult);
    				//stringResult.get(0).setSchoolName(blockInfo.toString());
    				//recordTable.setItems(stringResult);
    				//recordTable.getColumns().add((TableColumn<BlockProperty, ?>) stringResult);
    			
    			} 
    			//List<Block> blocks = findBlockService.queryFirst10BySchoolNameAndFacultyName(sName, fName);
    			//simpleResult.setText(blocks.toString());	
    			//simpleResult.setText(blockInfoService.queryFirst10BySchoolNameAndFacultyName(sName, fName).toString());
    		}
    		//List<Block> blocks = findBlockService.queryFirst10BySchoolName(sName);
    		//simpleResult.setText(blocks.toString());
    		//simpleResult.setText(blockInfoService.queryFirst10BySchoolName(sName).toString());
    	}else {
    		//改进……想弹出一个提示框提示学校名称不能为空
    		//暂时显示在text
    		//simpleResult.setText("学校名称不能为空");
    	}
  
    }
}

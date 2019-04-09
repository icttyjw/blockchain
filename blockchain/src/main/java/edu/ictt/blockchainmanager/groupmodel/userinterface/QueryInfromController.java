package edu.ictt.blockchainmanager.groupmodel.userinterface;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.db.DbStore;
import edu.ictt.blockchain.Block.db.query.service.BlockInfoService;
import edu.ictt.blockchain.Block.db.query.simple.ManageBlockMessage;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchainmanager.sql.service.FindBlockService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.validation.constraints.Null;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.stereotype.Component;


@FXMLController
@Component
public class QueryInfromController {
	
	//跨包问题这个暂时放弃
	//@Resource 
	//DbBlockManager dbBlockManager;
	//@Resource
	BlockInfoService blockInfoService;
	
	//@Resource
	FindBlockService findBlockService;
	
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
	
	@FXML
	private TableView recordTable;
	
	
	@FXML
    void initialize() {

    }
    
    @FXML
    void queryInDB(ActionEvent event) throws RocksDBException {
    	
    	recordTable.setVisible(true);
    	
    	//连接到rocksdb
    	RocksDB.loadLibrary();
    	Options options = new Options().setCreateIfMissing(true);
    	RocksDB rocksDB = RocksDB.open(options, "./rocksDB");
    	
    	//用非springboot的mysql
    	//String sName = schoolName.getText();
    	//ManageBlockMessage manageBlockMessage = new ManageBlockMessage();
    	//schoolName.setText(manageBlockMessage.queryBySchoolName(sName).toString());
    	
    	//springboot注解形式的mysql
    	anayField();
    	//rocksDB.put("1".getBytes(),"TEST".getBytes() );
    	//String queryKey = queryField.getText();
    	//String queryResult =  rocksDB.get(queryKey.getBytes()).toString();
    	//先看看能不能显示
    	//queryField.setText(queryResult);
    
    }
    
    void anayField() {
    	
    	String sName = schoolName.getText();
    	String fName = facultyName.getText();
    	String cName = courseName.getText();
    	
    	//改进……如果找不到应该给提示
    	if(sName != null) {
    		if(fName != null) {
    			if(cName != null) {
    				Block block = findBlockService.queryBySchoolNameAndFacultyNameAndCourseName(sName, fName, cName);
    				simpleResult.setText(block.toString());
    			} 
    			List<Block> blocks = findBlockService.queryFirst10BySchoolNameAndFacultyName(sName, fName);
    			simpleResult.setText(blocks.toString());	
    		}
    		List<Block> blocks = findBlockService.queryFirst10BySchoolName(sName);
    		simpleResult.setText(blocks.toString());
    	}else {
    		//改进……想弹出一个提示框提示学校名称不能为空
    		//暂时显示在text
    		schoolName.setText("学校名称不能为空");
    	}
  
    }
}

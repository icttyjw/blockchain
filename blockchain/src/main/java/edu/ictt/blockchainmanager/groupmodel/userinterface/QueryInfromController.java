package edu.ictt.blockchainmanager.groupmodel.userinterface;

import de.felixroske.jfxsupport.FXMLController;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.db.DbStore;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.stereotype.Component;


@FXMLController
@Component
public class QueryInfromController {
	
	//@Resource 跨包问题这个暂时放弃
	//DbBlockManager dbBlockManager;
	
	
	@FXML
	private TextField queryField;
	
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
    	
    	RocksDB.loadLibrary();
    	Options options = new Options().setCreateIfMissing(true);
    	RocksDB rocksDB = RocksDB.open(options, "./rocksDBWithUI");
    	
    	rocksDB.put("1".getBytes(),"TEST".getBytes() );
    	
    	String queryKey = queryField.getText();
    	String queryResult =  rocksDB.get(queryKey.getBytes()).toString();
    	//先看看能不能显示
    	queryField.setText(queryResult);
    	

    }
}

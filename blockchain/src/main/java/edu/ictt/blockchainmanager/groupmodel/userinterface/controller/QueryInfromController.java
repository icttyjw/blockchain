package edu.ictt.blockchainmanager.groupmodel.userinterface.controller;

import de.felixroske.jfxsupport.FXMLController;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.record.DegreeRecord;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.Block.record.TeacherInfo;
import edu.ictt.blockchain.core.manager.DbBlockManager;
//import edu.ictt.blockchain.Block.block.Block;
//import edu.ictt.blockchain.Block.db.DbStore;
//import edu.ictt.blockchain.Block.db.query.service.BlockInfoService;
//import edu.ictt.blockchain.Block.db.query.simple.ManageBlockMessage;
//import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchainmanager.groupmodel.BlockInfo;
import edu.ictt.blockchainmanager.groupmodel.BlockProperty;
import edu.ictt.blockchainmanager.sql.service.BlockInfoService;
import edu.ictt.blockchainmanager.sql.service.FindBlockService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javassist.expr.NewArray;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

import com.google.common.base.FinalizableReferenceQueue;


@FXMLController
@Component
public class QueryInfromController {
	
	//跨包问题这个暂时放弃
	@Resource 
	DbBlockManager dbBlockManager;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	BlockInfoService blockInfoService;
	
	@Resource
	FindBlockService findBlockService;
	
	@FXML
	private TextField schoolName;
	
	@FXML
	private TextField facultyName;
	
	@FXML
	private TextField courseName;
	
	//@FXML
	//private TextArea simpleResult;
	
	@FXML
	private Button queryButton;
	
	@FXML
	private TableView<BlockProperty> recordTable;
	
	@FXML
	private TableColumn<BlockProperty, String> schoolNameColumn;
	
	@FXML
	private TableColumn<BlockProperty, String> facultyNameColumn;
	
	@FXML
	private TableColumn<BlockProperty, String> courseNameColumn;
	
	@FXML 
	private TableColumn<BlockProperty, String> teacherNameColumn;
	
	@FXML
	private TableColumn<BlockProperty, String> studentColumn;
	
	@FXML
	private TableColumn<BlockProperty, Double> gradeColumn;
	
	@FXML
	private TableColumn<BlockProperty, Boolean> teacherCheckColumn;
	
	@FXML
	private TableColumn<BlockProperty, Boolean> facultyCheckColumn;
	
	@FXML
	private Pagination pagination;
	
	private ObservableList<BlockProperty> recordData = FXCollections.observableArrayList();
	
	
	//private ObservableList<TableColumn<BlockProperty, ?>> ObservableList = recordTable.getColumns();
	
	//RocksDB rocksDB;
	
	@FXML
    void initialize() throws RocksDBException {
		//此处返回recordTable为null
		/*ObservableList.get(0).setCellValueFactory(new PropertyValueFactory<>("schoolName"));
		ObservableList.get(1).setCellValueFactory(new PropertyValueFactory<>("facultyName"));
		ObservableList.get(2).setCellValueFactory(new PropertyValueFactory<>("courseName"));
		ObservableList.get(3).setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		ObservableList.get(4).setCellValueFactory(new PropertyValueFactory<>("student"));
		ObservableList.get(5).setCellValueFactory(new PropertyValueFactory<>("grade"));
		ObservableList.get(6).setCellValueFactory(new PropertyValueFactory<>("teacherCheck"));
		ObservableList.get(7).setCellValueFactory(new PropertyValueFactory<>("facultyCheck"));*/
	//数据和表格列关联
		schoolNameColumn.setCellValueFactory(new PropertyValueFactory<BlockProperty,String>("schoolName"));
		facultyNameColumn.setCellValueFactory(new PropertyValueFactory<BlockProperty,String>("facultyName"));
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<BlockProperty,String>("courseName"));
		teacherNameColumn.setCellValueFactory(new PropertyValueFactory<BlockProperty,String>("teacherName"));
		studentColumn.setCellValueFactory(new PropertyValueFactory<BlockProperty,String>("student"));
		gradeColumn.setCellValueFactory(new PropertyValueFactory<BlockProperty,Double>("grade"));
		teacherCheckColumn.setCellValueFactory(new PropertyValueFactory<BlockProperty,Boolean>("teacherCheck"));
		facultyCheckColumn.setCellValueFactory(new PropertyValueFactory<BlockProperty,Boolean>("facultyCheck"));
		//recordTable.setVisible(false);
		//recordTable.getColumns().addAll(schoolNameColumn,facultyNameColumn,courseNameColumn,
				//teacherNameColumn,studentColumn,gradeColumn,teacherCheckColumn,facultyCheckColumn);
		for(int i=0; i<20;i++) {
			recordData.add(new BlockProperty("西电"));
		}
		logger.info("共有" + recordData.size() + "个记录");
		//recordTable.setItems(recordData);
		
		pagination.setPageFactory(pageIndex -> {
            recordTable.setItems(
           		FXCollections.observableList(getCurrentPageDataList(pageIndex)));
           		return null;});
		//recordTable.setItems(recordData);
		//schoolNameColumn.setCellValueFactory(new PropertyValueFactory<>("西电"));
		//logger.info("当前的observablelist内容：" + recordData);
		//schoolNameColumn.setCellValueFactory(CellData -> CellData.getValue().schoolNameProperty());
		
		//将Pagination的pageCountProperty和Page对象的page.totalPageProperty属性进行双向绑定，这样他们的值就会同步：其中一个改变，另一个也会改变，并且值保持一样。
		//pagination.pageCountProperty().bindBidirectional(page.totalPageProperty());
		//连接到rocksdb
    	//RocksDB.loadLibrary();
    	//Options options = new Options().setCreateIfMissing(true);
    	//rocksDB = RocksDB.open(options, "./rocksDB");
    }
    
    @FXML
    void queryInDB(ActionEvent event) throws RocksDBException, FileNotFoundException {
    	
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
    	//simpleResult.setWrapText(true);
    	anayField();
    	
    	//单学校查询
    	//String sName = schoolName.getText();
    	//System.out.println("您输入的是：" + sName);
    	//List<BlockInfo> blockInfos= blockInfoService.queryBySchoolName(sName);
    	//System.out.println("查询结果：" + blockInfos);
    	//BlockInfo result = blockInfos.get(0);
    	//simpleResult.setText(result.toString());
    	
    	
    
    }
    
    void anayField() throws RocksDBException, FileNotFoundException {
    	
    	String sName = schoolName.getText();
    	String fName = facultyName.getText();
    	String cName = courseName.getText();
    	logger.info("[查询]：您输入的是" + sName + fName + cName);
    	//logger.info("您输入的是" + sName + fName);
    	//logger.info("您输入的是" + sName);
    	
    	
    	
    	//改进……如果找不到应该给提示
    	if(sName != null) {
    		if(fName != null) {
    			if(cName != null) {
    				BlockInfo blockInfo = blockInfoService.queryBySchoolNameAndFacultyNameAndCourseName(sName, fName, cName);
    				//logger.info("[查询]：找到的blockinfo" + blockInfo);
    				Block block = findBlockService.findBlockByBI(blockInfo);
    				//将区块解析为需要显示的记录格式
    				getRecordFromBlock(block);
    				
    				//创建新页
    				//Page<BlockProperty> page = new Page<>(recordData, 10);
    				//pagination.pageCountProperty().bindBidirectional(page.totalPageProperty());
    				pagination.setPageFactory(pageIndex -> {
    		            recordTable.setItems(
    		            		FXCollections.observableList(getCurrentPageDataList(pageIndex)));
    		            		return recordTable;});
    				//recordTable.setItems(recordData);
    				//updatePagination();
    				//logger.info("[查询]：找到的block" + block);
    				//simpleResult.setText(block.getBlockBody().toString());
    				/*try {
						FileOutputStream fileOutputStream = new FileOutputStream("./block");
						fileOutputStream.write(block.toString().getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
    				//simpleResult.setText(blockInfoService.queryBySchoolNameAndFacultyNameAndCourseName(sName, fName, cName).toString());
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
  
    
    List<BlockProperty> getCurrentPageDataList(Integer pageIndex) {
    	int fromIndex = 10 * pageIndex;
        int tmp = 10 * pageIndex + 10 - 1;
        int endIndex = tmp >= recordData.size() ? recordData.size() - 1 : tmp;

        // subList(fromIndex, toIndex) -> [fromIndex, toIndex)
        return recordData.subList(fromIndex, endIndex + 1);
    	
    }
   
    void getRecordFromBlock(Block block) {
    	
    	if(block.getBlockBody().getGrecordsList()!=null) {
    		List<GradeRecord> records =  block.getBlockBody().getGrecordsList();
    		for(GradeRecord record: records) {
    			logger.info("当前记录为：" +record);
        		BlockProperty blockProperty = new BlockProperty();
        		blockProperty.setSchoolName(record.getSchoolInfo().getSchoolName());
        		blockProperty.setFacultyName(record.getFacultyInfo().getFacultyName());
        		blockProperty.setCourseName(record.getGradeInfo().getCourseInfo().getCourseName());
        		TeacherInfo[] teacherInfo =record.getGradeInfo().getCourseInfo().getTeacherInfo();
        		blockProperty.setTeacherName(teacherInfo[0].getTeacherName());
        		blockProperty.setStudent(record.getGradeInfo().getStudentInfo().getStudentName());
        		blockProperty.setGrade(record.getGradeInfo().getGrade());
        		if(record.getTeacherSign()!=null) {
        			blockProperty.setTeacherCheck(true);
        			if(record.getFacultySign()!=null) {
        				blockProperty.setFacultyCheck(true);
        			}
        		}
        		recordData.add(blockProperty);
        	}
    	}else if(block.getBlockBody().getDrecordsList() != null) {
    		List<DegreeRecord> records = block.getBlockBody().getDrecordsList();
    	}
    }
    
    /*
     * 根据被点击的页面索引更新页面内容
     */
   /* @FXML
    void updatePagination() {
    	 pagination.setPageFactory(pageIndex -> {
             recordTable.setItems(FXCollections.observableList(page.getCurrentPageDataList(pageIndex)));
             return recordTable;
         });
    	
    }*/
}

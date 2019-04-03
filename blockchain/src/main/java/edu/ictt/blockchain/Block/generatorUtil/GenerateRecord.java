package edu.ictt.blockchain.Block.generatorUtil;

import edu.ictt.blockchain.Block.record.*;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.PairKey;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import static edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm.generatePrivateKey;
import static edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm.generatePublicKey;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 * @Modified By:
 */
public class GenerateRecord {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	//学校公私钥
	static String sPriKey = generatePrivateKey();
	static String sPubKey = generatePublicKey(sPriKey, true);
	
	//学院公私钥
	static String fPriKey = generatePrivateKey();
	static String fPubKey = generatePublicKey(fPriKey,true);
	
	//教师公私钥
	static String tPriKey = generatePrivateKey();
	static String tPubKey = generatePublicKey(tPriKey,true);
	
    public static SchoolInfo geneSchoolInfo(){

        SchoolInfo schoolInfo = new SchoolInfo();
        schoolInfo.setSchoolId(10001);
        schoolInfo.setSchoolName("清华");
        schoolInfo.setSchoolPubKey(sPubKey);

        String scPro = "重点" + "985";
        schoolInfo.setSchoolPro(scPro);

        return schoolInfo;
    }

    public static FacultyInfo geneFaculthInfo(){
    	
    	
        FacultyInfo facultyInfo = new FacultyInfo();

        facultyInfo.setFacultyId(1701);
        facultyInfo.setFacultyName("计算机");
        facultyInfo.setFacultyPro("重点学科");

        facultyInfo.setFacultyPubKey(fPubKey);

        //System.out.println("学院公钥" + pairKey.getPublicKey());
        //System.out.println("学院私钥" + pairKey.getPrivateKey());
        //System.out.println("学院信息：" + facultyInfo);

        return facultyInfo;
    }

    public static CourseInfo geneCurseInfo(){
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourseCredit(3);
        courseInfo.setCourseDate(String.valueOf(System.currentTimeMillis()));
        courseInfo.setCourseId(1);
        courseInfo.setCourseName("数学");
        courseInfo.setCoursePro("双一流");

        return courseInfo;
    }

    public static StudentInfo geneStuInfo(){

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentId(170112111);
        studentInfo.setStudentName("张三");
        studentInfo.setStudentPro("本科");

        return studentInfo;
    }

    public static TeacherInfo geneTeacherInfo(){

        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setTeacherId(0001);
        teacherInfo.setTeacherName("李老师");
        teacherInfo.setTeacherTitle("教授");

        teacherInfo.setTeacherPubKey(tPubKey);

        //System.out.println("教师公钥" + pairKey.getPublicKey());
        //System.out.println("教师私钥" + pairKey.getPrivateKey());

        return teacherInfo;
    }

    public static GradeInfo geneGradeInfo(){

        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setCourseInfo(geneCurseInfo());
        gradeInfo.setGrade(Math.random()*100);
        gradeInfo.setStudentInfo(geneStuInfo());

        TeacherInfo[] teacherInfos = new TeacherInfo[3];
        for(int i=0; i<teacherInfos.length; i++){
            teacherInfos[i] = geneTeacherInfo();
            //System.out.println("教师信息" + teacherInfos[i].getTeacherPubKey());
            //System.out.println("教师信息" + teacherInfos[i].toString());
        }
        gradeInfo.setTeacherInfo(teacherInfos);

        return gradeInfo;
    }

    //成绩记录:int facultyId, String facultyName, String facultyPro,PairKey faculthPairKey
    public static GradeRecord geneGRecord(){

        GradeRecord record = new GradeRecord();

        record.setSchoolInfo(geneSchoolInfo());
        record.setFacultyInfo(geneFaculthInfo());
        record.setGradeInfo(geneGradeInfo());
        record.setRecordTimeStamp(System.currentTimeMillis());//System.currentTimeMillis()


        TeacherInfo[] teacherInfos = record.getGradeInfo().getTeacherInfo();
        
        
        //^^^^^^^^^^^^^^^^^^^^此处有问题，私钥需存到数据库
        //^^^^^^^^^^^^^^^^^^^^暂时自己定义了公私钥对用于前期测试
        //String teaPriKey = teacherInfos[0].getTeacherPubKey();
        //String falPriKey = record.getFacultyInfo().getFacultyPubKey();
        String scinfo=FastJsonUtil.toJSONString(record.getSchoolInfo());
    	String facuinfo=FastJsonUtil.toJSONString(record.getFacultyInfo());
    	String grainfo=FastJsonUtil.toJSONString(record.getGradeInfo());
        try {
            String recordStr = scinfo+facuinfo+grainfo//scinfo+facuinfo+grainfo//record.getSchoolInfo().toString()+record.getFacultyInfo().toString()
            		+record.getRecordTimeStamp();//+record.getGradeInfo().toString()+record.getRecordTimeStamp();
            record.setTeacherSign(ECDSAAlgorithm.sign(tPriKey, recordStr));
            //System.out.println("被签名的记录为：" + recordStr);
            //System.out.println("记录的教师签名为：" + record.getTeacherSign());
            
            recordStr = scinfo+facuinfo+grainfo//scinfo+facuinfo+grainfo//record.getSchoolInfo().toString()+record.getFacultyInfo().toString()
            		+record.getRecordTimeStamp()+ record.getTeacherSign();//+record.getGradeInfo().toString()+record.getRecordTimeStamp()+ record.getTeacherSign();
            //System.out.println(recordStr);
            record.setFacultySign(ECDSAAlgorithm.sign(fPriKey,recordStr));
            //System.out.println("记录的学院签名为：" + record.getFacultySign());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return record;
    }

}

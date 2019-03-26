package edu.ictt.blockchain.Block.generatorUtil;

import edu.ictt.blockchain.Block.record.*;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.PairKey;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 * @Modified By:
 */
public class GenerateRecord {

    public static SchoolInfo geneSchoolInfo(){

        SchoolInfo schoolInfo = new SchoolInfo();
        schoolInfo.setSchoolId(10001);
        schoolInfo.setSchoolName("清华");
        schoolInfo.setSchoolPairKey(new PairKey());

        List<String> scPro = new ArrayList<>();
        scPro.add("重点");
        scPro.add("985");
        schoolInfo.setSchoolPro(scPro);

        return schoolInfo;
    }

    public static FacultyInfo geneFaculthInfo(){
        FacultyInfo facultyInfo = new FacultyInfo();

        facultyInfo.setFacultyId(1701);
        facultyInfo.setFacultyName("计算机");
        facultyInfo.setFacultyPro("重点学科");

        facultyInfo.setFacultyPairKey(new PairKey());

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

        teacherInfo.setTeacherPairKey(new PairKey());

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
            System.out.println("教师信息" + teacherInfos[i].getTeacherPairKey());
            System.out.println("教师信息" + teacherInfos[i].toString());
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
        String teaPriKey = teacherInfos[0].getTeacherPairKey().getPrivateKey();
        String falPriKey = record.getFacultyInfo().getFacultyPairKey().getPrivateKey();
        String scinfo=FastJsonUtil.toJSONString(record.getSchoolInfo());
    	String facuinfo=FastJsonUtil.toJSONString(record.getFacultyInfo());
    	String grainfo=FastJsonUtil.toJSONString(record.getGradeInfo());
        try {
            String recordStr = scinfo+facuinfo+grainfo//scinfo+facuinfo+grainfo//record.getSchoolInfo().toString()+record.getFacultyInfo().toString()
            		+record.getRecordTimeStamp();//+record.getGradeInfo().toString()+record.getRecordTimeStamp();
            record.setTeacherSign(ECDSAAlgorithm.sign(teaPriKey, recordStr));
            System.out.println("被签名的记录为：" + recordStr);
            System.out.println("记录的教师签名为：" + record.getTeacherSign());
            
            recordStr = scinfo+facuinfo+grainfo//scinfo+facuinfo+grainfo//record.getSchoolInfo().toString()+record.getFacultyInfo().toString()
            		+record.getRecordTimeStamp()+ record.getTeacherSign();//+record.getGradeInfo().toString()+record.getRecordTimeStamp()+ record.getTeacherSign();
            System.out.println(recordStr);
            record.setFalSign(ECDSAAlgorithm.sign(falPriKey,recordStr));
            System.out.println("记录的学院签名为：" + record.getFalSign());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return record;
    }

}

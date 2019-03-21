package edu.ictt.blockchain.Block.generatorUtil;

import edu.ictt.blockchain.Block.record.*;
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

        PairKey pairKey = new PairKey();
        pairKey.setPublicKey(ECDSAAlgorithm.generatePublicKey("1"));
        pairKey.setPrivateKey(ECDSAAlgorithm.generatePrivateKey());
        schoolInfo.setSchoolPairKey(pairKey);

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

        PairKey pairKey = new PairKey();
        pairKey.setPublicKey(ECDSAAlgorithm.generatePublicKey("2"));
        pairKey.setPrivateKey(ECDSAAlgorithm.generatePrivateKey());
        facultyInfo.setFaculthPairKey(pairKey);

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

        PairKey pairKey = new PairKey();
        pairKey.setPublicKey(ECDSAAlgorithm.generatePublicKey("3"));
        pairKey.setPrivateKey(ECDSAAlgorithm.generatePrivateKey());
        teacherInfo.setteacherPairKey(pairKey);

        return teacherInfo;
    }

    public static GradeInfo geneGradaInfo(){

        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setCourseInfo(geneCurseInfo());
        gradeInfo.setGrade(Math.random()*100);
        gradeInfo.setStudentInfo(geneStuInfo());

        TeacherInfo[] teacherInfos = new TeacherInfo[3];
        for(int i=0; i<teacherInfos.length; i++){
            teacherInfos[i] = geneTeacherInfo();
        }
         gradeInfo.setTeacherInfo(teacherInfos);

        return gradeInfo;
    }


    //成绩记录:int facultyId, String facultyName, String facultyPro,PairKey faculthPairKey
    public static GradeRecord geneGRecord(){

        GradeRecord record = new GradeRecord();

        record.setSchoolInfo(geneSchoolInfo());
        record.setFacultyInfo(geneFaculthInfo());
        record.setGradeInfo(geneGradaInfo());
        record.setRecordTimeStamp(System.currentTimeMillis());


        TeacherInfo[] teacherInfos = record.getGradeInfo().getTeacherInfo();
        String teaPriKey = teacherInfos[0].getteacherPairKey().getPrivateKey();
        String schPriKey = record.getSchoolInfo().getSchoolPairKey().getPrivateKey();
        try {
            record.setTeacherSign(ECDSAAlgorithm.sign(teaPriKey,record.toString()));
            record.setSchoolSign(ECDSAAlgorithm.sign(schPriKey,record.toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return record;
    }

}

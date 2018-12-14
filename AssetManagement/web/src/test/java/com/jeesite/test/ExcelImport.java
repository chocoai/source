package com.jeesite.test;

import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.service.AchCardService;
import com.jeesite.modules.achievement.usertarget.entity.AchUserTarget;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.config.Application;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback(false)
@EnableAutoConfiguration
public class ExcelImport {
    @Autowired
    public DingUserService dingUserService;
    @Autowired
    private DingDepartmentService dingDepartmentService;
    @Autowired
    private AmSeqService amSeqService;
    @Autowired
    private AchCardService achCardService;
    @Test
    public void importData() throws Exception {
        File file = new File("D:\\2018-12");
        if(file.exists()){
            File[] child = file.listFiles();
            for(File f:child){
                //String fileName = f.getName();
                //String[] fileNameSplit = fileName.substring(0,fileName.lastIndexOf(".")).split("-");
                //String userid = fileNameSplit[3];
                //String username = fileNameSplit[4];
                readExcel(f);
            }

        }
    }

    public void readExcel(File file)throws Exception{

        InputStream in = new FileInputStream(file);
        //XSSFWorkbook wb = new XSSFWorkbook(in);

        POIFSFileSystem fs = new POIFSFileSystem(in);
        EncryptionInfo info = new EncryptionInfo(fs);
        Decryptor d = Decryptor.getInstance(info);
        d.verifyPassword("666");
        in = d.getDataStream(fs);
        XSSFWorkbook wb = new XSSFWorkbook(in);
        Sheet sheet = wb.getSheetAt(1);//绩效卡
        Row rows = sheet.getRow(4);
        Cell cell = ((Row) rows).getCell(2);//所属部门
        String firstDeptName = cell.getStringCellValue();
        cell = ((Row) rows).getCell(4);//被考核人
        String user = cell.getStringCellValue();
        cell = ((Row) rows).getCell(6);//是否有下属
        String hadFollower = cell.getStringCellValue();
        rows = sheet.getRow(5);
        cell = ((Row) rows).getCell(2);//考核人
        String assessorCode=cell.getStringCellValue().split("-")[0];

        cell = ((Row) rows).getCell(4);//考核权重
        Double examineWeight=cell.getNumericCellValue();

        AchCard achCard = new AchCard();
        achCard.setExamineMonth("201812");//考核月份
        String staffid = user.split("-")[0];

        DingUser dingUser = new DingUser();
        dingUser.setJobnumber(staffid);
        List<DingUser> dingUserlist = dingUserService.findList(dingUser);

        DingUser user1 = dingUserlist.get(0);
        String userid = user1.getUserid();
        achCard.setExaminedStaffCode(userid);//被考核人
        List<DingDepartment> dingDepartmentByUser = dingUserService.getDingDepartmentByUser(userid);
        DingDepartment dept = dingDepartmentByUser.get(0);
        achCard.setDepartCode(dept.getDepartmentId());//所属部门编码
        achCard.setDepartName(dept.getName());//所属部门名称

        DingDepartment param = new DingDepartment();
        param.setName(firstDeptName);
        List<DingDepartment> deptlist = dingDepartmentService.findList(param);
        DingDepartment firstDept = deptlist.get(0);
        achCard.setFirstDepartCode(firstDept.getDepartmentId());//一级部门编码
        achCard.setFirstDepartName(firstDept.getName());//一级部门名称
        achCard.setPostCode(user1.getPosition());//岗位编码
        achCard.setHadFollower("有下属".equals(hadFollower)?"1":"0");//是否有下属

        dingUser.setJobnumber(assessorCode);
        dingUserlist = dingUserService.findList(dingUser);

        achCard.setAssessorCode(dingUserlist.get(0).getUserid());//考核人
        achCard.setExamineWeight(examineWeight);//考核权重



        String cardCode = amSeqService.getCode("JXK", "yyyyMM", 4);
        achCard.setCardCode(cardCode);
        achCard.setCreateDate(new Date());
        achCard.setCreateBy("11111111");

        achCardService.insert(achCard);

        AchUserTarget achUserTarget = new AchUserTarget();

        sheet = wb.getSheetAt(2);//2.1KPI-指标
        rows = sheet.getRow(5);
        cell = rows.getCell(2);//指标名称
        String targetName=cell.getStringCellValue();
        achUserTarget.setTargetName(targetName);
        cell = rows.getCell(3);//指标类型
        String targetType = cell.getStringCellValue();
        achUserTarget.setTargetType(targetType.equals("关键指标")?"0":"1");
        cell = rows.getCell(4);//标准分数
        String standardScore =  cell.getStringCellValue();
        achUserTarget.setStandardScore(Float.parseFloat(standardScore));
        cell = rows.getCell(5);//指标内容描述
        achUserTarget.setDescription(cell.getStringCellValue());
        cell = rows.getCell(6);//统计公式
        achUserTarget.setFormula(cell.getStringCellValue());
        cell = rows.getCell(7);//得分系数范围
        //
        achUserTarget.setCoefficientRange(cell.getStringCellValue());
        cell = rows.getCell(1);//←基础下行%（越多越好的指标用正数表示，反之，用负数表示）
        cell = rows.getCell(1);//→基础上行%（越多越好的指标用正数表示，反之，用负数表示）

        achUserTarget.setCardCode(cardCode);

        sheet = wb.getSheetAt(3);//2.2KPI-任务




    }
}

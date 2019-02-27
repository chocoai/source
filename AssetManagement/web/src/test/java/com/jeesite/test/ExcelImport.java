package com.jeesite.test;

import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.service.AchCardService;
import com.jeesite.modules.achievement.usermission.entity.AchCardMission;
import com.jeesite.modules.achievement.usermission.service.AchCardMissionService;
import com.jeesite.modules.achievement.usertarget.entity.AchUserTarget;
import com.jeesite.modules.achievement.usertarget.service.AchUserTargetService;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingDepartmentService;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.config.Application;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    @Autowired
    private AchUserTargetService achUserTargetService;
    @Autowired
    private AchCardMissionService achCardMissionService;

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





        int rowsNumSheet2 = 5;
        sheet = wb.getSheetAt(2);//2.1KPI-指标
        while (sheet.getRow(rowsNumSheet2).getCell(2)!=null && !sheet.getRow(rowsNumSheet2).getCell(2).getStringCellValue().equals("")) {
        AchUserTarget achUserTarget = new AchUserTarget();
        achUserTarget.setCardCode(cardCode);
        achUserTarget.setUserId(userid);
            rows = sheet.getRow(rowsNumSheet2);
            cell = rows.getCell(2);//指标名称
            String targetName = cell.getStringCellValue();
            System.out.println(targetName);
            achUserTarget.setTargetName(targetName);
            cell = rows.getCell(3);//指标类型
            String targetType = cell.getStringCellValue();
            achUserTarget.setTargetType(targetType.equals("关键指标") ? "0" : "1");
            cell = rows.getCell(4);//标准分数
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String standardScore = cell.getStringCellValue();
            achUserTarget.setStandardScore(Float.parseFloat(standardScore));
            cell = rows.getCell(5);//指标内容描述
            achUserTarget.setDescription(cell.getStringCellValue());
            cell = rows.getCell(6);//统计公式
            achUserTarget.setFormula(cell.getStringCellValue());
            cell = rows.getCell(7);//得分系数范围
            String coefficientRange = cell.getStringCellValue();
            if (coefficientRange != null && !coefficientRange.equals(""))
                if (coefficientRange.equals("[0-2]")) achUserTarget.setCoefficientRange("0");
                else if (coefficientRange.equals("[1-0.5]")) achUserTarget.setCoefficientRange("1");
                else achUserTarget.setCoefficientRange("2");
            else achUserTarget.setCoefficientRange(" ");
            cell = rows.getCell(9);//←基础下行%（越多越好的指标用正数表示，反之，用负数表示）
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals(""))achUserTarget.setBasicsDown(Float.parseFloat(cell.getStringCellValue().replace("%", "")));
            cell = rows.getCell(10);//→基础目标
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals(""))achUserTarget.setBasicAims(Float.parseFloat(cell.getStringCellValue().replace("%", "")));
            cell = rows.getCell(11);//→基础上行%（越多越好的指标用正数表示，反之，用负数表示）
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals(""))achUserTarget.setBasicsUpstream(Float.parseFloat(cell.getStringCellValue().replace("%", "")));

            achUserTargetService.insert(achUserTarget);
            rowsNumSheet2++;
        }


        sheet = wb.getSheetAt(3);//2.2KPI-任务
        int rowsNumSheet3 = 6;
        while (sheet.getRow(rowsNumSheet3).getCell(2)!=null && !sheet.getRow(rowsNumSheet3).getCell(2).getStringCellValue().equals("")) {
        AchCardMission achCardMission = new AchCardMission();
        achCardMission.setCardCode(cardCode);
        achCardMission.setUserId(userid);
            rows = sheet.getRow(rowsNumSheet3);
            cell = rows.getCell(2);//任务名称
            achCardMission.setMissionName(cell.getStringCellValue());
            cell = rows.getCell(3);//任务类型
            achCardMission.setMissionType(cell.getStringCellValue());
            cell = rows.getCell(4);//标准得分
            cell.setCellType(Cell.CELL_TYPE_STRING);
            achCardMission.setStandardScore(Double.parseDouble(cell.getStringCellValue()));
            cell = rows.getCell(5);//目标Objective
            achCardMission.setGoal(cell.getStringCellValue());
            cell = rows.getCell(6);//任务内容描述
            achCardMission.setDescription(cell.getStringCellValue());
            cell = rows.getCell(7);//考核标准
            achCardMission.setExamineStandard(cell.getStringCellValue());

            achCardMissionService.insert(achCardMission);
            rowsNumSheet3++;
        }

    }




}

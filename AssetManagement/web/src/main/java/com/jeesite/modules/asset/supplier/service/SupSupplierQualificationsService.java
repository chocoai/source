package com.jeesite.modules.asset.supplier.service;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.supplier.dao.SupSupplierDao;
import com.jeesite.modules.asset.supplier.dao.SupSupplierQualificationsDao;
import com.jeesite.modules.asset.supplier.entity.SupSupplier;
import com.jeesite.modules.asset.supplier.entity.SupSupplierQualifications;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;


@Service
@Transactional(readOnly=true)
public class SupSupplierQualificationsService extends CrudService<SupSupplierQualificationsDao,SupSupplierQualifications> {
    @Autowired
    private SupSupplierQualificationsDao dao;
    public List<SupSupplierQualifications> findBySupSupplierCode(String supplierCode){
        return dao.findBySupSupplierCode(supplierCode);
    }
    @Transactional(readOnly=false)
    public void deleteByQualificationCode(String qualifyCode){
       dao.deleteByQualificationCode(qualifyCode);
    }
    public SupSupplierQualifications findByQualificationCode(String qualifyCode){
        return dao.findByQualificationCode(qualifyCode);
    }
    @Transactional(readOnly=false)
    public void updateDateInfo(Date effectiveDate,Date expireDate,String qualifyCode,String isNeverExpired){
        dao.updateDateInfo(effectiveDate,expireDate,qualifyCode,isNeverExpired);
    }

    /**
     * 保存资质文件信息
     * @param profileInfo
     * @return
     */
    @Transactional(readOnly=false)
    public boolean saveProfiles(@RequestBody String profileInfo){
        boolean flag=true;
        JSONArray jsonArray=JSONArray.fromObject(profileInfo);
        JSONObject jsonObject=null;
        if(null!=jsonArray &&jsonArray.size()>0){
            for(int i=0;i<jsonArray.size();i++){
                jsonObject=jsonArray.getJSONObject(i);
                SupSupplierQualifications qualification=new SupSupplierQualifications();
                Date effectiveDate=DateUtils.parseDate(jsonObject.getString("effectiveDate"));
                String expireDate=jsonObject.getString("expireDate");
                if(null==expireDate ||"".equals(expireDate) ||" 00:00:00".equals(expireDate)){
                    Date expireDate1=DateUtils.parseDate("9999-12-12 00:00:00");
                    qualification.setExpireDate(expireDate1);
                    qualification.setIsNeverExpired("是");
                }else{
                    qualification.setExpireDate(DateUtils.parseDate(expireDate));
                    qualification.setIsNeverExpired("否");
                }
                qualification.setEffectiveDate(effectiveDate);
                qualification.setQualificationName(jsonObject.getString("qualificationName"));
                qualification.setTypeName(jsonObject.getString("typeName"));
                qualification.setSavePath(jsonObject.getString("savePath"));
                qualification.setSupplierCode(jsonObject.getString("supplierCode"));
                qualification.setProfileSurfix(jsonObject.getString("profileSurfix"));
                qualification.setIsNewRecord(true);
                qualification.preInsert();
                this.insert(qualification);
            }
        }
        return flag;
    }

}

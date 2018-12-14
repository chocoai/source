package com.jeesite.modules.asset.tianmao.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.tianmao.dao.SkuK3LogDAO;
import com.jeesite.modules.asset.tianmao.dao.TbSkuK3NameDao;
import com.jeesite.modules.asset.tianmao.entity.TbLog;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.entity.TbSkuK3Name;
import com.jeesite.modules.asset.tianmao.service.TbLogService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 线程类 用于查询K3物料，并插入tb_sku
 *
 * @author Jace Xiong
 */
@Service
@Transactional(readOnly=false)
public class K3Thread implements Runnable {
    private String id;
    private String url;
    private TbSku tbSku;
    private final TbSkuK3NameDao tbSkuK3NameDao;
    public TbSku getTbSku() {
        return tbSku;
    }

    public void setTbSku(TbSku tbSku) {
        this.tbSku = tbSku;
    }

    private final TbLogService tbLogService;
    private final SkuK3LogDAO skuK3LogDAO;
    @Autowired
    public K3Thread(TbLogService tbLogService, SkuK3LogDAO skuK3LogDAO, TbSkuK3NameDao tbSkuK3NameDao) {
        this.tbLogService = tbLogService;
        this.skuK3LogDAO = skuK3LogDAO;
        this.tbSkuK3NameDao = tbSkuK3NameDao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        User user = UserUtils.getUser();
        String result = null;
//        String content = "{\"CreateOrgId\":\"0\",\"Number\":\"KH1-06\",\"Id\":\"\"}";
//        String content = "{\"CreateOrgId\":\"0\",\"Number\":\""+tbSku.getOuterId()+"\",\"Id\":\"\"}";
        String content = "{\"FormId\":\""+ id +"\",\"FieldKeys\":\"FNAME\",\"FilterString\":\"FNUMBER='"+ tbSku.getOuterId()+"'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try {
            Thread.sleep(1000);
//            result = InvokeHelper.View(id,content,url);
            result = InvokeHelper.ExecuteBillQuery(id, content, url);
            if (!"[]".equals(result) && !"[[[]]]".equals(result)) {
                result = result.replace("[[\"", "");
                result = result.replace("\"]]", "");
                if (!"".equals(result) && result != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = sdf.format(new Date());
                    Date date = sdf.parse(time);

                    //对比数据库中物料名称
                    List<TbSkuK3Name> tbSkuK3Name = tbSkuK3NameDao.selectByOuterId(tbSku.getOuterId());
                    if (tbSkuK3Name == null || tbSkuK3Name.size() <= 0) {
                        //插入新数据
                        tbSkuK3NameDao.insertK3Name(tbSku.getSkuId(), tbSku.getOuterId(), result, time);
                    }
                    if (tbSkuK3Name != null && tbSkuK3Name.size() >0) {
                        if (!tbSkuK3Name.get(0).getSkuName().equals(result)) {
                            //更新数据操作
                            tbSkuK3NameDao.updateK3Name(tbSku.getSkuId(), result);
                            String describe = "K3物料名称原值【" + tbSkuK3Name.get(0).getSkuName() + "】,新值【" + tbSku.getSkuName() + "】";
                            TbLog tbLog = new TbLog();
                            tbLog.setDescribe(describe);
                            tbLog.setSku(tbSku.getOuterId());
                            tbLog.setSkuId(tbSku.getSkuId().toString());
                            tbLog.setUser(user.getUserName());
                            tbLog.setType("获取物料");
                            tbLog.setLogTime(date);
                            tbLogService.save(tbLog);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package spider.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.mapper.SpiderDataMapper;

import java.util.HashMap;
import java.util.Map;

import static spider.api.tokenUtils.ACCESS_TOKEN;

@Service
public class UnRegister {

    @Autowired
    private SpiderDataMapper spiderDataMapper;

    public  void unregister(Map param) throws Exception {

        Map map = new HashMap();
        map.put("description", param.getOrDefault("description",""));
        map.put("name", param.getOrDefault("name",""));
        map.put("open_option", "public");
        map.put("website", param.getOrDefault("website",""));
        String token = ACCESS_TOKEN;
        if (ACCESS_TOKEN == null){
            tokenUtils t = new tokenUtils();
            t.getToken();
            token = ACCESS_TOKEN;
        }
        spider.utils.httpReq.PostData("http://192.168.12.156:32002/api/dataNet/contribution/url/unregister",map,token);


        Map hashMap = new HashMap();
        hashMap.put("name",param.getOrDefault("name",""));
        hashMap.put("description",param.getOrDefault("description",""));
        hashMap.put("website",param.getOrDefault("website",""));
        hashMap.put("bizType",1);
        hashMap.put("dataType",10);
        spiderDataMapper.saveData(hashMap);
    }


}

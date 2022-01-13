package spider.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.mapper.SpiderDataMapper;
import spider.utils.httpReq;

import java.util.HashMap;
import java.util.Map;

import static spider.api.tokenUtils.ACCESS_TOKEN;

@Service
public class SignUrl {


    @Autowired
    private SpiderDataMapper spiderDataMapper;
    public  JSONObject GetCode(Map param) throws Exception {
        Map map = new HashMap();
        map.put("category", ""); //类别  根据网页信息填写
        map.put("data_type", "data");
        map.put("description", param.getOrDefault("description",""));
        map.put("method", "get");
        map.put("name", param.getOrDefault("name",""));
        map.put("open_option", "public");
        map.put("owner", "qqq");
        map.put("protocol", "http");
        map.put("url", param.getOrDefault("url",""));
        map.put("website", param.getOrDefault("website",""));

        String token = ACCESS_TOKEN;
        if (ACCESS_TOKEN == null){
            tokenUtils t = new tokenUtils();
            t.getToken();
            token = ACCESS_TOKEN;
        }
        JSONObject jsonObject = httpReq.PostData("http://192.168.12.156:32002/api/dataNet/contribution/url",map,token);

        Map hashMap = new HashMap();
        hashMap.put("name",param.getOrDefault("name",""));
        hashMap.put("description",param.getOrDefault("description",""));
        hashMap.put("website",param.getOrDefault("website",""));
        hashMap.put("url",param.getOrDefault("url",""));
        hashMap.put("bizType",2);
        hashMap.put("dataType",2);
        hashMap.put("code",jsonObject.getJSONObject("data").getString("apiCode"));

        spiderDataMapper.saveData(hashMap);
        return jsonObject;
    }



}

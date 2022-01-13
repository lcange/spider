package spider.api;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spider.utils.httpReq;

import java.util.HashMap;
import java.util.Map;


@Service
@EnableScheduling
@Slf4j
public class tokenUtils {

    public  static String ACCESS_TOKEN=null;

@Scheduled(fixedDelay = 3600000)
    public  void getToken() {

        Map map = new HashMap();
        map.put("client_id", "csop");
        map.put("username", "qqq");
        map.put("password", "qqq");
        map.put("grant_type", "password");

        JSONObject jsonObject = httpReq.PostData("http://192.168.12.156:32003/token", map);

        tokenUtils.ACCESS_TOKEN=jsonObject.getJSONObject("data").get("access_token").toString();
        log.info("token:"+ACCESS_TOKEN);
    }

}

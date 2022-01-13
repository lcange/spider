package spider.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import spider.utils.httpReq;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class Detail {

    @Autowired
    private ResultData resultData;

    @Async("spiderExecutor")
    public void detail(JSONArray results, int i) throws Exception {
        Map map = new HashMap();
        log.info("当前线程" + Thread.currentThread().getName());
        int j = 0;
        for (Object result : results) {
            j++;
            log.info("爬取第" + ((i - 1) * 10 + j) + "条");
            JSONObject detailResult = JSON.parseObject(result.toString());
            String title = "";
            if (detailResult.containsKey("title")) {
                title = detailResult.getJSONObject("title").get("en").toString();
            }
            map.put("name", title);
            String description = "";
            if (detailResult.containsKey("description")) {
                description = detailResult.getJSONObject("description").get("en").toString();
                map.put("description", description);
            }

            String dataId = "";
            if (detailResult.containsKey("id")) {
                dataId = detailResult.get("id").toString();
            }

            map.put("dataId", dataId);
            map.put("website", "https://data.europa.eu/data/datasets/" + map.get("dataId"));
            String website = "https://data.europa.eu/api/hub/search/datasets/" + dataId;
            JSONObject fileUrlResp = httpReq.GetData(website);
            JSONArray fileArrays = new JSONArray();
            if (fileUrlResp.containsKey("result")) {
                fileArrays = fileUrlResp.getJSONObject("result").getJSONArray("distributions");
            }


            String format = "";
            String download_url = "";
            for (Object fileArray : fileArrays) {
                JSONObject fileObj = JSON.parseObject(fileArray.toString());
                if (fileObj.containsKey("format")) {
                   if (fileObj.getJSONObject("format").get("id").toString().equals("CSV")){
                       format = "CSV";
                       if (fileObj.containsKey("download_url")) {
                           download_url = fileObj.getJSONArray("download_url").get(0).toString();
                       } else if (fileObj.containsKey("access_url")) {
                           download_url = fileObj.getJSONArray("access_url").get(0).toString();
                       }
                       map.put("url", download_url);
                       map.put("data_type", 1);
                       resultData.resultData(map);
                       break;
                   }
                }
            }
            if (format == ""){
                for (Object fileArray : fileArrays) {
                    JSONObject fileObj = JSON.parseObject(fileArray.toString());
                    if (fileObj.containsKey("format")) {

                        if (fileObj.getJSONObject("format").get("id").toString().equals("JSON")){
                            format = "JSON";
                            if (fileObj.containsKey("download_url")) {
                                download_url = fileObj.getJSONArray("download_url").get(0).toString();
                            } else if (fileObj.containsKey("access_url")) {
                                download_url = fileObj.getJSONArray("access_url").get(0).toString();
                            }
                            map.put("url", download_url);
                            map.put("data_type", 1);
                            resultData.resultData(map);
                            break;
                        }
                    }
                }
            }
            if (format =="") {
                map.put("data_type", 10);
                resultData.resultData(map);
            }
        }
    }
}

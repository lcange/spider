package spider.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import spider.api.Detail;
import spider.api.ResultData;
import spider.utils.httpReq;

@Slf4j
@Component
public class SpiderMain extends Thread implements ApplicationRunner {

    @Autowired
    ResultData resultData;

    @Autowired
    Detail detail;

    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        for (int i = 5000; i <= 50000; i++) {
//                getData(i*10);
            log.info("第" + i + "页");
            JSONArray results = GetResults(i);
//            redisTemplate.opsForValue().set(i,"0");
            try {
                sleep(3000);
//                if (redisTemplate.opsForValue().get(i) == "0"){
//
//                }
                detail.detail(results, i);
            } catch (Exception e) {
                log.info("Exception message:"+e);
            }
        }
        log.info("爬取完成 总数 10000条");
    }

    private JSONArray GetResults(int i) {
        String detailUrl = "https://data.europa.eu/api/hub/search/search?q=&filter=dataset&limit=20&page=" + (i - 1) + "&sort=relevance%2Bdesc,+modified%2Bdesc,+title.en%2Basc&facetOperator=AND&facetGroupOperator=AND&dataServices=false&includes=id,title.en,description.en,languages,modified,issued,catalog.id,catalog.title,catalog.country.id,distributions.format.label,distributions.format.id&facets=%7B%22country%22:[],%22catalog%22:[],%22categories%22:[],%22publisher%22:[],%22keywords%22:[],%22dataServices%22:[],%22scoring%22:[],%22format%22:[],%22license%22:[]%7D";
        JSONObject resp = httpReq.GetData(detailUrl);
        JSONArray results = resp.getJSONObject("result").getJSONArray("results");
        return results;
    }


//    @Async
//    public void sun(JSONArray results, int i) throws Exception {
//        ExecutorService pool = Executors.newCachedThreadPool();
//        Map map = new HashMap();
//        Thread.sleep(1000);
//        log.info("当前线程"+currentThread().getName());
//        int j = 0;
//        for (Object result : results) {
//            j++;
//            log.info("爬取第" + ((i - 1) * 10 + j) + "条");
//            JSONObject detailResult = JSON.parseObject(result.toString());
//            String title = detailResult.getJSONObject("title").get("en").toString();
//            map.put("name", title);
//            String description = "";
//            if (detailResult.containsKey("description")) {
//                description = detailResult.getJSONObject("description").get("en").toString();
//                map.put("description", description);
//            }
//
//            String dataId = "";
//            if (detailResult.containsKey("id")){
//                dataId=detailResult.get("id").toString();
//            }
//
//            map.put("dataId", dataId);
//            map.put("website", "https://data.europa.eu/data/datasets" + map.get("dataId"));
//            String website = "https://data.europa.eu/api/hub/search/datasets/" + dataId;
//            JSONObject fileUrlResp = httpReq.GetData(website);
//            JSONArray fileArrays = fileUrlResp.getJSONObject("result").getJSONArray("distributions");
//
//            String format = "";
//
//            for (Object fileArray : fileArrays) {
//                JSONObject fileObj = JSON.parseObject(fileArray.toString());
//                if (fileObj.containsKey("format")){
//                    format = fileObj.getJSONObject("format").get("id").toString() + format;
//                }
//            }
//            if (format.contains("CSV") || format.contains("JSON")) {
//                for (Object fileArray : fileArrays) {
//                    JSONObject fileObj = JSON.parseObject(fileArray.toString());
//                    if (fileObj.containsKey("format")) {
//                        if (fileObj.getJSONObject("format").get("id").toString().equals("CSV")) {
//                            String download_url = fileObj.getJSONArray("download_url").get(0).toString();
//                            map.put("url", download_url);
//                            map.put("data_type", 1);
//                            resultData.resultData(map);
//                        } else if (fileObj.getJSONObject("format").get("id").toString().equals("JSON")) {
//                            String download_url = fileObj.getJSONArray("download_url").get(0).toString();
//                            map.put("url", download_url);
//                            map.put("data_type", 2);
//                            resultData.resultData(map);
//                        }
//                    }
//                }
//            } else {
//                map.put("data_type",10);
//                resultData.resultData(map);
//            }
//        }
//    }


}



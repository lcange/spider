package spider.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import spider.mapper.SpiderDataMapper;

import java.util.Map;

@Service
@Slf4j
public class ResultData {


    @Autowired
    private SignCsv signCsv;
    @Autowired
    private SignUrl signUrl;
    @Autowired
    private UnRegister unRegister;
    @Autowired
    private SpiderDataMapper spiderDataMapper;




    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 3000L))
    public void resultData(Map map) throws Exception{

            if (map.get("data_type").equals(1)){
                signCsv.registerCsv(map);
            }else if (map.get("data_type").equals(2)){
                signUrl.GetCode(map);
            }else {
                unRegister.unregister(map);
            }
    }
    @Recover
    public void recover(Exception e,Map map){
        map.put("err",e.toString());
        spiderDataMapper.saveErr(map);
        log.info("错误数据"+map);
    }

}

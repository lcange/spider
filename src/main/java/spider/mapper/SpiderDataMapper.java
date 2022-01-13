package spider.mapper;

import spider.base.MyMapper;
import spider.model.spiderData;

import java.util.Map;

public interface SpiderDataMapper extends MyMapper<spiderData> {

    void saveData(Map map);

    void saveErr(Map map);
}
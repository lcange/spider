package spider.utils;

public interface spider {

    //爬取数据
    void catchData();

    //保存数据
    void savaData();

    //注册到中枢
    void register();

    //根据注册结果保存返回信息
    void saveMessage();
}

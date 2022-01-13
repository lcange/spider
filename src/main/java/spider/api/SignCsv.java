package spider.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.mapper.SpiderDataMapper;
import spider.utils.httpReq;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static spider.api.tokenUtils.ACCESS_TOKEN;

@Service
public class SignCsv {
    @Autowired
    private SpiderDataMapper spiderDataMapper;
    //第一步
    private static JSONObject ossPush(String name){
        BufferedReader inin = null;
        JSONObject jsonObject = null;
        String result = "";
        try {
            URL realUrl = new URL("http://inner-datasystem.citybrain.org/api/dataSystem/data/file/push");
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式

            File file = new File("C:\\Users\\SUNXINCAN\\OneDrive\\桌面\\spider\\"+name+".csv");

            if (!file.exists() || !file.isFile()) {
                throw new IOException("文件不存在");
            }
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
            // 请求正文信息
            // 第一部分：
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 输出表头
            out.write(head);
            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 1, bytes);
            }
            in.close();
            // 结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            out.close();


            // 获取请求返回数据（设置返回数据编码为UTF-8）
            inin = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = inin.readLine()) != null) {
                result += line;
            }
            inin.close();
            jsonObject = JSON.parseObject(result);
            return jsonObject.getJSONObject("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    private static JSONObject pushODpsByUrl(String url) throws Exception {
        Map map = new HashMap();
        map.put("url",url);
        String token = ACCESS_TOKEN;
        if (ACCESS_TOKEN == null){
            tokenUtils t = new tokenUtils();
            t.getToken();
            token = ACCESS_TOKEN;
        }
        JSONObject jsonObject = httpReq.PostData("http://inner-datasystem.citybrain.org/api/dataSystem/url/push", map,token);
        return jsonObject.getJSONObject("data");

    }


    //第二步
    private static JSONObject pushODps(JSONObject ossPushData, String name, String description) throws Exception {
        Map map = new HashMap();
        map.put("name",name);
        map.put("description",description);
        map.put("open_state","public");
        if (ossPushData.containsKey("rows")){
            map.put("rows",ossPushData.get("rows"));
        }else {
            map.put("rows","");
        }
        if (ossPushData.containsKey("data_file_id")){
            map.put("data_file_id",ossPushData.get("data_file_id"));
        }else {
            map.put("data_file_id","");
        }
        String token = ACCESS_TOKEN;
        if (ACCESS_TOKEN == null){
            tokenUtils t = new tokenUtils();
            t.getToken();
            token = ACCESS_TOKEN;
        }
        JSONObject jsonObject = httpReq.PostData("http://inner-datasystem.citybrain.org/api/dataSystem/data/push", map,token);
        return  jsonObject.getJSONObject("data");
    }

    //第三步
    private static JSONObject CSVGetCode(JSONObject pushODpsData, String name, String description, String website) throws Exception {
        Map map = new HashMap();
        map.put("category",""); //类别  根据网页信息填写
        map.put("data_type","data");
        map.put("description",description);
        map.put("method","get");
        map.put("name",name);
        map.put("open_option","public");
        map.put("owner","qqq");
        map.put("protocol","http");
        map.put("website",website);
        if (pushODpsData.containsKey("url")){
            map.put("url",pushODpsData.get("url"));
        }else {
            map.put("url","");
        }
        if (pushODpsData.containsKey("table_name")){
            map.put("table_name",pushODpsData.get("table_name"));
        }else {
            map.put("table_name","");
        }
        if (pushODpsData.containsKey("data_id")){
            map.put("data_id",pushODpsData.get("data_id"));
        }else {
            map.put("data_id","");
        }
        String token = ACCESS_TOKEN;
        if (ACCESS_TOKEN == null){
            tokenUtils t = new tokenUtils();
            t.getToken();
            token = ACCESS_TOKEN;
        }
        JSONObject jsonObject = httpReq.PostData("http://192.168.12.156:32002/api/dataNet/contribution/csv", map, token);
        return jsonObject.getJSONObject("data");
    }



    public  void registerCsv(Map param) throws Exception{

        JSONObject jsonObject = pushODpsByUrl(param.get("url").toString());
        JSONObject jsonObject1 = pushODps(jsonObject,param.get("name").toString(),param.get("description").toString());
        JSONObject jsonObject2 = CSVGetCode(jsonObject1, param.get("name").toString(), param.get("description").toString(), param.get("website").toString());
        Map map = new HashMap();
        map.put("name",param.get("name").toString());
        map.put("description",param.get("description").toString());
        map.put("website",param.get("website").toString());
        map.put("url",param.get("url").toString());
        map.put("bizType",3);
        map.put("dataType",1);
        map.put("dataId",jsonObject1.getString("data_id"));
        map.put("dataContributionId",jsonObject2.getString("id"));
        map.put("code",jsonObject2.getString("apiCode"));
        map.put("tableName",jsonObject1.getString("table_name"));
        map.put("odpsUrl",jsonObject1.getString("url"));
        spiderDataMapper.saveData(map);
    }


}

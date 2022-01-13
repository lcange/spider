import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class test {


    private Object List;

    @Test
    public void element() throws IOException {
        Document document = Jsoup.connect("https://datacatalogapi.worldbank.org/ddhxext/SearchData").post();
        //1.查找元素
        //通过id查询元素
        Element element1 = document.getElementById("a");
        //通过标签查询一些元素，然后查询第一个元素
        Elements element2 = document.getElementsByTag("a");
        //Element e=element2.first();
        //通过class查询一些元素
        Elements element3 = document.getElementsByTag("a");
        String str = element3.text();
        //通过属性名称查询一些元素
        Elements element4 = document.getElementsByAttribute("a");
        //通过属性名称和属性值查询一些元素
        Elements element5 = document.getElementsByAttributeValue("a", "b");
        //通过属性名称查询一些元素
        Elements element6 = document.getElementsByClass("a");
        //2.从元素中查取数据
//        String result = null;
//        //通过获取到的元素查询元素的id
//        result = element1.id();
//        //通过获取到的元素查询元素的className,其中element1类型为Element
//        result=element1.className();
//        //通过获取到的元素去查询属性值
//        result=element1.attr("class");
//        //通过获取到的元素去查询元素的所有属性
//        Attributes attr=element1.attributes();
//        result=attr.toString();
//        //通过获取到的元素去查询文本内容
//        result=element1.text();

        //3.通过选择器去获取元素
        //通过标签查找元素
        Elements elements1=document.select("div");
        String html = elements1.get(0).html();

        Element element=elements1.get(2);
        String str2=element.text();
        //通过class名称查找元素
        Elements elements2=document.select(".class");
        //通过id名称查找元素
        Elements elements3=document.select("#id");
        //通过属性名称查找元素
        Elements elements4=document.select("[text]");
        //通过属性值查找元素
        Elements elements5=document.select("[class=btn]");
        //遍历元素
        for(Element e :elements5){
            System.out.println(e.text());
        }
        //4.通过组合选择器去获取元素
        //元素+ID  element#id
        Elements elements6=document.select("h3#cid");
        //元素+CLASS  element.class
        Elements elements7=document.select("li.name");
        //元素+属性名  element[attr]
        Elements elements8=document.select("span[abc]");
        //查找父元素下的子元素，parent>child
        Elements elements9=document.select("#id>ul>li");
        //查找父元素下的所有元素，parent>*
        Elements elements10=document.select("#id>*");
    }

    @Test
    public void ttt(){
        Map map = new HashMap();
        PrintWriter out = null;
        BufferedReader in = null;
        JSONObject jsonObject = null;
        String result = "";
        try {
            URL realUrl = new URL("https://datacatalogfiles.worldbank.org/ddh-published/0060145/DR0084244/major_ports.csv?versionId=2021-11-04T17:26:54.6091484Z");
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Accept", "application/vnd.ms-excel"); // 设置接收数据的格式
            conn.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            // 获取URLConnection对象对应的输出流（设置请求编码为UTF-8）
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));

            out.flush();
            // 获取请求返回数据（设置返回数据编码为UTF-8）
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            jsonObject = JSON.parseObject(result);
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


    @Test
    public void download() throws MalformedURLException {
            int bytesum = 0;
            int byteread = 0;
            URL url = new URL("https://datacatalogfiles.worldbank.org/ddh-published/0060145/DR0084244/major_ports.csv?versionId=2021-11-04T17:26:54.6091484Z");
            try {
                URLConnection conn = url.openConnection();
                InputStream inStream = conn.getInputStream();
                FileOutputStream fs = new FileOutputStream("C:\\Users\\SUNXINCAN\\OneDrive\\桌面\\spider\\download.csv");
                byte[] buffer = new byte[1204];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Test
        public void up()  {

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
//                conn.setRequestProperty("Content-Type", "multipart/form-data"); // 设置发送数据的格式
                // 获取URLConnection对象对应的输出流（设置请求编码为UTF-8）

                File file = new File("C:\\Users\\SUNXINCAN\\OneDrive\\桌面\\spider\\download.csv");

                if (!file.exists() || !file.isFile()) {
                    throw new IOException("文件不存在");
                }
//                URL urlObj = new URL(url);
//                HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
//                con.setRequestMethod("POST"); // 设置关键值,以Post方式提交表单，默认get方式
//                con.setDoInput(true);
//                con.setDoOutput(true);
//                con.setUseCaches(false); // post方式不能使用缓存
                // 设置请求头信息
//                con.setRequestProperty("Connection", "Keep-Alive");
//                con.setRequestProperty("Charset", "UTF-8");
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
                    out.write(bufferOut, 0, bytes);
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

                List<Map> list = new ArrayList<>();
                jsonObject = JSON.parseObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                String oss_key = data.get("oss_key").toString();
                List<Map> rows = (java.util.List<Map>) data.get("rows");
                System.out.println(rows);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    @Test
    public void ccc(){
        JSON  param = JSON.parseObject("{\"SearchUrl\":\"https://itsdt-ddhext-search-prd.search.windows.net/indexes/ddh-dataset/docs?api-version=2020-06-30&search=*&$count=true&queryType=full&$top=10&$orderby=last_updated_date desc&$select=ClickCount,name,dataset_unique_id,identification,geographical_extent/coverage,lineage,Indicator,searchterms,data_quality/data_notes,last_updated_date,modified_on,constraints/security/classification,Resources&highlight=name,identification/title,identification/description,Indicators/title&$searchFields=Indicators/title,Indicators/description,identification/title,identification/description,searchterms,identification/acronym,geographical_extent/coverage/name,lineage/source_reference,high_priority_tags,priority_tags,Indicator,data_quality/data_notes,lineage/description,lineage/statistical_concept_and_methodology,lineage/study_type&highlightPreTag=<span class='search-chars'>&highlightPostTag=</span>&$filter=constraints/security/classification eq 'Public' and (Resources/any(res:res/format eq 'CSV'))&$skip=0&facet=constraints/license/license_id,count:20000&facet=constraints/security/classification,count:20000&facet=identification/working_unit_vpu/name,count:20000&facet=Resources/format,count:20000&facet=temporal_resolution/periodicity,count:20000&facet=identification/language_supported/name,count:20000&facet=spatial_resolution/granularity,count:20000&facet=keywords/name,count:20000&facet=identification/collection_id,count:20000&facet=geographical_extent/coverage/name,count:20000&facet=lineage/harvest_system,count:20000&facet=identification/topics/name,count:20000&facet=keywords_list,count:20000\",\"Body\":\"\",\"KeyName\":\"AzureSearchApiKey\",\"RequestType\":\"GET\",\"HeaderInfo\":[{\"Key\":\"x-ms-azs-return-searchid\",\"Value\":\"true\"}]}");
        PrintWriter out = null;
        BufferedReader in = null;
        JSONObject jsonObject = null;
        String result = "";

        try {
            URL realUrl = new URL("https://datacatalogapi.worldbank.org/ddhxext/SearchData");
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            conn.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            // 获取URLConnection对象对应的输出流（设置请求编码为UTF-8）
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            // 发送请求参数
            out.write(param.toJSONString());
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 获取请求返回数据（设置返回数据编码为UTF-8）
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            jsonObject = JSON.parseObject(result);
            JSONObject response = jsonObject.getJSONObject("Response");

            JSONArray value = response.getJSONArray("value");


            for (Object valueObj: value){

                JSONObject  valueJson = JSON.parseObject(valueObj.toString());
                JSONObject identification = valueJson.getJSONObject("identification");
                String name = valueJson.get("name").toString();
                String dataset_unique_id = valueJson.get("dataset_unique_id").toString();
                String description = identification.get("description").toString();

                JSONArray resources = valueJson.getJSONArray("Resources");


                for(Object obj : resources){

                    JSONObject  resourcesJson = JSON.parseObject(obj.toString());
                    String string1 = resourcesJson.get("format").toString();
                    if (resourcesJson.get("format").toString().equals("CSV") ){
                        if (resourcesJson.containsKey("harvest_source")){
                            String string = resourcesJson.get("harvest_source").toString();
                        }
                        String string = resourcesJson.get("url").toString();
                    }
                    if (resourcesJson.get("format").toString() == "JSON"){
                        String string = resourcesJson.get("harvest_source").toString();
                    }
                    if (resourcesJson.get("format").toString() == "API"){
                        String string = resourcesJson.get("harvest_source").toString();
                    }
                }
            }
            System.out.println(value);
            System.out.println(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
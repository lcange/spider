package spider.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class file {
    public static void downloadCSV(String CSVUrl,String name) throws MalformedURLException {
        int bytesum = 0;
        int byteread = 0;
        String readIn =  "";
        URL url = new URL(CSVUrl);
        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("C:\\Users\\SUNXINCAN\\OneDrive\\桌面\\spider\\"+name+".csv");
            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            fs.flush();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean createCsvFile(List<Map> list) {
        //标记文件生成是否成功；
        boolean flag = true;

        //文件输出流
        BufferedWriter fileOutputStream = null;

        try {
            //含文件名的全路径
            String fullPath = "C:\\Users\\SUNXINCAN\\OneDrive\\桌面\\spider" + File.separator + "AAA" + ".csv";
            File file = new File(fullPath);
            if (!file.getParentFile().exists())     //如果父目录不存在，创建父目录
            {
                file.getParentFile().mkdirs();
            }
            if (file.exists())     //如果该文件已经存在，删除旧文件
            {
                file.delete();
            }
//            file = new File(fullPath);
            file.createNewFile();
            //格式化浮点数据
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMaximumFractionDigits(10);     //设置最大小数位为10；

            //格式化日期数据
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            //实例化文件输出流
            fileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"), 1024);

            //遍历输出每行
            for (Map map : list) {
                fileOutputStream.newLine();   //换行，创建一个新行；
                String ip = map.getOrDefault("ip", "") + ",";
                String cityName = map.getOrDefault("cityName", "") + ",";
                String lng = map.getOrDefault("lng", "") + ",";
                String lat = map.getOrDefault("lat", "") + ",";
                String origin_lng = map.getOrDefault("origin_lng", "") + ",";
                String origin_lat = map.getOrDefault("origin_lat", "") + ",";
                String target_lng = map.getOrDefault("target_lng", "") + ",";
                String target_lat = map.getOrDefault("target_lat", "") + ",";
                String api_json_code = map.getOrDefault("api_json_code", "") + ",";
                String api_csv_code = map.getOrDefault("api_csv_code", "") + ",";
                String api_json_live = "";
                String api_csv_live = "";
                if (api_json_code != ",") {
                    api_json_live = ",";
                } else {
                    api_json_live = 1 + ",";
                }
                if (api_csv_code != ",") {
                    api_csv_live = ",";
                } else {
                    api_csv_live = 1 + ",";
                }
                String line = ip + lng + lat + origin_lng + origin_lat + target_lng + target_lat + api_json_code + api_csv_code + api_json_live + api_csv_live;
                fileOutputStream.write(line);
            }

            fileOutputStream.flush();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }


}

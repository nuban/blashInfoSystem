package vip.imagin.blast.modules.user.controller;
import org.json.JSONObject;
import vip.imagin.blast.modules.user.entity.Setingmodel;
import vip.imagin.blast.utils.GsonUtils;
import vip.imagin.blast.utils.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AiFace {
        public static String getAuth() {
            String ak= "9V7dkSGSeRBaxcz7ctjykUjW";
            // 官网获取的 Secret Key 更新为你注册的
            String sk = "cuWkFvtZ62ovvzfpzocrhBewzrz1Hr5N";
            // 获取token地址
            String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
            String getAccessTokenUrl = authHost
                    // 1. grant_type为固定参数
                    + "grant_type=client_credentials"
                    // 2. 官网获取的 API Key
                    + "&client_id=" + ak
                    // 3. 官网获取的 Secret Key
                    + "&client_secret=" + sk;
            try {
                URL realUrl = new URL(getAccessTokenUrl);
                // 打开和URL之间的连接
                HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                // 获取所有响应头字段
                Map<String, List<String>> map = connection.getHeaderFields();
                // 遍历所有的响应头字段
                for (String key : map.keySet()) {
                    System.err.println(key + "--->" + map.get(key));
                }
                // 定义 BufferedReader输入流来读取URL的响应
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String result = "";
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                /**
                 * 返回结果示例
                 */
                System.err.println("result:" + result);
                JSONObject jsonObject = new JSONObject(result);
                String access_token = jsonObject.getString("access_token");
                return access_token;
            } catch (Exception e) {
                System.err.printf("获取token失败！");
                e.printStackTrace(System.err);
            }
            return null;
        }

    public static Map FaceSearch(Setingmodel setingmodel) throws IOException {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
//        byte[] bytes = Files.readAllBytes(Paths.get(setingmodel.getImgpath()));
//        String imagebase64 = Base64Util.encode(bytes);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", setingmodel.getImgpath());
            map.put("liveness_control", setingmodel.getLiveness_Control());
            map.put("group_id_list", setingmodel.getGroupID());
            map.put("image_type", setingmodel.getImage_Type());
            map.put("quality_control", setingmodel.getQuality_Control());
            String param = GsonUtils.toJson(map);
            String result = HttpUtil.post(url, getAuth(), "application/json", param);
            Map<String,Object> resultmaps = GsonUtils.fromJson(result, Map.class);
            System.out.println("cuowudaima"+resultmaps.get("error_code"));
            if(!resultmaps.get("error_code").toString().equals("222207.0")){
                String resultlist = resultmaps.get("result").toString();
                String substring = resultlist.substring(1, resultlist.length() - 1);
                String regEx="[\n`~!@#$%^&*()+|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
                String aa = "";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(substring);//这里把想要替换的字符串传进来
                String newString = m.replaceAll(aa).trim();
                String[] split = newString.split(",");
                split[1]=split[1].substring(10, split[1].length());
                String face_token=split[0].substring(11,split[0].length());
                String group_id=split[1].substring(9,split[1].length());
                String user_id=split[2].substring(8,split[2].length());
                String user_info=split[3].substring(10,split[3].length());
                String score=split[4].substring(6,split[4].length());
                System.out.println(face_token);
                resultmaps.put("face_token",face_token);
                resultmaps.put("group_id",group_id);
                resultmaps.put("user_id",user_id);
                resultmaps.put("user_info",user_info);
                resultmaps.put("score",score);
                return resultmaps;

            }else {
                System.out.println("失败分支");
                return resultmaps;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}



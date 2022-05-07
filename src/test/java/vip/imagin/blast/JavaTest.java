package vip.imagin.blast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import vip.imagin.blast.modules.meterial.entity.Caseman;
import vip.imagin.blast.modules.selectsomething.entity.Explosive;
import vip.imagin.blast.modules.selectsomething.entity.Selectcasemantemp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JavaTest {

//    @Test
//    public void test1(){
//        Caseman caseman = new Caseman(1L,"哈哈哈","ttt","tttt");
//        String s = JSON.toJSONString(caseman);
//        System.out.println(s);
//    }
//
    @Test
    public void test1(){
        Selectcasemantemp selectcasemantemp = new Selectcasemantemp(1,1L,"12344","我就是想查","1","",1L,"1");

        String s = JSON.toJSONString(selectcasemantemp);
        System.out.println(s);

    }


    @Test
    public void test2(){
        Explosive explosive = new Explosive(1L,"hhh","jhjj.jpg","ddfd",1L,"qbw","起爆器材");
        explosive.setDescription("爆炸物就是这么的的点点滴滴");
        String s = JSON.toJSONString(explosive);
        System.out.println(s);
    }

    @Test
    public void testrci(){

        //分词
        List<String> list = new ArrayList<String>();
        Process proc;
        try {
            proc = Runtime.getRuntime().exec(new String[] {"python","classpath:mian.py" ,"现场非常惨烈，什么都有，炸弹，原子弹，手榴弹"});// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            String line = null;
            String temp = null;
            while ((temp = in.readLine()) != null) {
                line = temp;
                String substring = line.substring(1, line.length() - 1);
                String[] split = substring.split("\'");
                for (int i = 0 ;i < split.length;i++) {
                    if(i%2 == 1){
                        list.add(split[i]);
                    }
                }
            }
            System.out.println(list);
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

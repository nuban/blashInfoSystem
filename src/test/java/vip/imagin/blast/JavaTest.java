package vip.imagin.blast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import vip.imagin.blast.modules.meterial.entity.Caseman;
import vip.imagin.blast.modules.selectsomething.entity.Selectcasemantemp;

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

}

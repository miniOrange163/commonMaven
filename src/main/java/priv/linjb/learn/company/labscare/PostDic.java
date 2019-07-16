package priv.linjb.learn.company.labscare;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import priv.linjb.common.util.http.HttpPrivateUtils;
import priv.linjb.common.util.http.SimpleHttpUtils;
import priv.linjb.common.util.json.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/5/22
 *  
 * @name: 
 *
 * @Description: 
 */
public class PostDic {

    public static String url = "http://127.0.0.1:9999/api/admin/dic/v1";
    public static void main(String[] args) {

        List<Header> headerList = new ArrayList<>();
        headerList.add(new BasicHeader("token", "98ae00aaaf724441970afaaae4ce0198"));
        List<DicEntity> list = createList();

        int num = list.size();
        int success = 0;
        for (DicEntity dic : list) {

            String result = SimpleHttpUtils.postJson(url, JsonUtils.convertToString(dic),headerList,null);
            System.out.println(result);
            if (result.indexOf("\"code\":1") > -1) {
                success ++;
            }
            else{
                System.out.println(JsonUtils.convertToString(dic));
            }

        }

        System.out.println("计划数量："+num+",成功数量："+success);

    }

    private static List<DicEntity> createList() {

        List<DicEntity> list = new ArrayList<>();



        list.add(new DicEntity("其他食品", "food", "food_otherfood"));
        list.add(new DicEntity("化妆品", "food", "food_cosmetics"));
        int n = 1;
        for (DicEntity dic : list) {
            dic.setDicType("gaugingv2_product_type");
            dic.setDicValue(dic.getDicName());
            dic.setLeaf("leaf");
            dic.setSort(n++);
        }

        return list;
    }


}

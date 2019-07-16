package priv.linjb.common.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/7/16
 *  
 * @name: 
 *
 * @Description: 
 */
public class RegexUtil {

    // 是否包含中文
    public final static Pattern chinaPattern = Pattern.compile("[\\u4e00-\\u9fa5]");
    // 是否以数字开头
    public final static Pattern numHeadPattern = Pattern.compile("^\\d+.*");
    // 判断文件扩展名是否属于图片
    public final static Pattern picTailPattern = Pattern.compile("^.*[.](png|gif|jpg|jpeg|bmp|tif|psd|ICO)$");
    // 判断文件夹名称是否包含非法字符
    public final static Pattern folderPattern = Pattern.compile("((?![\\\\]|[:]|[*]|[?]|[\"]|[<]|[>]|[|]).)*");



    public static void pattern(String str){

        Matcher matcher = chinaPattern.matcher(str);
        final boolean matches = matcher.matches();
    }

}

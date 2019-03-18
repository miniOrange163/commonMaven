package priv.linjb.common.util.file;
/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/6/22
 *  
 * @name: 
 *
 * @Description: 
 */

import java.io.File;

/**
 * 获取打包后jar的路径信息
 *
 * @author Rex
 */
public class JarToolUtil
{


    /**
     * 获取jar包绝对路径，包含jar包的文件名
     *
     * @return
     */
    public static String getJarPath()
    {
        File file = getFile();
        if (file == null) {
            return null;
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取jar包所在目录
     *
     * @return
     */
    public static String getJarDir()
    {
        File file = getFile();
        if (file == null) {
            return null;
        }
        return getFile().getParent();
    }

    /**
     * 获取jar包名
     *
     * @return
     */
    public static String getJarName()
    {
        File file = getFile();
        if (file == null) {
            return null;
        }
        return getFile().getName();
    }

    /**
     * 获取当前Jar文件
     *
     * @return
     */
    private static File getFile()
    {
        // 关键是这行...
        String path = JarToolUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        try
        {
            path = java.net.URLDecoder.decode(path, "UTF-8"); // 转换处理中文及空格
        }
        catch (java.io.UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
        return new File(path);
    }
}
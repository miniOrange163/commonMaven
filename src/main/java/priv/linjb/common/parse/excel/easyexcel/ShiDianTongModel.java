package priv.linjb.common.parse.excel.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
//import java.util.Date;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/5/27
 *  
 * @name: 
 *
 * @Description: 
 */
public class ShiDianTongModel  extends BaseRowModel {
    @ExcelProperty(index = 0)
    private String conty;
    @ExcelProperty(index = 1)
    private String title;
    @ExcelProperty(index = 2 ,format = "yyyy-MM-dd")
    private Date date;

    public String getConty() {
        return conty;
    }

    public void setConty(String conty) {
        this.conty = conty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "ShiDianTongModel{" +
                "conty='" + conty + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}

package priv.linjb.common.parse.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Linjb
 * @Date: 2018/12/1
 * @name:
 * @Description:
 */
public class EasyExcelTest {

    // 按模版导出
    @Test
    public void test() throws FileNotFoundException {

        try (InputStream in = new FileInputStream("my/excel/easyExcel.xlsx");
             OutputStream out = new FileOutputStream("my/excel/easyExcel_Out.xlsx")) {

            ExcelWriter writer = new ExcelWriter(in,out, ExcelTypeEnum.XLSX,false);

            Sheet sheet = new Sheet(0,1,ImportInfo.class);
            sheet.setStartRow(0);
            writer.write(getData(),sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<? extends BaseRowModel> getData() {
        List list = new ArrayList();

        list.add(new ImportInfo("桂A11111", "蓝", 20));
        list.add(new ImportInfo("桂A22222", "黄", 30));
        list.add(new ImportInfo("桂A33333", "蓝", 40));

        return list;
    }

    public class ImportInfo extends BaseRowModel {
        @ExcelProperty(index = 0)
        private String carno;
        @ExcelProperty(index = 1)
        private String color;
        @ExcelProperty(value = "车辆品牌",index = 4)
        private Integer sum;

        public ImportInfo(String carno, String color, Integer sum) {
            this.carno = carno;
            this.color = color;
            this.sum = sum;
        }

        public String getCarno() {
            return carno;
        }

        public void setCarno(String carno) {
            this.carno = carno;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getSum() {
            return sum;
        }

        public void setSum(Integer sum) {
            this.sum = sum;
        }
    }
}

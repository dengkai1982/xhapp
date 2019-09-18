package kaiyi.app.tcsys.test;

import kaiyi.puer.commons.poi.ExcelUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ExcelParse {

    @Test
    public void excelParse() throws IOException {
        File file=new File("/Users/dengkai/金红/批量导入模板.xlsx");
        ExcelUtils.readExcel(file,c->{
            for(int i=0;i<c.size();i++){
                System.out.print(c.get(i).getData().stringValue());
            }
            System.out.println();
        });
    }


}

package kaiyi.app.tcsys.test;

import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.commons.poi.ExcelUtils;
import kaiyi.puer.commons.utils.CoderUtil;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.json.creator.JsonMessageCreator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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

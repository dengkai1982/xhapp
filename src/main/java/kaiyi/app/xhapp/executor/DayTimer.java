package kaiyi.app.xhapp.executor;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.log.Logger;
import kaiyi.puer.commons.time.DateTimeUtil;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;

/**
 * 日处理方式
 */
public class DayTimer {

    @Resource
    private ConfigureService configureService;
    @Resource
    private AccountService accountService;
    public void dispose(){
        clearTempFile();
        int day=Integer.parseInt(DateTimeUtil.getCurrentDay());
        if(day==1){
            accountService.monthClear();
        }else{
            accountService.dayClear();
        }
    }

    private void clearTempFile(){
        String fileDir=configureService.getStringValue(ConfigureItem.DOC_TEMP_PATH);
        File file=new File(fileDir);
        Logger logger=configureService.getLogger(this.getClass());
        if(file.isDirectory()){
            Date currentDate=new Date();
            File[] files=file.listFiles();
            for(File f:files){
                if(f.isFile()){
                    long lastModifyed=f.lastModified();
                    Date fileDate=new Date(lastModifyed);
                    long day=DateTimeUtil.getDifference(fileDate,currentDate,DateTimeUtil.Type.day);
                    if(day>=2){
                        logger.info(()->{
                            return "删除临时上传的文件,文件:"+f.getAbsolutePath();
                        });
                        f.delete();
                    }
                }
            }
        }
        logger.close();
    }
}

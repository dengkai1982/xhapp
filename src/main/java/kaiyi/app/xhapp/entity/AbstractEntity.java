package kaiyi.app.xhapp.entity;

import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.fs.DiskFile;
import kaiyi.puer.commons.utils.CoderUtil;
import kaiyi.puer.h5ui.bean.PageFieldData;
import kaiyi.puer.h5ui.entity.AbstractDocumentStorageEntity;
import kaiyi.puer.h5ui.ft.DocumentFieldType;
import kaiyi.puer.h5ui.service.DocumentService;
import kaiyi.puer.h5ui.service.PageElementManager;
import kaiyi.puer.web.servlet.FileInfo;
import kaiyi.puer.web.servlet.UploadFileInfo;
import kaiyi.puer.web.servlet.WebInteractive;

import javax.persistence.MappedSuperclass;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity extends AbstractDocumentStorageEntity {
    private static final long serialVersionUID = -2000100881726573736L;
    //处理默认使用 hex 并且用,号分割，存储文件的情况,并且返回ZUI需要的文件上传控件
    @Override
    public <T> void tryStorage(WebInteractive interactive, String className, Map<String, JavaDataTyper> params, String fileSaveDir) {
        PageElementManager pageElementManager=PageElementManager.getInstance();
        StreamCollection<PageFieldData> pageFieldDatas = pageElementManager.getDocumentPageFieldDatas(className);
        for(PageFieldData fieldData:pageFieldDatas){
            String fieldName=fieldData.getField().getName();
            UploadFileInfo uploadFileInfo=interactive.parseUploadFileInfo(fieldName);
            if(Objects.nonNull(uploadFileInfo)){
                StreamCollection<String> pathStream=new StreamCollection<>();
                DocumentFieldType type=(DocumentFieldType)fieldData.getType();
                List<FileInfo> fileInfos=uploadFileInfo.getFileInfo();
                for(FileInfo fileInfo:fileInfos){
                    DiskFile savedFile=DocumentService.filePathToStorage(fileSaveDir,fileInfo.getFile().getFile().getAbsolutePath());
                    String hex=CoderUtil.stringToHex(savedFile.getFile().getAbsolutePath());
                    pathStream.add(hex);
                }
                String joinString=pathStream.joinString(m->m,type.getSplit());
                params.put(fieldName,new JavaDataTyper(joinString));
            }
        }
    }
}

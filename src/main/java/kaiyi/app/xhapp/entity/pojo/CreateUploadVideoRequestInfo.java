package kaiyi.app.xhapp.entity.pojo;

public class CreateUploadVideoRequestInfo {
    /**
     * 视频标题
     * */
    private String title;
    /**
     * 文件名，必须带有后缀
     */
    private String fileName;
    /**
     * 分类ID
     */
    private String cateId;
    /**
     * 视频转码模板号
     */
    private String templateId;
    /**
     * 视频上传结果回调地址
     */
    private String callbackURL;
    /**
     * 视频上传结果回调协议,填写http或者https
     */
    private String callbackType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }
}

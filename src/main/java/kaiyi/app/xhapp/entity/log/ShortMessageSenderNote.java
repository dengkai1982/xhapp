package kaiyi.app.xhapp.entity.log;
import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldBoolean;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;
import java.util.Date;

/**
 * 短信发送记录
 *
 */
@Entity(name=ShortMessageSenderNote.TABLE_NAME)
@Table(indexes = {
		@Index(name="smsn_index_phone",columnList = "phone",unique = false),
		@Index(name="smsn_index_create_time",columnList = "createTime",unique = false)
})
@PageEntity(showName = "短信发送记录",entityName = "shortMessageSenderNote",serviceName = "shortMessageSenderNoteService")
public class ShortMessageSenderNote extends AbstractEntity {
	public static final String TABLE_NAME="short_message_sender_note";
	private static final long serialVersionUID = -257996796147815673L;
	@PageField(label = "手机号码",tableLength = 140)
	private String phone;
	//是否失效,true已失效,false未失效
	@PageField(label = "是否失效",type = FieldType.BOOLEAN)
	@FieldBoolean(values = {"已失效","未失效"})
	private boolean invalide;
	//是否验证,true 已验证,false,未验证
	@PageField(label = "完成验证",type = FieldType.BOOLEAN)
	@FieldBoolean(values = {"已验证","未验证"})
	private boolean validate;
	//短信正文
	@PageField(label = "短信内容",tableLength = 140)
	private String code;
	//用途
	@PageField(label = "用途")
	private String codeUsage;
	@IDate
	@PageField(label = "发送时间",type = FieldType.DATETIME,tableLength = 160)
	private Date createTime;

	private Date updateTime;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isInvalide() {
		return invalide;
	}

	public void setInvalide(boolean invalide) {
		this.invalide = invalide;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public ShortMessageSenderNote() {
	}

	public ShortMessageSenderNote(String phone, boolean invalide, boolean validate, String code) {
		this.phone = phone;
		this.invalide = invalide;
		this.validate = validate;
		this.code = code;
		this.createTime=new Date();
		this.updateTime=new Date();
	}

	public String getCodeUsage() {
		return codeUsage;
	}

	public void setCodeUsage(String codeUsage) {
		this.codeUsage = codeUsage;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

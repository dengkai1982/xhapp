package kaiyi.app.xhapp.entity.pub;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.puer.commons.bean.Identify;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name=Configure.TABLE_NAME)
public class Configure {
	private static final long serialVersionUID = -397798393299664204L;
	public static final String TABLE_NAME="configure";
	private ConfigureItem item;
	
	private String value;
	@Id
	@Enumerated(EnumType.STRING)
	public ConfigureItem getItem() {
		return item;
	}

	public void setItem(ConfigureItem item) {
		this.item = item;
	}
	@Lob
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

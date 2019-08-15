package kaiyi.app.xhapp.entity.distribution.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

/**
 * 申请提现状态
 *
 */
public enum WithdrawStatus implements H5ChosenInterface {
	/**
	 * 等待审核
	 */
	WAIT_VERIFY{
		@Override
		public String toString() {
			return "等待审核";
		}
		@Override
		public String getHexColor() {
			return "#a1a1a2";
		}
	},
	/**
	 * 已拒绝
	 */
	REFUSED{
		@Override
		public String toString() {
			return "已拒绝";
		}
		@Override
		public String getHexColor() {
			return "#f1a325";
		}
	},
	/**
	 * 已完成
	 */
	FINISH{
		@Override
		public String toString() {
			return "已完成";
		}
		@Override
		public String getHexColor() {
			return "#38b03f";
		}
	}
	;
	@Override
	public String[] getSearchValues() {
		return new String[]{
				toString(),PinyinUtils.getStringPinYin(toString())
		};
	}

	@Override
	public int getItemNumber() {
		return ordinal();
	}

	@Override
	public String getValue() {
		return toString();
	}

	@Override
	public String getShowName() {
		return name();
	}


	@Override
	public String getDataKeys() {
		return getSearchValues().toString();
	}
}

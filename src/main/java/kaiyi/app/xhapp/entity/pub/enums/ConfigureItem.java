package kaiyi.app.xhapp.entity.pub.enums;
/**
 * 系统配置项
 *
 */
public enum ConfigureItem {
	/**
	 * 短信接口url
	 */
	SMS_SEND_URL{
		@Override
		public String toString() {
			return "短信接口url";
		}
	},
	/**
	 * 短信发送者用户名
	 */
	SMS_USER_NAME{
		@Override
		public String toString() {
			return "短信发送用户名";
		}
	},
	/**
	 * 短信发送者密码
	 */
	SMS_PASSWORD{
		@Override
		public String toString() {
			return "短信发送密码";
		}
	},
	/**
	 * 商户名称
	 */
	BUSINESS_NAME{
		@Override
		public String toString() {
			return "商户名称";
		}
	},
	/**
	 * 客户电话
	 */
	CUSTOMER_PHONE{
		@Override
		public String toString() {
			return "短信发送方名称";
		}
	},
	WEIXIN_PUBLIC_NUMBER{
		//gh_1e290140b539
		@Override
		public String toString() {
			return "公众号";
		}
	},
	WEIXIN_APPID{
		//wxb9c1bafe582b1125 鑫鸿的
		@Override
		public String toString() {
			return "微信Appid";
		}
	},
	WEIXIN_SECRET{
		//bafcd83a39b653887afdea4abbb1bd79 鑫鸿的
		@Override
		public String toString() {
			return "微信Secret";
		}
	},
	WEIXIN_API_KEY{
		//LXggppwe3qtfWukaWjdaB9nvXgl1z1Y9
		@Override
		public String toString() {
			return "微信用于支付的APIKey";
		}
	},
	WEIXIN_MCH_ID{
		//1487739792
		@Override
		public String toString() {
			return "微信MCH_ID";
		}
	},
	DOC_SAVE_PATH{
		@Override
		public String toString() {
			return "文档存放路径";
		}
	},
	DOC_TEMP_PATH{
		@Override
		public String toString() {
			return "文档上传路径";
		}
	},
	DOC_SERVER_PREFIX{
		@Override
		public String toString() {
			return "文件服务器访问前缀";
		}
	},
	LOG_DIR{
		@Override
		public String toString() {
			return "日志写入目录";
		}
	},
	ALIYUN_USER_ID{
		@Override
		public String toString() {
			return "阿里云USERID";
		}
	},
	ALIYUN_VOD_ACCESS_KEY_ID{
		@Override
		public String toString() {
			return "阿里云AccessId";
		}
	},
	ALIYUN_VOD_ACCESS_KEY_SECRET(){
		@Override
		public String toString() {
			return "阿里云Secret";
		}
	},
	VOD_UPLOAD_TEMPLATE_ID{
		@Override
		public String toString() {
			return "上传模板号";
		}
	},
	VOD_UPLOAD_CATE_ID{
		@Override
		public String toString() {
			return "视频分类ID号";
		}
	},
	ALIPAY_APPID{
		//2018013102118231
		@Override
		public String toString() {
			return "支付宝APPID";
		}
	},
	MY_PRIVATE_KEY{
		@Override
		public String toString() {
			return "我的私钥";
		}
	},
	MY_PUBLIC_KEY{
		@Override
		public String toString() {
			return "我的公钥";
		}
	},
	CHARSET{
		@Override
		public String toString() {
			return "字符编码";
		}
	},
	ALIPAY_FORMAT{
		@Override
		public String toString() {
			return "编码格式";
		}
	},
	ALIPAY_PUBLIC_KEY{
		@Override
		public String toString() {
			return "支付宝公钥";
		}
	},
	ALIPAY_SIGNTYPE{
		@Override
		public String toString() {
			return "支付宝签名类型";
		}
	},
	ALIPAY_GATEWAY_URL{
		@Override
		public String toString() {
			return "支付宝支付网关";
		}
	},
	/****************需要进行配置的参数******************/
	EXAM_QUESTION_SINGLE_NUMBER{
		@Override
		public String toString() {
			return "单选题数量";
		}
	},
	EXAM_QUESTION_MULTIPLE_NUMBER{
		@Override
		public String toString() {
			return "多选题数量";
		}
	},
	EXAM_QUESTION_ANSWER_NUMBER{
		@Override
		public String toString() {
			return "问答题数量";
		}
	},
	SALE_LEVEL_COMMISSION_1{
		@Override
		public String toString() {
			return "直接上级提成比例";
		}
	},
	SALE_LEVEL_COMMISSION_2{
		@Override
		public String toString() {
			return "上上级提成比例";
		}
	},
	LIMIT_WITHDRAW_AMOUNT{
		@Override
		public String toString() {
			return "提现金额上限";
		}
	},
	INSIDE_MEMBER_COMMISSION{
		@Override
		public String toString() {
			return "内部会员提成比例";
		}
	},
	CURRENT_ANDROID_VERSION{
		@Override
		public String toString() {
			return "当前Android版本";
		}
	},
	CURRENT_IOS_VERSION{
		@Override
		public String toString() {
			return "当前IOS版本";
		}
	},
	SECURITY_IP{
		@Override
		public String toString() {
			return "安全IP地址";
		}
	}
}

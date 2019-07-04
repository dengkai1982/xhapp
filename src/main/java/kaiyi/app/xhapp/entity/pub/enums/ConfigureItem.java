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
		//wx32c8bcbddd3ae5a7
		@Override
		public String toString() {
			return "微信Appid";
		}
	},
	WEIXIN_SECRET{
		//77dcf484da7cb77dab73bbb496f58bbc
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
	}
}

package kaiyi.app.xhapp.controller.tag;
import kaiyi.app.xhapp.controller.mgr.ManagerController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
/**
 * 权限显示标签
 *
 */
public class VisibleableAuthorizationTag extends BodyTagSupport{
	private String url;
	private static final long serialVersionUID = 926164631406959313L;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
    public int doEndTag() throws JspException {
        return BodyTagSupport.EVAL_BODY_INCLUDE;
    }
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		if(ManagerController.hasAuthor(request, url)){
        	//显示标签
        	return BodyTagSupport.EVAL_BODY_INCLUDE;
        }
		return BodyTagSupport.SKIP_BODY;
	}
}

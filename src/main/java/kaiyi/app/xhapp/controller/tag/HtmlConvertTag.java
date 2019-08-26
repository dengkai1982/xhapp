package kaiyi.app.xhapp.controller.tag;

import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.web.html.HtmlConvertUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class HtmlConvertTag extends BodyTagSupport {
    private static final long serialVersionUID = 859186905516478207L;
    private String value;
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {
        if(value!=null){
            try {
                pageContext.getOut().print(HtmlConvertUtils.htmlUnescape(value));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.doStartTag();
    }
}

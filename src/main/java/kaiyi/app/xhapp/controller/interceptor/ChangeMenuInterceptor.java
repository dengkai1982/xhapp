package kaiyi.app.xhapp.controller.interceptor;
import kaiyi.app.xhapp.controller.mgr.ManagerController;
import kaiyi.app.xhapp.service.log.MenuTooltipService;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.web.servlet.ServletUtils;
import kaiyi.puer.web.servlet.WebInteractive;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChangeMenuInterceptor extends ManagerController implements HandlerInterceptor{
    private List<String> excludeUrl;
    @Resource
    private MenuTooltipService menuTooltipService;
    /**
     * 设置排除的url
     * @param excludeUrl
     */
    public void setExcludeUrl(List<String> excludeUrl) {
        this.excludeUrl = excludeUrl;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath= ServletUtils.getRequestPrefix(request, false);
        if(excludeUrl!=null){
            for(String exclude:excludeUrl){
                if(ServletUtils.urlCompare(exclude, requestPath)){
                    return true;
                }
            }
        }
        if(StreamCollection.assertNotEmpty(getMenuBySession(request))){
            WebInteractive interactive=WebInteractive.build(request,response);
            perfectPage(interactive);
            request.setAttribute(WebInteractive.SAVE_NAME,interactive);
        }
        menuTooltipService.clearMenuNotice(requestPath);
        return true;
    }
}

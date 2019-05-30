package kaiyi.app.xhapp.controller.mgr;

import kaiyi.app.xhapp.entity.jobs.Position;
import kaiyi.app.xhapp.service.jobs.PositionService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.CompareQueryExpress.*;
import kaiyi.puer.db.query.NullQueryExpress;
import kaiyi.puer.db.query.NullQueryExpress.*;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.web.elements.FormElementHidden;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(PersonnelController.rootPath)
@AccessControl(name = "人才库", weight = 4f, detail = "人才信息库管理", code = PersonnelController.rootPath)
public class PersonnelController extends ManagerController{
    public static final String rootPath=prefix+"/personnel";
    @Resource
    private PositionService positionService;

    @RequestMapping("/position")
    @AccessControl(name = "招聘职位", weight = 4.1f, detail = "管理招聘职位", code = rootPath+ "/position", parent = rootPath)
    public String position(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/position");
        String parent=interactive.getStringParameter("parent","");
        interactive.setRequestAttribute("parent",parent);
        QueryExpress queryExpress=null;
        FormElementHidden[] hiddens=null;
        if(StringEditor.notEmpty(parent)){
            Position parentposition=positionService.findForPrimary(parent);
            hiddens=new FormElementHidden[]{
                    new FormElementHidden("parent",parent)
            };
            queryExpress=new CompareQueryExpress("parent",Compare.EQUAL,parentposition);
            if(Objects.nonNull(parentposition)){
                Map<String,Object> params=new HashMap<>();
                StreamArray<Integer> pageNumbers=getPageNumberStack(interactive);
                boolean isback=interactive.getBoolean("isback","true",false);
                int previous=pageNumbers.getLast();
                if(isback){
                    pageNumbers.removeLast();
                    previous=pageNumbers.getLast();
                }else{
                    interactive.setCurrentPage(1);
                }
                String pageNumber=pageNumbers.joinString(m->{
                    return m.toString();
                },",");
                interactive.setRequestAttribute("pageNumber",pageNumber);
                params.put(WebInteractive.PAGINATION_PARAMETER_CURRENT_PAGE,previous);
                params.put("pageNumber",pageNumber);
                params.put("isback",true);
                if(parentposition.getParent()!=null){
                    params.put("parent",parentposition.getParent().getEntityId());
                }
                interactive.getWebPage().setBackPage(rootPath+"/position",params);
                interactive.setRequestAttribute("hasParent",true);
            }
        }else{
            queryExpress=new NullQueryExpress("parent",NullCondition.IS_NULL);
            hiddens=new FormElementHidden[]{
                    new FormElementHidden("topParent","topParent"),
                    new FormElementHidden("deliver","true")
            };
        }
        mainTablePage(interactive,positionService,queryExpress,hiddens,new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/position";
    }
    @RequestMapping("/position/new")
    @AccessControl(name = "新增招聘职位", weight = 4.11f,code = rootPath+ "/position/new", parent = rootPath+"/position")
    public String positionNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String parent=interactive.getStringParameter("parent","");
        if(Objects.nonNull(parent)){
            interactive.setRequestAttribute("parent",positionService.findForPrimary(parent));
        }
        newOrEditPage(interactive,positionService,3);
        setDefaultPage(interactive,rootPath+"/position");
        return rootPath+"/positionForm";
    }
    @RequestMapping("/position/modify")
    @AccessControl(name = "编辑招聘职位", weight = 4.12f,code = rootPath+ "/position/modify", parent = rootPath+"/position")
    public String positionModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,positionService,3);
        setDefaultPage(interactive,rootPath+"/position");
        String parent=interactive.getStringParameter("parent","");
        if(Objects.nonNull(parent)){
            interactive.setRequestAttribute("parent",positionService.findForPrimary(parent));
        }
        return rootPath+"/positionForm";
    }
    @PostMapping("/position/commit")
    public void positionCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,positionService);
        interactive.writeUTF8Text(msg.build());
    }
}

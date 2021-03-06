package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.access.enums.MemberShip;
import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.curriculum.CourseBuyerPrivilege;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.utils.EnumUtils;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.*;
import kaiyi.puer.db.query.CompareQueryExpress.Compare;
import kaiyi.puer.db.query.LinkQueryExpress.LINK;
import kaiyi.puer.json.JsonParserException;
import kaiyi.puer.json.JsonUtils;
import kaiyi.puer.json.parse.ArrayJsonParser;
import kaiyi.puer.web.html.HtmlConvertUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service("courseService")
public class CourseServiceImpl extends InjectDao<Course> implements CourseService {
    private static final long serialVersionUID = 6202595141917981754L;

    @Resource
    private CategoryService categoryService;
    /*
    buyerPrivileges: [{"memberShip":"0","free":"false","amount":"100"},
    {"memberShip":"1","free":"false","amount":"88"},
    {"memberShip":"2","free":"false","amount":"60"},
    {"memberShip":"3","free":"true","amount":"0"}]
     */

    @Override
    protected void objectBeforePersistHandler(Course course, Map<String, JavaDataTyper> params) throws ServiceException {
        StreamCollection<CourseBuyerPrivilege> privileges=parseCourseBuyerPrivilege(params);
        if(StreamCollection.assertNotEmpty(privileges)){
            privileges.forEach(p->{
                p.setCourse(course);
                p.setEntityId(randomIdentifier());
            });
        }
        course.setPrivileges(privileges.toSet());
    }

    @Override
    protected void objectBeforeUpdateHandler(Course course, Map<String, JavaDataTyper> data) throws ServiceException {
        StreamCollection<CourseBuyerPrivilege> privileges=parseCourseBuyerPrivilege(data);
        if(StreamCollection.assertNotEmpty(privileges)){
            Set<CourseBuyerPrivilege> buyerPrivileges = course.getPrivileges();
            for(CourseBuyerPrivilege privilege:buyerPrivileges){
                CourseBuyerPrivilege finder=privileges.find(f->{
                    return  f.getMemberShip().equals(privilege.getMemberShip());
                });
                if(Objects.nonNull(finder)){
                    privilege.setFree(finder.isFree());
                    privilege.setPrice(finder.getPrice());
                }
            }
            System.out.println(buyerPrivileges);
        }

    }

    private StreamCollection<CourseBuyerPrivilege> parseCourseBuyerPrivilege(Map<String, JavaDataTyper> params){
        StreamCollection<CourseBuyerPrivilege> privileges=new StreamCollection<>();
        if(Objects.nonNull(params.get("buyerPrivileges"))){
            String buyerPrivilegesJson=params.get("buyerPrivileges").stringValue();
            buyerPrivilegesJson=HtmlConvertUtils.htmlUnescape(buyerPrivilegesJson);
            if(!JsonUtils.isEmptyJson(buyerPrivilegesJson)){
                ArrayJsonParser parse=new ArrayJsonParser(buyerPrivilegesJson);
                try {
                    StreamCollection<Map<String,JavaDataTyper>> jsonArray=parse.doParser();
                    for(Map<String,JavaDataTyper> json:jsonArray){
                        MemberShip memberShip=EnumUtils.getEnumValueByOrdinal(MemberShip.class,json.get("memberShip").integerValue(0));
                        boolean free=json.get("free").booleanValue(false);
                        int price=json.get("price").currencyValue(2).getNoDecimalPointToInteger();
                        CourseBuyerPrivilege privilege=new CourseBuyerPrivilege(memberShip,free,price);
                        privileges.add(privilege);
                    }
                    return privileges;
                } catch (JsonParserException e) {
                    e.printStackTrace();
                }
            }
        }
        return privileges;
    }

    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress query = super.getCustomerQuery(params);
        if(Objects.nonNull(params.get("limitCategory"))){
            if(Objects.nonNull(params.get("currentCategory"))){
                String categoryId=params.get("currentCategory").stringValue();
                Category category=new Category();
                category.setEntityId(categoryId);
                query=new LinkQueryExpress(query,LINK.AND,new CompareQueryExpress("category",Compare.EQUAL,category));
            }else{
                query=new LinkQueryExpress(query,LINK.AND,new NullQueryExpress("category",NullQueryExpress.NullCondition.IS_NULL));
            }
        }else if(Objects.nonNull(params.get("category"))){
            String categoryId=params.get("category").stringValue();
            StreamCollection<Category> categories=categoryService.getCategoryAndChildren(categoryId);
            query=new LinkQueryExpress(query,LINK.AND,new ContainQueryExpress("category",ContainQueryExpress.CONTAINER.IN,
                    categories.toList()));
        }
        return query;
    }

    @Override
    public void changeSale(String entityId) {
        Course course=findForPrimary(entityId);
        if(Objects.nonNull(course)){
            course.setSale(!course.isSale());
            updateObject(course);
        }
    }

    @Override
    public void addBrowseVolume(String entityId) {
        Course course=findForPrimary(entityId);
        if(Objects.nonNull(course)){
            course.setBrowseVolume(course.getBrowseVolume()+1);
        }
    }

    @Override
    public void addBuyVolume(String entityId) {
        Course course=findForPrimary(entityId);
        if(Objects.nonNull(course)){
            course.setBuyVolume(course.getBuyVolume()+1);
        }
    }
}

package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.Entity;

@Entity(name=CourseColumn.TABLE_NAME)
@PageEntity(showName = "课程栏目",entityName = "courseColumn",serviceName = "courseColumnService")
public class CourseColumn extends AbstractEntity {
    public static final String TABLE_NAME="course_column";
    private static final long serialVersionUID = -8070048181470690195L;
    @PageField(label = "栏目名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

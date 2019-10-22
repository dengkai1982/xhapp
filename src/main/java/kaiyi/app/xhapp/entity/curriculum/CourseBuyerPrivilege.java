package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.enums.MemberShip;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;

@Entity(name=CourseBuyerPrivilege.TABLE_NAME)
@PageEntity(showName = "视频购买权限",entityName = "courseBuyerPrivilege",serviceName = "courseBuyerPrivilegeService")
public class CourseBuyerPrivilege extends AbstractEntity {
    public static final String TABLE_NAME="course_buyer_privilege";
    private static final long serialVersionUID = -611454684637217675L;

    private Course course;
    @PageField(label = "会员类型",type = FieldType.CHOSEN)
    @FieldChosen
    private MemberShip memberShip;
    @PageField(label = "会员类型",type = FieldType.BOOLEAN)
    @FieldBoolean(values={"免费","收费"})
    private boolean free;
    @ICurrency
    @PageField(label = "课程售价",type = FieldType.NUMBER)
    private int price;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="course")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    @Enumerated(EnumType.STRING)
    public MemberShip getMemberShip() {
        return memberShip;
    }

    public void setMemberShip(MemberShip memberShip) {
        this.memberShip = memberShip;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public StreamArray<String> filterField() {
        return new StreamArray<>(new String[]{"course"});
    }

    public CourseBuyerPrivilege() {
    }

    public CourseBuyerPrivilege(MemberShip memberShip, boolean free, int price) {
        this.memberShip = memberShip;
        this.free = free;
        this.price = price;
        if(free){
            this.price=0;
        }
    }
}

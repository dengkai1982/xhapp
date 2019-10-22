<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="/WEB-INF/htmlHeader.jsp"%>
    <style>
        input[type=checkbox],input[type=radio]{
            margin-left:10px;
        }
        .tree li>.list-toggle{
            top:2px;
        }
    </style>
</head>
<body style="background: #fff;padding: 10px 20px;">
${requestScope.privilegeList}
<script type="text/javascript">
    function pageReady(doc){
        $(".changePrivilege").click(function(){
            var $this=$(this);
            var checked=$this.prop("checked");
            setChildSelect($this,checked);
            setParentSelect($this,checked);
            var data = [];
            $(".changePrivilege").each(function(i,d){
                var row = {};
                row.menuid=$(d).attr("id");
                row.auth=$(d).prop("checked");
                data.push(row);
            });
            $.post("${managerPath}/account/visitorRole/changePrivilege${suffix}",{
                roleid:"${requestScope.roleId}",
                privileges:JSON.stringify(data)
            },function(result){
                //$("span").html(result);
            });
        });
        $(".tree span").css("margin-left","4px");
        $('.tree').tree('expand',$('.tree li:first-child'));
        function setParentSelect(el,checked){
            var parentId=el.attr("parent");
            if(parentId=="")return;
            var parent=$("#"+parentId);
            if(checked){
                parent.prop("checked",true);
            }else{
                var doit=true;
                $("input[parent='"+parentId+"']").each(function(i,d){
                    var each=$(d);
                    if(each.attr("id")!=el.attr("id")&&each.prop("checked")){
                        doit=false;
                        return false;
                    }
                });
                if(doit){
                    parent.prop("checked",false);
                }
            }
        }
        function setChildSelect(parent,checked){
            $("input[parent='"+parent.attr("id")+"']").each(function(i,d){
                var child=$(d);
                if(checked){
                    child.prop("checked",true);
                }else{
                    child.prop("checked",false);
                }
                setChildSelect(child,checked);
            })
        }
    }
</script>
</body>
</html>
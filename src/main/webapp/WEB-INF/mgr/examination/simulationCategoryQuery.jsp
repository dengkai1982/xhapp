<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="/WEB-INF/htmlHeader.jsp"%>
</head>
<body style="background: #fff;padding: 10px 20px;">
<ul id="categoryTree" class="tree tree-menu tree-lines">

</ul>
<script type="text/javascript">
    function pageReady(doc){
        var data=${requestScope.treeData};
        if(data.simulationCategory){
            data=data.simulationCategory;
        }
        $("#categoryTree").tree({
            animate:true,
            data:data,
            itemCreator:function($li, item){
                $li.append(item.title);
            }
        });
        $("#categoryTree").on("click",".categoryChoose",function(){
            var $this=$(this);
            var entityId=$this.attr("data-id");
            var name=$this.attr("data-name");
            if(window.parent.simulationCategoryQueryModalTrigger){
                $("input[name='${requestScope.referenceQueryName}']", window.parent.document).val(name);
                $("input[name='${requestScope.referenceQueryId}']", window.parent.document).val(entityId);
                window.parent.simulationCategoryQueryModalTrigger.close();
            }
        })
    };
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: BRUCE
  Date: 2022/1/24
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<%@ include file="include-head.jsp" %>
<link rel="stylesheet" href="./static/ztree/zTreeStyle.css"/>
<script type="text/javascript" src="./static/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="./static/crowdfunding-js/menu.js"></script>
<script type="text/javascript">
    $(function () {
        generateTree();

        // 给添加子节点按钮绑定单击响应函数
        $("#treeDemo").on("click",".addBtn",function(){
            window.pid = this.id;
            $("#menuAddModal").modal("show");
            return false;
        });

        $("#menuSaveBtn").click(function(){
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            var icon = $("#menuAddModal [name=icon]:checked").val();

            $.ajax({
                "url":"http://localhost:8080/admin/menu/add.json",
                "type":"post",
                "data":{
                    "pid": window.pid,
                    "name":name,
                    "url":url,
                    "icon":icon
                },
                "dataType":"json",
                "success":function(response){
                    var result = response.operationResult;
                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
                        generateTree();
                    }
                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },
                "error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }
            });
            $("#menuAddModal").modal("hide");
            $("#menuResetBtn").click();
        });

        // 给编辑按钮绑定单击响应函数
        $("#treeDemo").on("click",".editBtn",function(){
            window.id = this.id;
            $("#menuEditModal").modal("show");
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var key = "id";
            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam(key, value);

            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            $("#menuEditModal [name=icon]").val([currentNode.icon]);
            return false;
        });

        // 给更新模态框中的更新按钮绑定单击响应函数
        $("#menuEditBtn").click(function(){
            var name = $("#menuEditModal [name=name]").val();
            var url = $("#menuEditModal [name=url]").val();
            var icon = $("#menuEditModal [name=icon]:checked").val();
            $.ajax({
                "url":"http://localhost:8080/admin/menu/update.json",
                "type":"post",
                "data":{
                    "id": window.id,
                    "name":name,
                    "url":url,
                    "icon":icon
                },
                "dataType":"json",
                "success":function(response){
                    var result = response.operationResult;
                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
                        generateTree();
                    }
                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },
                "error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }
            });
            $("#menuEditModal").modal("hide");
        });

        // 给“×”按钮绑定单击响应函数
        $("#treeDemo").on("click",".removeBtn",function(){
            window.id = this.id;
            $("#menuConfirmModal").modal("show");
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var key = "id";
            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam(key, value);
            $("#removeNodeSpan").html(" 【<i class='"+currentNode.icon+"'></i>"+currentNode.name+"】");
            return false;
        });

        // 给确认模态框中的OK 按钮绑定单击响应函数
        $("#confirmBtn").click(function(){
            $.ajax({
                "url":"http://localhost:8080/admin/menu/delete.json",
                "type":"post",
                "data":{
                    "id":window.id
                },
                "dataType":"json",
                "success":function(response){
                    var result = response.operationResult;
                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
                        generateTree();
                    }
                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },
                "error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }
            });
            $("#menuConfirmModal").modal("hide");
        });
    })
</script>
<body>
<%@ include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <!-- 这个ul标签是ztree动态生成的节点所依附的静态节点-->
                    <ul id="treeDemo" class="ztree"></ul>

                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-add.jsp" %>
<%@include file="/WEB-INF/modal-menu-update.jsp"%>
<%@include file="/WEB-INF/modal-menu-delete.jsp"%>
</body>
</html>

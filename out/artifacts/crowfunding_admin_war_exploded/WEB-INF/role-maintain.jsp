<%--
  Created by IntelliJ IDEA.
  User: BRUCE
  Date: 2022/1/14
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>>
<html lang="zh-CN">
<%@ include file="include-head.jsp" %>
<link rel="stylesheet" href="./static/css/pagination.css"/>
<link rel="stylesheet" href="./static/ztree/zTreeStyle.css"/>
<script type="text/javascript" src="./static/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="./static/jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="./static/crowdfunding-js/role.js"></script>
<script type="text/javascript">
    $(function() {
        // 1.为分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";
        // 2.调用执行分页的函数，显示效果
        generatePage();
        // 3.给查询按钮绑定单击响应函数
        $("#searchBtn").click(function () {
            window.keyword = $("#keywordInput").val();
            generatePage();
        });
        // 4.点击新增按钮打开模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        });
        // 5.给新增模态框中的保存按钮绑定单击响应函数
        $("#saveRoleBtn").click(function () {
            var roleName = $.trim($("#addModal [name=roleName]").val());
            $.ajax({
                "url": "http://localhost:8080/admin/role/save.json",
                "type": "post",
                "data": {
                    "name": roleName
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        window.pageNum = 99999999;
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！ " + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#addModal").modal("hide");
            $("#addModal [name=roleName]").val("");
        });
        // 6.给页面上的“铅笔”按钮绑定单击响应函数，目是打开模态框
        $("#rolePageBody").on("click",".pencilBtn",function(){
            $("#editModal").modal("show");
            var roleName = $(this).parent().prev().text();
            window.roleId = this.id;
            $("#editModal [name=roleName]").val(roleName);
        });
        // 7.给更新模态框中的按钮绑定单击响应函数
        $("#updateRoleBtn").click(function(){
            var roleName = $("#editModal [name=roleName]").val();
            $.ajax({
                "url":"http://localhost:8080/admin/role/update.json",
                "type":"post",
                "data":{
                    "id":window.roleId,
                    "name":roleName
                },
                "dataType":"json",
                "success":function(response){
                    var result = response.operationResult;
                    if(result == "SUCCESS") {
                        layer.msg("操作成功！ ");
                        generatePage();
                    }
                    if(result == "FAILED") {
                        layer.msg("操作失败！ "+response.message);
                    }
                },
                "error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }
            });
            $("#editModal").modal("hide");
        });
        // 8.点击确认模态框中的删除按钮执行点击确认模态框中的删除
        $("#removeRoleBtn").click(function(){
            var requestBody = JSON.stringify(window.roleIdArray);
            $.ajax({
                "url":"http://localhost:8080/admin/role/delete.json",
                "type":"post",
                "data":requestBody,
                "contentType":"application/json;charset=UTF-8",
                "dataType":"json",
                "success":function(response){
                    var result = response.operationResult;
                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
                        generatePage();
                    }
                    if(result == "FAILED") {
                        layer.msg("操作失败！ "+response.message);
                    }
                    console.log(requestBody);
                },
                "error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }
            });
            $("#confirmModal").modal("hide");
        });
        // 9.给页面上的单条删除按钮绑定单击响应函数，目是打开模态框
        $("#rolePageBody").on("click",".removeBtn",function(){
            var roleName = $(this).parent().prev().text();
            var roleArray = [{
                roleId:this.id,
                roleName:roleName
            }];

            showConfirmModal(roleArray);
        });
        //10.给总的checkbox绑定单击函数
        $("#summaryBox").click(function(){
            var currentStatus = this.checked;
            $(".itemBox").prop("checked", currentStatus);
        });
        //11.全选、全不选反向操作
        $("#rolePageBody").on("click",".itemBox",function(){
            var checkedBoxCount = $(".itemBox:checked").length;
            var totalBoxCount = $(".itemBox").length;
            $("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount);
        });
        // 12.给批量删除的按钮绑定单击响应函数
        $("#batchRemoveBtn").click(function(){
            var roleArray = [];
            $(".itemBox:checked").each(function(){
                var roleId = this.id;
                var roleName = $(this).parent().next().text();
                roleArray.push({
                    "roleId":roleId,
                    "roleName":roleName
                });
            });
            console.log(roleArray)
            if(roleArray.length == 0) {
                layer.msg("请至少选择一个执行删除 ");
                return ;
            }
            showConfirmModal(roleArray);
        });
        // 13.给分配权限按钮绑定单击响应函数
        $("#rolePageBody").on("click",".checkBtn",function(){
            // 把当前角色id存入全局变量
            window.roleId = this.id;
            $("#assignModal").modal("show");
            fillAuthTree();
        });
        // 14.给分配权限模态框中的“分配”按钮绑定单击响应函数
        $("#assignBtn").click(function() {
            var authIdArray = [];
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
            var checkedNodes = zTreeObj.getCheckedNodes();
            for (var i = 0; i < checkedNodes.length; i++) {
                var checkedNode = checkedNodes[i];
                var authId = checkedNode.id;
                authIdArray.push(authId);
            }
            var requestBody = {
                "authIdList": authIdArray,
                "roleId": [window.roleId]
            };
            requestBody = JSON.stringify(requestBody);
            $.ajax({
                "url": "http://localhost:8080/admin/assign/auth/save.json",
                "type": "post",
                "data": requestBody,
                "contentType": "application/json;charset=UTF-8",
                "dataType": "json",
                "success": function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#assignModal").modal("hide");
        });
    });
</script>

<body>
<%@ include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning">
                            <i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showAddModalBtn" type="button" id="showAddModalBtn" class="btn btn-primary" style="float:right;" >
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            <tr>
                                <td></td>
                                <td>
                                    <input type="checkbox" class="itemBox">
                                </td>
                                <td></td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs">
                                        <i class=" glyphicon glyphicon-check"></i>
                                    </button>
                                    <button type="button" class="btn btn-primary btn-xs pencilBtn">
                                        <i class=" glyphicon glyphicon-pencil"></i>
                                    </button>
                                    <button type="button" class="btn btn-danger btn-xs removeBtn">
                                        <i class=" glyphicon glyphicon-remove"></i>
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-role-add.jsp" %>
<%@include file="/WEB-INF/modal-role-update.jsp"%>
<%@include file="/WEB-INF/modal-role-delete.jsp"%>
<%@include file="/WEB-INF/modal-role-assign-auth.jsp"%>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: BRUCE
  Date: 2022/1/30
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="zh-CN">
<%@ include file="include-head.jsp" %>
<script type="text/javascript">
    $(function (){
        $("#toRightBtn").click(function(){
            $("select:eq(0)>option:selected").appendTo("select:eq(1)");
        });
        $("#toLeftBtn").click(function(){
            $("select:eq(1)>option:selected").appendTo("select:eq(0)");
        });
        ("#submitBtn").click(function(){
            $("select:eq(1)>option").prop("selected","selected");
        });
    });
</script>
<body>
<%@ include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="http://43.138.154.51:8080/admin/main">首页</a></li>
                <li><a href="http://43.138.154.51:8080/admin/usermaintain">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline" action="http://43.138.154.51:8080/admin/assign/save" method="post">
                        <input type="hidden" name="adminId" value="${param.adminId}" />
                        <input type="hidden" name="pageNum" value="${param.pageNum}" />
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">

                                <c:forEach items="${unassignedRole }" var="role">
                                    <!--
                                    <option value="将来在提交表单时一起发送给handler 的值">在浏览器上让用户看到的数据</option>
                                    -->
                                    <option value="${role.id }">${role.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select name="roleIdList" class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${assignedRole }" var="role">
                                    <option value="${role.id }">${role.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <br>
                        <button id="submitBtn" type="submit" style="width: 150px;" class="btn btn-lg btn-success btn-block">保存</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

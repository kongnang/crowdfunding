<%--
  Created by IntelliJ IDEA.
  User: BRUCE
  Date: 2021/12/24
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="./static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="./static/css/font-awesome.min.css">
    <link rel="stylesheet" href="./static/css/login.css">
    <script type="text/javascript" src="./static/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="./static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $("button").click(function(){
// 调用 back()方法类似于点击浏览器的后退按钮 方法类似于点击浏览器的后退按钮 方法类似于点击浏览器的后退按钮 方法类似于点击浏览器的后退按钮 方法类似于点击浏览器的后退按钮 方法类似于点击浏览器的后退按钮 方法类似于点击浏览器的后退按钮
                window.history.back();
            });
        });
    </script>
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">
    <h2 class="form-signin-heading" style="text-align: center;">
        <i class="glyphicon glyphicon-log-in"></i> 出错啦！
    </h2>
    <!--
    requestScope存放request域中的Map
    .exception相当于request.getAttribute("exception")
    .message相当于exception.getMessge()
    -->
    <h3>${requestScope.exception.message}</h3>
    <button style="width: 300px;margin: 0px auto 0px auto;" class="btn btn-lg btn-success btn-block">返回刚才页面</button>
</div>
</body>
</html>

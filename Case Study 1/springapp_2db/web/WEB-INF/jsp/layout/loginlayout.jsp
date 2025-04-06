<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><tiles:insertAttribute name="logintitle" /></title>
        <link type="text/css" href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link type="text/css" href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
        <link type="text/css" href="<%=request.getContextPath()%>/resources/css/theme.css" rel="stylesheet">
        <link type="text/css" href="<%=request.getContextPath()%>/resources/images/icons/css/font-awesome.css" rel="stylesheet">
        <link type="text/css" href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600' rel='stylesheet'>
    </head>
    <body>

        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse">
                        <i class="icon-reorder shaded"></i>
                    </a>

                    <a class="brand" href="index.html">
                        Admin
                    </a>

                    <div class="nav-collapse collapse navbar-inverse-collapse">

                        <ul class="nav pull-right">

                            <li><a href="#">
                                    Sign Up
                                </a></li>



                            <li><a href="#">
                                    Forgot your password?
                                </a></li>
                        </ul>
                    </div><!-- /.nav-collapse -->
                </div>
            </div><!-- /navbar-inner -->
        </div><!-- /navbar -->

         <tiles:insertAttribute name="loginbody" />

        <!--/.wrapper-->

        <div class="footer">
            <tiles:insertAttribute name="loginfooter" />
        </div>
        <script src="<%=request.getContextPath()%>/resources/scripts/jquery-1.9.1.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
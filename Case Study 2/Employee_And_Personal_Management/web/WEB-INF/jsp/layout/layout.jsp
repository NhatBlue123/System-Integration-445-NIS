<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><tiles:insertAttribute name="title" /></title>
        <link type="text/css" href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link type="text/css" href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
        <link type="text/css" href="<%=request.getContextPath()%>/resources/css/theme.css" rel="stylesheet">
        <link type="text/css" href="<%=request.getContextPath()%>/resources/images/icons/css/font-awesome.css" rel="stylesheet">
        <link type="text/css" href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
              rel='stylesheet'>
    </head>
    <body>
        <div class="navbar navbar-fixed-top">
            <tiles:insertAttribute name="header" />
            <!-- /navbar-inner -->
        </div>
        <!-- /navbar -->
        <div class="wrapper">
            <div class="container">
                <div class="row">
                    <div class="span3">
                        <tiles:insertAttribute name="navigation" />
                        <!--/.sidebar-->
                    </div>
                    <!--/.span3-->
                    <div class="span9">
                        <tiles:insertAttribute name="body" />
                        <!--/.content-->
                    </div>
                    <!--/.span9-->
                </div>
            </div>
            <!--/.container-->
        </div>
        <!--/.wrapper-->
        <div class="footer">
            <tiles:insertAttribute name="footer" />
            <!--footer -->
        </div>
        <script src="<%=request.getContextPath()%>/resources/scripts/jquery-1.9.1.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/flot/jquery.flot.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/flot/jquery.flot.resize.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/datatables/jquery.dataTables.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/common.js" type="text/javascript"></script>
    </body>
</html>
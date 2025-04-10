<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div class="sidebar">
    <ul class="widget widget-menu unstyled">
        <li class="active"><a href="<%=request.getContextPath()%>/admin/dashboard.html"><i class="menu-icon icon-dashboard"></i>Dashboard
            </a></li>
        <li><a href="<%=request.getContextPath()%>/admin/EPerson.html"><i class="menu-icon icon-bullhorn"></i>EPerson Management </a></li>
        <li><a href="<%=request.getContextPath()%>/admin/employee/list.html"><i class="menu-icon icon-bullhorn"></i>Employee</a>
        </li>
        <li><a href="<%=request.getContextPath()%>/admin/payrates/list.html"><i class="menu-icon icon-inbox"></i>Pay Rates </a></li>
        <li><a href="task.html"><i class="menu-icon icon-tasks"></i>Tasks <b class="label orange pull-right">
                    19</b> </a></li>
    </ul>
    <!--/.widget-nav-->


<!--    <ul class="widget widget-menu unstyled">
        <li><a href="ui-button-icon.html"><i class="menu-icon icon-bold"></i> Buttons </a></li>
        <li><a href="ui-typography.html"><i class="menu-icon icon-book"></i>Typography </a></li>
        <li><a href="form.html"><i class="menu-icon icon-paste"></i>Forms </a></li>
        <li><a href="table.html"><i class="menu-icon icon-table"></i>Tables </a></li>
        <li><a href="charts.html"><i class="menu-icon icon-bar-chart"></i>Charts </a></li>
    </ul>-->
    <!--/.widget-nav-->
    <ul class="widget widget-menu unstyled">
        <li><a class="collapsed" data-toggle="collapse" href="#togglePages"><i class="menu-icon icon-cog">
                </i><i class="icon-chevron-down pull-right"></i><i class="icon-chevron-up pull-right">
                </i>More Pages </a>
            <ul id="togglePages" class="collapse unstyled">
                <li><a href="<%=request.getContextPath()%>/admin/user/list.html"><i class="icon-inbox"></i>All Users </a></li>
                <li><a href="<%=request.getContextPath()%>/admin/user/add.html"><i class="icon-inbox"></i>Create User</a></li>
                <li><a href="other-user-profile.html"><i class="icon-inbox"></i>Profile </a></li>
                
            </ul>
        </li>
        <li><a href="<%=request.getContextPath()%>/admin/logout.html"><i class="menu-icon icon-signout"></i>Logout </a></li>
    </ul>
</div>
          
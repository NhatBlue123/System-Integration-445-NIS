<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div class="navbar-inner">
    <div class="container">
        <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse">
            <i class="icon-reorder shaded"></i></a><a class="brand" href="index.html">Admin </a>
        <div class="nav-collapse collapse navbar-inverse-collapse">
            <ul class="nav nav-icons">
                <li class="active"><a href="#"><i class="icon-envelope"></i></a></li>
                <li><a href="#"><i class="icon-eye-open"></i></a></li>
                <li><a href="#"><i class="icon-bar-chart"></i></a></li>
            </ul>
            <form class="navbar-search pull-left input-append" action="#">
                <input type="text" class="span3">
                <button class="btn" type="button">
                    <i class="icon-search"></i>
                </button>
            </form>
            <ul class="nav pull-right">
                <li><a href="#">Welcome: <%=request.getSession().getAttribute("USER")%> </a></li>
                <li class="nav-user dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="<%=request.getContextPath()%>/resources/images/user.png" class="nav-avatar" />
                        <b class="caret"></b></a>
                    <ul class="dropdown-menu">ss
                        <li><a href="#">Your Profile</a></li>
                        <li><a href="#">Edit Profile</a></li>
                        <li><a href="#">Account Settings</a></li>
<button onclick="redirectToHRApp()" style="margin-top: 15px; margin-left: 10px">
    Login HRAPP
</button>
                        <li class="divider"></li>
                        <li><a href="<%=request.getContextPath()%>/admin/logout.html">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
<script>
function redirectToHRApp() {
    var username = "<%= request.getSession().getAttribute("USER") %>";
    if (!username || username.trim() === "null") {
        alert("User không hợp lệ!");
        return;
    }
    var tokenUrl = "http://localhost:8080/springapp/admin/generate-token?username=" + encodeURIComponent(username);

    fetch(tokenUrl)
        .then(function(response) {
            if (!response.ok) {
                throw new Error("Lỗi tạo token: " + response.status);
            }
            return response.text();
        })
        .then(function(token) {
            console.log("Token nhận được:", token);
//            var hrUrl = "http://localhost:19335/Auth/SSOLogin?token=" + encodeURIComponent(token);
//            console.log("Đang gọi API HR App: " + hrUrl);
            window.open("http://localhost:19335/Auth/SSOLogin?token=" + token, "_blank");
        })
        .catch(function(error) {
            console.error("Lỗi:", error);
            alert("Không thể đăng nhập vào HR App!");
        });
}
</script>

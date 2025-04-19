<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<tiles:insertDefinition name="login">
    <tiles:putAttribute name="loginbody">
        <form:form method="POST" action="login.html" commandName="user">
            <div class="wrapper">
                <div class="container">
                    <div class="row">
                        <div class="module module-login span4 offset4">
                            <form class="form-vertical">
                                <div class="module-head">
                                    <h3>Sign In</h3>
                                </div>
                                <div class="module-body">
                                    <div class="control-group">
                                        <div class="controls row-fluid">
                                            ${message}
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="controls row-fluid">
                                            <form:input class="span12" path="userName" id="username" maxlength="400" placeholder="Username"/>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="controls row-fluid">
                                            <form:password id="password" cssClass="span12" path="password" placeholder="Password"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="module-foot">
                                    <div class="control-group">
                                        <div class="controls clearfix">
                                            <button type="submit" class="btn btn-primary pull-right">Login</button>
                                            <label class="checkbox">
                                                <input type="checkbox"> Remember me
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div><!--/.wrapper-->
        </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <title>Работа мечты</title>
</head>
<body>
<div class="container">
    <div style="width: 100%; height: 50px">
        <div style="width: 10%; float: left">
            <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Главная</a>
        </div>
        <div style="width: 10%; float: left">
            <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
        </div>
        <div style="width: 10%; float: left">
            <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
        </div>
        <c:if test="${user != null}">
            <c:if test="${user.role == 'HR'}">
                <div style="width: 20%; float: left">
                    <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
                </div>
            </c:if>
            <c:if test="${user.role != 'HR'}">
                <div style="width: 20%; float: left">
                    <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Разместить
                        резюме</a>
                </div>
            </c:if>
            <div style="width: 10%; float: right">
                <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">Выйти</a>
            </div>
            <a style="float: right" class="nav-link" href="<%=request.getContextPath()%>/login.jsp"><c:out
                    value="${user.name}"/></a>
        </c:if>
        <c:if test="${user == null}">
            <div style="float: right">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
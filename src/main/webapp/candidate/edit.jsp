<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.DbStore" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script
            src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous">
    </script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Работа мечты</title>
</head>

<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", "");
    if (id != null) {
        candidate = DbStore.instOf().findByIdCon(Integer.valueOf(id));
    }
%>
<script>
    j = jQuery.noConflict();
    j(document).ready(function () {
        j.ajax({
            type: 'GET',
            crossdomain: true,
            url: 'http://localhost:8080/job4j_dreamjob/cities',
            dataType: 'json'
        }).done(function (data) {
            for (var i in data) {
                $('#select').append(`<option value='${i}'>${data[i]}</option>`)
            }
        }).fail(function (err) {
            console.log(err);
        });
    })
</script>
<div class="container pt-3">
    <jsp:include page="/header.jsp"/>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новый кандидат.
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>" method="post">
                    <div class="form-group">
                        <label>Названние</label>
                        <input type="text" class="form-control" name="name" value="<%=candidate.getName()%>">
                    </div>
                    <div class="form-group">
                        <label>Описание</label>
                        <textarea class="form-control" style="height: 150px" name="description"
                                  value="<%=candidate.getDescription()%>"></textarea>
                    </div>
                    <label>Город проживания:</label>
                    <select class="form-control" id="select" name="city"></select>
                    <br/>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
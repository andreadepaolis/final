
<%@ page import="bean.ProfessorBean" %>
<%@ page import="model.ScheduleInfo" %>
<%@ page import="utils.Toast" %>
<%@ page import="bean.HomeworkBean" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="model.Argument" %>


<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <link rel="stylesheet" href="css/login.css" type="text/css">
    <link rel="stylesheet" href="css/toast.css" type="text/css">
    <link rel="stylesheet" href="css/app.css" type="text/css">
    <link rel="stylesheet" href="css/navbar.css" type="text/css">
    <link rel="stylesheet" href="css/register.css" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Home</title>
</head>
<body style="background-color: #2581b8; color:white">


<%

    //allow access only if session exists
    if (session == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    if (session.getAttribute("professor") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    ProfessorBean p = (ProfessorBean) session.getAttribute("professor");
    String name = p.getName();
    String[] days = {"Lun", "Mar", "Mer", "Gio", "Ven", "Sab"};
    int start = 9;
    String flag = "";
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String today = dateFormat.format(p.getCurrentDate());

%>
<ul class="row col-sm-12" style="padding: 0px 15px">
    <li><a><strong>Prof. <%=p.getLastname()%>
    </strong></a></li>
    <li><a style="border-bottom: 5px white solid;" href="HomeProfessor.jsp">Home</a></li>
    <li><a style="padding:0px">
        <form action="HomeProfessorServlet" method="get">
            <input class="buttonNav" style="padding:14px 16px" name="cmd" type="submit" value="Register">
        </form>
    </a></li>
    <li><a style="padding:0px">
        <form action="LogoutServlet" method="post">
            <input class="buttonLogout" style="padding:14px 16px" type="submit" value="Logout">
        </form>
    </a>
    </li>
</ul>
<div class="container-fluid col-sm-12" style="padding:30px">
    <br>

    <%

    %>
    <!--
    <form action="HomeProfessorServlet" method="post">
        <input type ="hidden" name="cmd" value="reg">
        <input type="submit" value="register">
    </form>
    -->

    <div class="container-fluid col-sm-12 row" style="margin:0px">
        <div class="col-sm-6" align="center" style="border:0px">
            <div class="shadow" style="background-color: #53a8db; border-radius: 5px;color:white;">
                <br>
                <div align="center" class="col-sm-12"><h5>Here below you can view and assign homework for your
                    classes</h5></div>
                <br>
                <div style="padding:0px 15px 15px 15px">
                    <div class="row col-sm-12" style="padding:0px">
                        <form action="HomeProfessorServlet" method="get">
                                <input type="hidden" name="cmd" value="change_matter">
                                <select name="current_matter" onchange='this.form.submit()'>
                                    <option selected disabled hidden> <%=p.getCurrentMatter()%></option>

                                    <% for (String s : p.getMatter()) { %>
                                    <option value="<%=s%>">
                                        <%=s%>
                                    </option>
                                    <% }%>
                                </select>
                                <noscript><input type="submit" value="Submit"/></noscript>

                        </form>
                        <form style="padding:0px 5px" action="HomeProfessorServlet" method="get">
                            <input type="hidden" name="cmd" value="change_class">
                            <select name="current_class" onchange='this.form.submit()'>
                                <option selected disabled hidden><%=p.getCurrentClass()%>
                                </option>
                                <% for (String t : p.getClassi()) { %>
                                <option value="<%=t%>">
                                    <%=t%>
                                </option>
                                <% }%>
                            </select>
                            <noscript><input type="submit" value="Submit"/></noscript>
                        </form>
                        <form style="padding:0px 5px" action="HomeProfessorServlet" method="post">
                            <input class="buttonSave" type="submit" value="<">
                            <input type="hidden" name="cmd" value="hmw">
                            <input type="hidden" name="temp" value="dec">
                        </form>
                        <%=today%>
                        <form style="padding:0px 5px" action="HomeProfessorServlet" method="post">
                            <input class="buttonSave" type="submit" value=">">
                            <input type="hidden" name="cmd" value="hmw">
                            <input type="hidden" name="temp" value="inc">
                        </form>
                        <form style="padding:0px 5px" action="HomeProfessorServlet" method="post">
                            <input class="buttonSave" type="submit" value="today">
                            <input type="hidden" name="cmd" value="hmw">
                            <input type="hidden" name="temp" value="today">
                        </form>
                    </div>
                    <br>
                    <div style="margin:1px; color:black;
                                    padding:3px;
                                    width: 100%;
                                    height: 380px;
                                    overflow-x: hidden;
                                    overflow-x: auto;
                                    text-align:justify;
                                    font-size: 13px;
                                    background-color: white !important;
                                    border-radius: 5px;">

                        <%
                            if (p.getHomework() != null && p.getHomework().size() > 0) {
                                for (HomeworkBean hmw : p.getHomework()) {
                        %>
                        <div style="text-align: left">
                            <form action="HomeProfessorServlet" method="post">
                                <span class="close" align="right"><input
                                        style="background-color: transparent; border:0px" type="submit" value="&times"></span>
                                <input type="hidden" name="cmd" value="deletehmw">
                                <input type="hidden" name="desc" value="<%=hmw.getDescription()%>">
                            </form>
                            <b><%=hmw.getMyclasse()%>
                            </b>, <%=hmw.getData()%>: <br><b><%=hmw.getMateria()%>
                        </b>: <%=hmw.getDescription()%>
                        </div>
                        <hr style="margin:6px">


                        <%
                            }
                        } else {
                        %>
                        No homework available
                        <%}%>
                    </div>
                </div>


                <form action="HomeProfessorServlet" method="post">

                    <div class="row col-sm-12">

                        <div align="center" class="col-sm-12"><h6>Select the correct option and assign an homework</h6>
                        </div>
                        <br><br>
                        <div style="padding:0px 5px">

                            <select style="border:0px" name="materia">
                                <% for (String s : p.getMatter()) { %>
                                <option value="<%=s%>">
                                    <%=s%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                        <div style="padding:0px 5px">
                            <select style="border:0px" name="classe">
                                <% for (String t : p.getClassi()) { %>
                                <option value="<%=t%>">
                                    <%=t%>
                                </option>
                                <% }%>
                            </select>
                        </div>

                    </div>
                    <br>
                    <div class="col-sm-12 row" style="padding: 0px 20px">
                        <input class="col-sm-7" type="text" style="border-radius: 5px; border:0px"
                               placeholder="Insert Homework" name="descrizione">
                        <div class="col-sm-3">
                            <input style="border-radius: 5px; border:0px" type="date" name="data" value="<%=today%>">
                            <input type="hidden" name="cmd" value="newhw">
                        </div>

                    </div>

                    <br>
                    <div align="center"><input class="buttonSave" type="submit" value="Save"></div>
                    <br>
                </form>
            </div>

        </div>
        <div class="col-sm-6" align="center" style="border:0px">
            <div class="shadow" style="background-color: #53a8db; border-radius: 5px;color:white;">
                <br>
                <div align="center" class="col-sm-12"><h5>Schedule</h5></div>
                <br>
                <table style="border: 1px black solid; background-color:white; color:black; width: 95%; font-size: 14px"
                       class="tg">
                    <tr align="center">
                        <th>Orario</th>
                        <%for (int i = 0; i < 6; i++) {%>
                        <th><%=days[i]%>
                        </th>
                        <%}%>
                    </tr>
                    <%for (int j = 0; j < 6; j++) { %>
                    <tr align="center">
                        <td><%=start + j%>:00</td>
                        <%
                            for (int x = 0; x < 6; x++) {
                                for (ScheduleInfo s : p.getSchedule()) {
                                    if (s.getDay() == x && s.getHours() == j + start)
                                        flag = s.getMateria().concat(" ").concat(s.getClasse());

                                }
                        %>
                        <td><%=flag%>
                        </td>
                        <%flag = "";%>
                        <%}%>
                    </tr>
                    <%}%>

                </table>
                <br>
            </div>
            <br>
            <div class="shadow" style="background-color: #53a8db; border-radius: 5px;color:white;" class="col-sm-12">
                <br>
                <div align="center" class="col-sm-12"><h5>Here below you can check the arguments </h5></div>
                <br>

                <div style="padding:0px 15px 15px 15px">

                    <div style="margin:1px; color:black;
                                    padding:3px;
                                    width: 100%;
                                    height: 250px;
                                    overflow-x: hidden;
                                    overflow-x: auto;
                                    text-align:justify;
                                    font-size: 13px;
                                    background-color: white !important;
                                    border-radius: 5px;">
                        <% if (p.getArguments() != null && p.getArguments().size() > 0) {
                            int count = 0;
                            for (Argument arg : p.getArguments()) {
                                count++;
                        %>
                        <form action="HomeProfessorServlet" method="get">
                                <span class="close" align="right"><input
                                        style="background-color: transparent; border:0px" type="submit" value="&times"></span>
                            <input type="hidden" name="cmd" value="deleteArg">
                            <input type="hidden" name="index" value="<%=arg.getIndex()%>">
                        </form>
                        <b>Lezione <%=count%>
                        </b>:<br> <%=arg.getDescprition()%><br>
                        <hr style="margin:8px">

                        <%
                            }
                        } else {
                        %>
                        No arguments available
                        <%}%>

                    </div>
                    <br>
                    <div class="col-sm-12 row" style="padding:0px">

                        <form class="col-sm-12" style="padding:0px" action="HomeProfessorServlet" method="post">

                            <input type="hidden" name="cmd" value="newArg">
                            <input class="col-sm-12"
                                   style="background-color: white !important; border-radius: 5px; border:0px"
                                   type="text" name="description" placeholder="Insert an argument and click enter">

                        </form>

                    </div>
                </div>
            </div>


        </div>
    </div>
</div>

<%
    if (request.getAttribute("toast") != null) {
        Toast t = (Toast) request.getAttribute("toast");
%>


<div id="snackbar"><%=t.getTitle()%><br> <%=t.getMessage()%>
</div>
<script type="text/javascript">
    function ShowToast(value) {
        var x = document.getElementById("snackbar");
        if (value == 1) {
            x.className = "error";
            setTimeout(function () {
                x.className = x.className.replace("error", "");
            }, 3000);
        } else {
            x.className = "succ";
            setTimeout(function () {
                x.className = x.className.replace("succ", "");
            }, 3000);
        }


    }
</script>

<script type="text/javascript">ShowToast(<%=t.getType()%>);</script>
<%
        request.setAttribute("toast", null);

    }
%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<br><br>
</body>
</html>

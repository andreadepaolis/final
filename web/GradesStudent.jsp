<%@ page import="bean.StudentBean" %>
<%@ page import="bean.GradesPageBean" %>
<%@ page import="bean.MatterBean" %>
<%@ page import="model.Grades" %><%--
  Created by IntelliJ IDEA.
  User: GianMarcoColagrossi
  Date: 10/02/2020
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <title>Student's Home</title>
    <link rel="stylesheet" href="css/toast.css" type="text/css">
    <link rel="stylesheet" href="css/app.css" type="text/css">
    <link rel="stylesheet" href="css/navbar.css" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body style="background-color: #2581b8; color:white">


<%
    if (session.getAttribute("gradesPage") != null) {

        GradesPageBean gpb = (GradesPageBean) session.getAttribute("gradesPage");
%>

<ul class="col-sm-12" style="padding: 0px 15px">
    <li><a><strong><%=gpb.getStudent().getLastname()%>
    </strong></a></li>
    <li><a href="HomeStudent.jsp">Home</a></li>
    <li><a style="border-bottom: 5px white solid;" href="GradesStudent.jsp">Grades</a></li>
    <li>
        <form action="ParentalControlServlet" method="post">
            <input class="buttonNav" style="padding:14px 16px" name="cmd" type="submit" value="Absences">
        </form>
    </li>
    <li>
        <form action="LogoutServlet" method="post">
            <input class="buttonLogout" style="padding:14px 16px" type="submit" value="Logout">
        </form>
    </li>

</ul>


<div class="container-fluid col-sm-12" style="padding:30px">
    <div class="row col-sm-12 card-deck row container-fluid">


        <div class="shadow card col-sm-2"
             style="background-color: #53a8db; border-radius: 5px;color:white; padding:0px 20px">
            <br>
            <h5><strong>Subjects</strong></h5><br>
            <div class="col-sm-12" style="padding:0px">

                <%
                    for (MatterBean m : gpb.getMatter()) {
                %>
                <form action="GradesStudentServlet" method="post">
                    <input type="hidden" name="materia" value="<%=m.getMateria()%>"/>
                    <input type="hidden" name="cmd" value="materia">
                    <input style="width:100px" class="buttonSave" type="submit" value="<%=m.getMateria()%>">
                </form>
                <%}%>
            </div>

        </div>

        <div class="shadow container-fluid card col-sm-4"
             style="background-color: #53a8db; border-radius: 5px;color:white; padding:0px 20px">
            <br>
            <h5><strong><%=gpb.getCurrentMatter().getMateria()%>
            </strong></h5><br>
            <div style="background-color: white; border: 0px; border-radius: 5px; color:black; padding:20px;
                                    margin:1px;
                                    color:black;
                                    width: 100%;
                                    height: 490px;
                                    overflow-x: hidden;
                                    overflow-x: auto;
                                    text-align:justify;
                                    font-size: 13px;
                                    background-color: white !important;
                                    border-radius: 5px;">
                <!-- for per voti -->
                <%
                    if (gpb.getCurrentMatter().getGradesForMatter().size() > 0) {
                        for (Grades g : gpb.getCurrentMatter().getGradesForMatter()) {
                            if (g.getVoto() < 6) {
                %>
                <strong>Mark:</strong> <strong style="font-size: 22px; color:red"><%=g.getVoto()%>
            </strong><br>
                <%} else {%>
                <strong>Mark:</strong> <strong style="font-size: 22px; color:lawngreen"><%=g.getVoto()%>
            </strong><br>
                <%}%>
                <strong>Type:</strong> <%=g.getTipo()%><br>
                <strong>Date:</strong> <%=g.getData()%><br>
                <hr style="margin:8px">
                <!-- fine for per voti-->
                <%
                    }
                } else {%>
                No grades available
                <%}%>
            </div>
            <br>
        </div>


        <div class="shadow container-fluid card col-sm-6"
             style="background-color: #53a8db; border-radius: 5px;color:white; padding:0px 20px">
            <br>
            <h5><strong>School Report</strong></h5><br>
            <!-- for -->
            <div class="row"
                 style="background-color: white; border: 0px; border-radius: 5px; color:black; padding:20px; margin:0px">
                <%
                    for (MatterBean m : gpb.getMatter()) {

                %>
                <div style="width:100%; padding:0px 20px 20px 20px">
                    <strong><%=m.getMateria()%>
                        <% if (m.getMedia() < 6) {%>

                    </strong>: <strong style="font-size: 22px; float:right; color:red"><%=m.getMedia()%>
                    <%} else {%>
                </strong>: <strong style="font-size: 22px; float:right; color:lawngreen"><%=m.getMedia()%>
                    <%}%>
                </strong>
                    <hr style="margin:8px 0px">
                </div>
                <%}%>
            </div>
            <br>
        </div>

    </div>
</div>
<%}%>

</body>
</html>

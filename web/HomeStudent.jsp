<%@ page import="bean.StudentBean" %>
<%@ page import="model.Homework" %>
<%@ page import="model.ScheduleInfo" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
  Created by IntelliJ IDEA.
  User: andrea
  Date: 06/02/2020
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student's Home</title>
    <link rel="stylesheet" href="css/toast.css" type="text/css">
    <link rel="stylesheet" href="css/app.css" type="text/css">
    <link rel="stylesheet" href="css/navbar.css" type="text/css">
    <link rel="stylesheet" href="css/studentHome.css" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body style="background-color: #2581b8; color:white">
<%
    if (session.getAttribute("student") != null) {

        StudentBean s = (StudentBean) session.getAttribute("student");
        String[] days = {"Lun", "Mar", "Mer", "Gio", "Ven", "Sab"};
        int start = 9;
        String flag = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String d = formatter.format(s.getCurrentDate());


%>
<ul class="col-sm-12" style="padding: 0px 15px">
    <li><a><strong><%=s.getLastname()%></strong></a></li>
    <li><a style="border-bottom: 5px white solid;" href="HomeStudent.jsp">Home</a></li>
    <li>
        <form action="HomeStudentServlet" method="post">
            <input class="buttonNav" style="padding:14px 16px" name="cmd" type="submit" value="Grades">
        </form>
    </li>
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

<div class="container-fluid col-sm-12 card-deck row" style="padding: 20px; margin:0px">
    <br><br>
    <div class="shadow card col-sm-6"
         style="background-color: #53a8db; border-radius: 5px;color:white; padding:0px 20px;">
        <br>
        <div class="row">
            <form style="padding:0px 5px" action="HomeStudentServlet" method="post">
                <input class="buttonSave" type="submit" value="<">
                <input type="hidden" name="cmd" value="hmw">
                <input type="hidden" name="temp" value="dec">
            </form>
            <%=d%>
            <form style="padding:0px 5px" action="HomeStudentServlet" method="post">
                <input class="buttonSave" type="submit" value=">">
                <input type="hidden" name="cmd" value="hmw">
                <input type="hidden" name="temp" value="inc">
            </form>


            <form style="padding:0px 5px" action="HomeStudentServlet" method="post">
                <input class="buttonSave" type="submit" value="today">
                <input type="hidden" name="cmd" value="hmw">
                <input type="hidden" name="temp" value="today">
            </form>
        </div>
        <div style="margin:1px; color:black;
    padding:3px;
    width: 100%;
    height: 300px;
    overflow-x: hidden;
    overflow-x: auto;
    text-align:justify;
    font-size: 13px;
    background-color: white !important;
    border-radius: 5px;">
            <%
                if (s.getHomework() != null) {
                    for (Homework hmw : s.getHomework()) {


            %>


            <b><%=hmw.getMatter()%>
            </b>, <b><%=hmw.getData()%> :</b><br> <%=hmw.getDescription()%> <br>
            <hr style="margin:8px">
            <%
                }
            } else {
            %>

            Nessun nuovo compito
            <%}%>
        </div>
        <br>
    </div>
    <br>
    <div class="shadow card col-sm-6"
         style="background-color: #53a8db; border-radius: 5px;color:white; padding:0px 20px">
        <br>
        <div align="center"><h5>Schedule</h5></div>
        <br>
        <table style="border: 1px black solid; background-color:white; color:black; width: 100%; font-size: 14px"
               class="col-sm-12">
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
                        for (ScheduleInfo schedule : s.getSchedule()) {
                            if (schedule.getDay() == x && schedule.getHours() == j + start)
                                flag = schedule.getMateria();

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
        <%
            } else {

                response.sendRedirect("index.jsp");
                return;
            }
        %>
    </div>
</div>
</body>
</html>

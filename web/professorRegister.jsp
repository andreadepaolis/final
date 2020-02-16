<%@ page import="bean.ProfessorBean" %>
<%@ page import="utils.month" %>
<%@ page import="java.util.Date" %>
<%@ page import="register.ProfessorRegister" %>
<%@ page import="utils.MonthFactory" %>
<%@ page import="bean.StudentBean" %>
<%@ page import="model.Grades" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="model.Absences" %>
<%@ page import="utils.Toast" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>


<%--
  Created by IntelliJ IDEA.
  User: andrea
  Date: 02/02/2020
  Time: 09:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
        <link rel="stylesheet" href="css/login.css" type="text/css">
        <link rel="stylesheet" href="css/toast.css" type="text/css">
        <link rel="stylesheet" href="css/app.css" type="text/css">
        <link rel="stylesheet" href="css/modal.css" type="text/css">
        <link rel="stylesheet" href="css/navbar.css" type="text/css">
        <link rel="stylesheet" href="css/register.css" type="text/css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Home</title>



        <script>

            window.onload = function(){

               var tds = document.getElementsByTagName("td");
                console.log(tds.InnerText);

                if(tds.InnerText < 6){
                    tds.classList.add("red_grades");
                }
            }


        </script>
    </head>

    <body style="background-color: #2581b8; color:white">


    <ul class="row col-sm-12">
        <li><a class="buttonNav" href="HomeProfessor.jsp">Home</a></li>
        <li><a class="buttonNav" href="professorRegister.jsp">Register</a></li>

            <a style="padding:0px">
                <form action="LogoutServlet" method="post">
                    <input class="buttonLogout" style="padding:14px 16px"  type="submit" value="Logout">
                </form>
            </a>

    </ul>
    <div class="container-fluid col-sm-12" style="padding:30px">
        <%
            if(session.getAttribute("professor") != null && session.getAttribute("register") != null){


            ProfessorBean p = (ProfessorBean) session.getAttribute("professor");


                ProfessorRegister register = (ProfessorRegister)session.getAttribute("register");
                month m = register.getCurrent_month();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String today = dateFormat.format(new Date());

                MonthFactory mf = new MonthFactory();
                month pr;
                month sx;
                if(m.getIndex() == 1) {
                    pr = mf.createMonth(12,m.getYear()-1);
                } else {
                    pr = mf.createMonth(m.getIndex()-1,m.getYear());
                }
                if(m.getIndex() == 12){
                    sx = mf.createMonth(1,m.getYear()+1);
                } else {
                    sx = mf.createMonth(m.getIndex()+1,m.getYear());
                }
                String materia = register.getCurrent_matter();

        %>


        <div align="center" class="col-sm-12"><h3>Hi <%=p.getName()%>, welcome in the register.</h3></div>
        <br>


        <div class="shadow" style="background-color: #53a8db; border-radius: 5px;color:white; padding:0px 20px">
<br>
        <div class="container-fluid row" style="margin:0px">
            <form action = "ProfessorRegisterServlet" method="post">
                <select name ="classe"  onchange='this.form.submit()'>
                    <option selected disabled hidden> Class</option>
            <%
                for(String t: p.getClassi()){
                    %>
            <option value ="<%=t%>">
                <%=t%>
            </option>
            <%}%>
            </select>
                <input type="hidden" name ="cmd" value ="classe">
                <noscript><input type="submit" value="Submit"/></noscript>

                &nbsp;&nbsp;</form>

            <form action="ProfessorRegisterServlet" method="post">
                    <select name ="materia"  onchange='this.form.submit()'>
                        <option selected disabled hidden> Materia</option>
                    <% for(String s: p.getMatter()){ %>
                    <option value ="<%=s%>">
                        <%=s%>
                    </option>
                    <% }%>
                    <input type="hidden" name="cmd" value="mat">
                    <input type="hidden" name="month" value ="<%=m.getIndex()%>">
                    <input type ="hidden" name="classe" value ="<%=register.getCurrent_class()%>">
                </select>
                <noscript><input type="submit" value="Submit"/></noscript>
            </form>

            <form style="padding:0px 5px"  action="ProfessorRegisterServlet" method="post">
                <input type="hidden" name="cmd" value="m">
                <input type="hidden" name="monthIndex" value="<%=pr.getIndex()%>">
                <input type="hidden" name="monthYear" value="<%=pr.getYear()%>">
                <input class="buttonSave" type="submit" value ="<">
            </form>

            &nbsp;<%=m.getName()%> <%=m.getYear()%>&nbsp;

            <form style="padding:0px 5px"  action="ProfessorRegisterServlet" method="post">
                <input type="hidden" name="cmd" value="m">
                <input type="hidden" name="monthIndex" value="<%=sx.getIndex()%>">
                <input type="hidden" name="monthYear" value="<%=sx.getYear()%>">
                <input class="buttonSave" type="submit" value =">">
            </form>

            <form style="padding:0px 5px"  action="ProfessorRegisterServlet" method="post">
                <input type="hidden" name="cmd" value="today">
                <input class="buttonSave" type="submit" value="Today">
            </form>
        </div>
<div class="container-fluid col-sm-12 row" style="margin:0px">
        <table id="tableReg" style="margin:0px; width:100%; text-align: center; color:black" class="tableRegister col-sm-10">

            <tr>
                <td style="text-align: left">
                    <b>Students</b>
                </td>

                <%for(int i = 1; i < m.getDay()+1; i++){%>
                <td><b><%= i %></b></td>
                <%}%>
                <td><b>Media</b></td>
            </tr>

            <%

                for(StudentBean u: register.getUsers()){
            %>                  <tr>
            <td style="text-align: left">

                <b><%=u.getLastname()%> <%=u.getName()%></b>
            </td>
                <%for(int i = 1; i < m.getDay()+1; i++){%>

                <%
                  String result = "";
                  if(u.getGrades()!= null){
                  for (Grades g :u.getGrades()) {
                      Calendar cal = Calendar.getInstance();
                        cal.setTime(g.getData());
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            if(day == i){
                                result = String.valueOf(g.getVoto());

                            }
                       }
                  }
                  if(u.getAbsences()!=null){
                      for(Absences a: u.getAbsences()){
                      Calendar cal = Calendar.getInstance();
                        cal.setTime(a.getData());
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            if(day == i){
                                    if(a.getTipo().equals("assenza"))
                                        result = "A";
                                    else
                                         result = "R";
                       }
                    }
                }
                  %>

            <td onclick="test(this)"><%=result%> </td>
                <%

              }
              %>
            <td><%=register.getMedia(u.getMatricola(),materia)%></td>

                <%

}

%>
        </table>
        <div class="col-sm-2" style="background-color: transparent; border: 0px; color:white; padding: 0px 0px 0px 15px" align="center">
            <form action="ProfessorRegisterServlet" method="post">
                <br><br>
                <div align="center" class="col-sm-12"><h5>Extract a random student:</h5></div><br>

                <input type ="submit" name="cmd" class="buttonSave" value="random">
                <%
                    if (request.getAttribute("random_student")!= null) {
                        StudentBean s = (StudentBean) request.getAttribute("random_student");
                %>
                <div><br><%=s.getLastname()%><div>
                        <%}%>
            </form>
        </div>
    </div>
            <br>

        </div>

    <br>
    <div class="container-fluid col-sm-12 row" style="padding: 0px; margin:0px">
    <div class="container-fluid col-sm-6 shadow" style="background-color: #53a8db; border-radius: 5px;color:white;">
        <br>
        <div>
            <br>
            <div align="center" class="col-sm-12"><h5>Assign grade</h5></div><br>

            <form align="center" action="ProfessorRegisterServlet" method="post">
                <input type="hidden" name="cmd" value="ng">
                Student:<br>
                <select name="matricola">
                    <% for(StudentBean st: register.getUsers()){ %>
                    <option value ="<%=st.getMatricola()%>">
                        <%=st.getLastname()%>
                    </option>
                    <% }%>
                </select>
                <br><br>
                Subject:<br>
                <select name ="materia">
                    <% for(String s: p.getMatter()){ %>
                    <option value ="<%=s%>">
                        <%=s%>
                    </option>
                    <% }%>
                </select><br><br>
                Type:<br>
                <select name = "tipo">
                    <option value = "orale">Orale</option>
                    <option value = "scritto">Scritto</option>
                    <option value ="laboratorio">Laboratorio</option>
                </select><br><br>
                Data:<br>
                <input type="date" name="data" value="<%=today%>"><br><br>
                Vote:<br>
                <input style="border-radius: 5px" type="text" name="voto"><br><br>
                <div align="center">
                    <input class="buttonSave" type="submit" value="Confirm">
                </div>
            </form>
            <br>
        </div>
    </div>

    <div class="container-fluid col-sm-6 shadow" style="background-color: #53a8db; border-radius: 5px;color:white;">
        <br>
        <div>
            <br>
            <div align="center" class="col-sm-12"><h5>Assign absence</h5></div><br>

            <form align="center" action="ProfessorRegisterServlet" method="post">
                <input type="hidden" name="cmd" value="newAbsence">
                Student:<br>
                <select name="matricola">
                    <% for(StudentBean std: register.getUsers()){ %>
                    <option value ="<%=std.getMatricola()%>">
                        <%=std.getLastname()%>
                    </option>
                    <% }%>
                </select>
                <br><br>
                </select>
                Type:<br>
                <select name = "tipo">
                    <option value = "ritardo">Ritardo</option>
                    <option value = "assenza">Assenza</option>
                </select><br><br>
                Data:<br>
                <input type="date" name="data" value="<%=today%>">
                <div align="center">
                    <br><br>
                    <input class="buttonSave" type="submit" value="Confirm">
                </div>
            </form>
            <br>
        </div>
    </div>
    </div>
    <form action="ProfessorRegisterServlet" method="post" id="delete">
        <input type="hidden" name="rowIndex">
        <input type="hidden" name="colIndex">
        <input type="hidden" name="type">
        <input type ="hidden" name="cmd" value="delete">
    </form>
    <%}else{

                response.sendRedirect("index.jsp");
                return;
    }%>

    <%
        if(request.getAttribute("toast")!= null ){
            Toast t = (Toast) request.getAttribute("toast");
    %>


    <div id="snackbar"><%=t.getTitle()%><br> <%=t.getMessage()%></div>
    <script type="text/javascript">
        function ShowToast(value) {
            var x = document.getElementById("snackbar");
            if(value == 1){
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
        request.setAttribute("toast",null);

        }
    %>



    <script>
            function highlight_function(x) {

                for( var i = 0; i < x.classList.length; i++){
                  if(x.classList[i] == "highlight") {
                      x.classList.remove("highlight");
                  return;}
                    }
                x.classList.add("highlight");
                console.log(x);
            }


    </script>
    <!-- The Modal -->
    <div id="myModal" class="modal col-sm-12">

        <!-- Modal content -->
        <div class="modal-content col-sm-3">
            <span class="close" align="right">&times;</span>
            <p style="color: black">Are you sure you want to delete it?</p>
            <button class="buttonSave" id="confirm"> Yes </button>
        </div>

    </div>


    <script>
        function test(x){
            var doc = document.getElementById("delete");
            doc.rowIndex.value = x.parentElement.rowIndex;
            doc.colIndex.value = x.cellIndex;
            if(x.innerText == null || x.innerText === " " ||x.innerText === "")
                return;
            if(x.innerText.toString() === "A" || x.innerText.toString() === "R")
                doc.type.value = "assenza";
            else
                doc.type.value = x.innerHTML;

            var modal = document.getElementById("myModal");

            var span = document.getElementsByClassName("close")[0];

            var buttn = document.getElementById("confirm");


                modal.style.display = "block";

            span.onclick = function() {
                modal.style.display = "none";
            };

            window.onclick = function(event) {
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            }

            buttn.onclick = function(){
                 doc.submit();

                modal.style.display = "none";

            }
        }


    </script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </div>
    </div>
    </body>
</html>
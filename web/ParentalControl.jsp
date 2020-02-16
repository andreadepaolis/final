<%@ page import="bean.StudentBean" %>
<%@ page import="model.Absences" %>
<%@ page import="utils.Toast" %><%--
  Created by IntelliJ IDEA.
  User: GianMarcoColagrossi
  Date: 10/02/2020
  Time: 15:55
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
    <link rel="stylesheet" href="css/modal.css" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body style="background-color: #2581b8; color:white">
<ul>
    <li><a href="HomeStudent.jsp">Home</a></li>
    <li>
        <form action="HomeStudentServlet" method="post">
            <input class="buttonNav" style="padding:14px 16px"  name="cmd" type="submit" value="Grades">
        </form>
    </li>
    <li><a href="ParentalControl.jsp">Absences</a></li>
    <li>
        <form action="LogoutServlet" method="post">
            <input class="buttonLogout" style="padding:14px 16px"  type="submit" value="Logout">
        </form>
    </li>

</ul>

<div class="container-fluid col-sm-12" style="padding:30px">

    <%
        if(session.getAttribute("student") != null){

            StudentBean s = (StudentBean) session.getAttribute("student");

            if(s.getAbsences() != null)
                 for(Absences a : s.getAbsences()){

    %>
                <%=a.getData()%> <%=a.getTipo()%>
                        <% if(a.getCheckbit() == 1){%>

                            <button value="Giustifica" onclick=openModal(<%=s.getAbsences().indexOf(a)%>)></button>

                        <%}%><br>
    <%}}%>


    <form action="ParentalControlServlet" method="post" id="giust">
        <input type ="hidden" name="cmd" value="giust">
    </form>

</div>

<div id="myModal" class="modal col-sm-12">

    <!-- Modal content -->
    <div class="modal-content col-sm-3">
        <span class="close" align="right">&times;</span>
        <p style="color: black">Are you sure you want to delete it?</p>
        <input type="text" value="" id="pin"/>
        <button class="buttonSave" id="confirm"> Yes </button>
    </div>

</div>
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

<script type="text/javascript">

            function openModal(index){


                var doc = document.getElementById("giust");

                var modal = document.getElementById("myModal");

                var span = document.getElementsByClassName("close")[0];

                var buttn = document.getElementById("confirm");

                var pintext = document.getElementById("pin");

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

                    var temp = pintext.value;
                    var element1 = document.createElement("input");
                    element1.type = "hidden";
                    element1.value = temp;
                    element1.name = "pinvalue";
                    doc.appendChild(element1);
                    var element2 = document.createElement("input");
                    element2.type = "hidden";
                    element2.value = index;
                    element2.name = "index";
                    doc.appendChild(element2);
                    doc.submit();

                    modal.style.display = "none";

                }


            }

</script>


</body>


</html>

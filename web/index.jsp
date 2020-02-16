<%@ page import="utils.Toast" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <link rel="stylesheet" href="css/login.css" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/toast.css" type="text/css">
    <link rel="stylesheet" href="css/app.css" type="text/css">
    <title>Login Page</title>
</head>
<body style="height: 100vh; background-color: #2581b8; color: white">
<div class="col-sm-12 row" style="padding:0px; margin:0px">
    <div align="center" style="padding:80px 0px 0px 0px" class="col-sm-12"><h3>Welcome to Liceo Collodi's Electronic
        Register</h3></div>

    <form class="col-sm-6 card" align="center"
          style="padding:100px 150px; margin:0px; border:0px; background-color: transparent"
          action="LoginServlet?type=user" method="post">

        <div align="center"><h3>Login as student </h3></div>
        <br>

        Matricola:<br> <input class="buttonLogin" style="border-radius: 10px; border: 0px" type="text" name="matricola">
        <br>
        Password:<br> <input class="buttonLogin" style="border-radius: 10px; border: 0px" type="password"
                             name="password">
        <br><br>
        <div align="center"><input class="buttonSave" style="width:140px; color:white; border:0px" type="submit"
                                   value="Login"></div>
    </form>

    <form class="col-sm-6 card" align="center"
          style="padding:200px 150px; margin:0px; border:0px; background-color: transparent"
          action="LoginServlet?type=professor" method="post">
        <div align="center"><h3>Login as professor</h3></div>
        <br>
        Matricola: <br> <input class="buttonLogin" style="border-radius: 10px; border: 0px" type="text"
                               name="matricola">
        <br>
        Password: <br> <input class="buttonLogin" style="border-radius: 10px; border: 0px" type="password"
                              name="password">
        <br><br>
        <div align="center"><input class="buttonSave" style="width:140px; border: 0px; color:white" type="submit"
                                   value="Login"></div>
    </form>

    <%
        if (request.getAttribute("toast") != null) {
            Toast t = (Toast) request.getAttribute("toast");
    %>


    <div id="snackbar"><%=t.getTitle()%><br> <%=t.getMessage()%>
    </div>
    <script type="text/javascript">
        function ShowToast(value) {
            console.log("hello");
            var x = document.getElementById("snackbar");
            x.className = "error";
            setTimeout(function () {
                x.className = x.className.replace("error", "");
            }, 3000);
        }
    </script>

    <script type="text/javascript">ShowToast("1");</script>
    <%
            request.setAttribute("toast", null);

        }
    %>


</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>

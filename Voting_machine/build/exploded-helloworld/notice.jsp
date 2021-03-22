<%@ page import="com.example.appengine.java8.DTO.VoteTime" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Voting period</title>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">

</head>
<body>
    <div style="background-color: bisque;height:90px;width: 700px;border-radius: 30px;">
        <%
            List<VoteTime> voteTimeList =null;
            try {
                voteTimeList = (List<VoteTime>) request.getSession().getAttribute("votingTime");
            }catch (Exception e){
            }
        %>
<h1 style="color:blue;margin-left: 30px">
    Voting booth will open from <%=voteTimeList.get(0).getStartdate()%>.
</h1>
</div>
    <h2 style="color:blue;margin-left: 30px">
        Please register yourself to cast vote ontime. Thank you.
        <br>
    </h2>

    <div style="color:blue;margin-left: 30px" class="col-1" align="center">
        <a href="/" class="btn btn-danger">Home</a>
    </div>
</body>
</html>

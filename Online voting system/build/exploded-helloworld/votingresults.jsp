<%@ page import="java.util.HashMap" %>
<%@ page import="com.example.appengine.java8.DTO.Candidates" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Voting Result</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
</head>
<body>
<h1 style="color: red;margin-left: 600px;">Election Result</h1>
<%
    HashMap<String,Integer> votestats=new HashMap<>();
    votestats= (HashMap<String, Integer>) request.getAttribute("votestats");
    Float votepercentage=(Float)request.getAttribute("percentagevoter");
%>
<div>
    <h2 style="color: blue;margin-left: 600px;">Summary</h2>
    <table id="tableid" class="table table-responsive" border="1">
        <tr>
            <th>Total Voters</th>
            <th>Vote Casted</th>
            <th>Participation (%)</th>
            <th>Not Participated (%)</th>
        </tr>
        <tbody>
        <td>
            <%= votestats.get("votercount")!=null?votestats.get("votercount"):"0"%>
        </td>
        <td>
            <%= votestats.get("votecasted")!=null?votestats.get("votecasted"):"0"%>
        </td>
        <td>
            <%= votepercentage!=null?votepercentage:"0"%>
        </td>
        <td>
            <% if (votestats.get("votercount") <= 0) { %>
            <%= 0.0 %>
            <%} else { %>
            <%= votepercentage!=null?(100-votepercentage):"0"%>
            <% }%>
        </td>
        </tbody>
    </table>

    <%
        List<Candidates> candidatesList =null;
        try {
            candidatesList = (List<Candidates>) request.getSession().getAttribute("candidates");
        }catch (Exception e){
        }
    %>

    <h2 style="color: blue;margin-left: 600px;">Candidate Information</h2>
    <table id="tableid" class="table table-responsive" border="1">

        <tr>
            <th>First Name</th>
            <th>Sur Name</th>
            <th>Faculty</th>
            <th >number of votes </th>
        </tr>


        <tbody>
        <%
            if (candidatesList != null) {
                for (Candidates candidate : candidatesList) {
        %>
        <tr class="keydata" data-id="<%=candidate.getKey()!=null?candidate.getKey().getId():""%>">

            <td>
                <%= candidate.getFirstName()!=null?candidate.getFirstName():""%>
            </td>

            <td>
                <%= candidate.getSurName()!=null?candidate.getSurName():""%>
            </td>


            <td>
                <%= candidate.getFaculty()!=null?candidate.getFaculty():""%>
            </td>
            <td>
                <%= candidate.getEarnedVote()!=null?candidate.getEarnedVote():""%>
            </td>

        </tr>
        <% }
        }%>
        </tbody>
    </table>
    <div style="color:blue;margin-left: 30px" class="col-1" align="center">
        <a href="/" class="btn btn-danger">Home</a>
    </div>
</div>
</body>
</html>

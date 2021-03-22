<%@ page import="com.example.appengine.java8.DTO.Candidates" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Votingwindow</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">


</head>
<body>
<marquee><h1 style="color: red">Voting Booth. Select your candidate and submit your vote carefully</h1></marquee>

<div style="width:900px">
    <%
            List<Candidates> candidatesList =null;
            try {
                candidatesList = (List<Candidates>) request.getSession().getAttribute("candidates");
            }catch (Exception e){
            }
        %>
        <form id="votting_form" class="table table-responsive" action = "/public/votecastingbooth" method="post">
        <table id="table_id" align="center" border="3">

            <tr border="3" >
                <th style="color:green">Select your candidate here</th>
                <th style="color:green">Candidate Name and info</th>
                <th style="color:green">Faculty of the candidate</th>
            </tr>
            <tbody>
                <%
                if (candidatesList != null) {
                    for (Candidates candidate : candidatesList) {
                %>
            <tr style="height: 60px" class="keydata" data-id="<%=candidate.getKey()!=null?candidate.getKey().getId():""%>">
                <td>
                    <div class="radio">
                        <input type="radio" style="width:30px; height:20px; left:30px;bottom:-15px;margin-left: 90px" class=" form-control plaintext candidateSelection" name="candidateSelection"
                               value="<%= candidate.getKey()!=null?candidate.getKey().getId():""  %>" required >
                    </div>
                </td>

                <td>
                    <div class="col-xs">
                        <label>
                            <input type="text" readonly class="row-values form-control plaintext candidateName" name="candidateName"
                                   value="<%= candidate.getFirstName() !=null?candidate.getFirstName()+" "+ candidate.getSurName():"" %>">
                        </label>
                    </div>
                </td>

                <td>
                    <div class="col-xs">
                        <label>
                            <input type="text" readonly class="row-values form-control plaintext faculty" name="faculty"
                                   value="<%= candidate.getFaculty()!=null?candidate.getFaculty():""%>">
                        </label>
                    </div>
                </td>
            </tr>
                <% }
            }%>
           
        </table><br>

            <div align="center" >
                <h3>Voter token</h3>
                <input type="text"  id="token" style="border-radius:50px;"  border:7; required name="token" >
            </div><br>
            <p align="center" >
               <button style="border-radius:50px"  > <input type="submit" value="submit my vote now!!!" style="height: 60px; width: 300px;background-color: wheat;border-radius:70px" ></input></button>
            </p>
        </form>

        <p>&nbsp;</p>
    <div style="color:blue;margin-left: 30px" class="col-1" align="center">
        <a href="/" class="btn btn-danger">Home</a>
    </div>
    </div>


<script type="application/javascript">
    $("#voteform").submit(function(e) {

        var form = $(this);
        var url = form.attr('action');

        $.ajax({
            type: "POST",
            url: url,
            data: form.serialize(), // serializes the form's elements.
            success: function(data)
            {
                if(data=="true")
                    window.location="/submission.jsp";
                else
                    alert(data);
            }
        });

        e.preventDefault();
    });
</script>

</body>
</html>

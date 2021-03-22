<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.appengine.java8.DTO.VoteTime" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vote Time Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
</head>
<body>
<h1 style="color: red;margin-left: 600px;">Vote Time Management</h1>


<%

    List<VoteTime> voteTime = null;
    try {
        voteTime = (List<VoteTime>) request.getSession().getAttribute("voteTimes");
    }catch (Exception e){
    }
%>
<%
    if (voteTime.isEmpty()){
%>
<p>
    <button href="/admin/votingtime" class="add-new btn btn-primary">Add Vote time</button>
</p>
<%
    }
%>
<form action="/admin/votingtime" id="votetime" method="POST" class="formvalidation">
    <table id="tableid" class="table table-responsive" border="1">
        <tr>
            <th>Start Date</th>
            <th>End Date</th>
            <th colspan="2">Actions</th>
        </tr>
        <tbody>
        <%
            if (voteTime != null) {
                for (VoteTime votetime : voteTime) {
        %>
        <tr  class="keydata" data-id="<%=votetime.getKey()!=null?votetime.getKey().getId():""%>">
            <td style="display:none;">
                <input id="id" value="<%=votetime.getKey()!=null?votetime.getKey().getId():""%>">
            </td>
            <%
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startdate="";
                if(votetime.getStartdate()!=null){
                    startdate= simpleDateFormat.format(votetime.getStartdate());
                }
            %>
            <td>
                <div class="form-group">
                    <div id="startdate3" class="col-xs-12 input-group date form_datetime" data-date="1979-09-16T05:25:07Z"
                         data-date-format="dd MM yyyy - HH:ii p" data-link-field="dtp_input1">
                        <input type="text" onkeydown="return false" required class="form_datetime form-control" <%=voteTime!=null ?"readonly":""%>
                               aria-describedby="startdateHelp" name="startdate" value=<%=voteTime!=null ?"'"+startdate+"'":""%>>

                    </div>
                </div>
            </td>
            <%
                SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String enddate="";
                if(votetime.getEnddate()!=null){
                    enddate= simpleDateFormat.format(votetime.getEnddate());
                }
            %>
            <td>
                <div class="form-group">
                    <div id="enddate3" class="col-xs-12 input-group date form_datetime" data-date="1979-09-16T05:25:07Z"
                         data-date-format="dd MM yyyy - HH:ii p" data-link-field="dtp_input2">
                        <input type="text" onkeydown="return false" required class="form_datetime form-control" <%=voteTime!=null ?"readonly":""%>
                                aria-describedby="enddateHelp" name="enddate" value=<%=voteTime!=null ?"'"+enddate+"'":""%>>

                    </div>
                </div>
            </td>
            <td class="editCan">
                <button type="button" <%=voteTime!=null ? "hidden" : ""%> class="edit btn btn-primary">Edit</button>
            </td>
            
        </tr>
        <% }
        }%>
        <tr class="newitem" style="display: none">
            <td>
                <div class="form-group">
                    <div id="startdate2" class="col-xs-12 input-group date form_datetime" data-date="1979-09-16T05:25:07Z"
                         data-date-format="dd MM yyyy - HH:ii p" data-link-field="dtp_input3">
                        <input type="text" onkeydown="return false" required class="form_datetime form-control"
                                aria-describedby="startdateHelp" name="startdate1" >

                    </div>
                </div>
            </td>
            <td>
                <div id="enddate2" class="col-xs-12 input-group date form_datetime" data-date="1979-09-16T05:25:07Z"
                     data-date-format="dd MM yyyy - HH:ii p" data-link-field="dtp_input4">
                    <input type="text" onkeydown="return false" required class="form_datetime form-control"
                            aria-describedby="enddateHelp" name="enddate1" >

                </div>
            </td>

            <td>
                <button type="button" class="button btn btn-primary submit saveCandidate">Create</button>
            </td>

            <td>
                <button type="button" class="button btn btn-primary newitemcancel cancel">Cancel</button>
            </td>
        </tr>
        </tbody>
    </table>
    <button type="button" align="center" class="btn btn-danger cancel <%=voteTime!=null ?"hidden":""%>">Cancel</button>
    <button type="button" align="center" class="btn btn-primary save updatecandidate <%=voteTime!=null ?"hidden":""%>">Update</button>
</form>
<div style="color:blue;margin-left: 30px" class="col-1" align="center">
    <a href="/" class="btn btn-danger">Home</a>
</div>
</body>
</html>
<script type="application/javascript">
    $(".add-new").click(function () {
        $(this).attr("disabled", "disabled");
        $(".newitem").show();
        $(".newitem").children("td").children().removeClass("hidden");
        $(".newitem").children("td").children(".form-control").attr("required", "required");
    });
    $(".cancel").click(function () {
        location.reload();
    });
    $(".edit").click(function () {
        $("input").removeAttr("readonly");
        $(".save").removeClass("hidden");
        $(".cancel").removeClass("hidden");
    });

    $(".cancel").click(function () {
        $("input").attr("readonly","readonly");
        $(".save").addClass("hidden");
        $(".cancel").addClass("hidden");
    });
    $(".submit").click(function () {

        if (!$(".formvalidation")[0].checkValidity({ignore: ':readonly,:hidden'})) {
            $(".formvalidation")[0].reportValidity();
        }
        else {
            if ($(this).hasClass("saveCandidate")) {
                savetime();
            }
            else if ($(this).hasClass("updatecandidate")){
            }
        }

    });
    savetime = function () {
        var startdate = $("#startdate2").find("input").val();
        var enddate = $("#enddate2").find("input").val();
        console.log(startdate);
        console.log(enddate);
        $.ajax
        ({

            url: '/admin/votingtime',
            data: {
                "startdate": startdate, "enddate": enddate,
            },
            type: 'POST',
            success: function (data) {
                location.reload();
            }
        });

    }
    $(".updatecandidate").click(function () {
        var startdate = $("#startdate3").find("input").val();
        var enddate = $("#enddate3").find("input").val();
        var table=$("table");
        var id = table.find('tr td:eq(0) input').val();
        console.log(startdate);
        console.log(enddate);
        console.log(id);
        $.ajax
        ({
            url: '/admin/votingtime',
            data: {
                "id":id, "startdate": startdate, "enddate": enddate,
            },
            type: 'POST',
            success: function (data) {
                location.reload();
            }
        });

    });
    var setdate = new Date();
    setdate.setDate(setdate.getDate());
    $('.form_datetime').datetimepicker({
        format : "YYYY-MM-DD HH:mm:ss",
        minDate: setdate
    });
    $("#timeform").submit(function(e) {
        var form = $(this);
        var url = form.attr('action');
        var sdate= startdate.value();
        var edate= enddate.value();
        e.preventDefault();
    });
</script>

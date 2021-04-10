<%@ page import="com.example.appengine.java8.DTO.Candidates" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 1/26/2020
  Time: 11:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Candidates Management</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<h1 style="color: red;margin-left: 600px;">Candidate Information List</h1>
<div class="container">
    <p>
        <button href="/admin/candidates" class="add-new btn btn-primary">Add Candidate</button>
    </p>

    <p> </p>


    <%
        List<Candidates> candidatesList =null;
        try {
            candidatesList = (List<Candidates>) request.getSession().getAttribute("candidates");
        }catch (Exception e){
        }
    %>
    <form class="formvalidation">
        <table id="tableid" class="table table-responsive" border="1">

            <tr>
                <th>First Name</th>
                <th>Sur Name</th>
                <th>Faculty</th>
                <th colspan="2">Actions</th>
            </tr>


            <tbody>
            <%
                if (candidatesList != null) {
                    for (Candidates candidate : candidatesList) {
            %>
            <tr class="keydata" data-id="<%=candidate.getKey()!=null?candidate.getKey().getId():""%>">

                <td>
                    <div class="col-xs">
                        <input type="text" readonly class="row-values form-control plaintext name"
                               name="name"
                               value="<%= candidate.getFirstName()!=null?candidate.getFirstName():""%>">
                    </div>
                </td>

                <td>
                    <div class="col-xs">
                        <input type="text" readonly class="row-values form-control plaintext surname"
                               name="surname"
                               value="<%= candidate.getSurName()!=null?candidate.getSurName():""%>">
                    </div>
                </td>


                <td>
                    <div class="col-xs">
                        <input type="text" readonly class="row-values form-control plaintext faculty"
                               name="faculty"
                               value="<%= candidate.getFaculty()!=null?candidate.getFaculty():""%>">
                    </div>
                </td>
                <td class="updateCan" style="display: none">
                    <div class="col-xs">
                        <button type="button" class="btn btn-primary updatecandidate submit">
                            Update
                        </button>
                    </div>
                </td>
                <td class="updateCan" style="display: none">
                    <div class="col-xs">
                        <button type="button" id="canceledit" class="btn btn-primary cancel">
                            Cancel
                        </button>
                    </div>
                </td>
                <td class="editCan">
                    <div class="col-xs">
                        <button type="button" class="btn btn-primary editcandidate">Edit</button>
                    </div>
                </td>
                <td class="deletecan">
                    <div class="col-xs">
                        <button type="button" class="btn btn-danger deletecandidate">Delete</button>
                    </div>
                </td>
            </tr>
            <% }
            }%>
            <tr class="newitem" style="display: none">
                <td>
                    <input type="text" class="row-values form-control plaintext name hidden" name="name">
                </td>
                <td>
                    <input type="text" class="row-values form-control plaintext surname hidden"
                           name="surname">
                </td>
                <td>
                    <input type="text" class="row-values form-control plaintext faculty hidden"
                           name="faculty">
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
    </form>
    <div style="color:blue;margin-left: 30px" class="col-1" align="center">
        <a href="/" class="btn btn-danger">Home</a>
    </div>
</div>

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
    $(".submit").click(function () {

        if (!$(".formvalidation")[0].checkValidity({ignore: ':readonly,:hidden'})) {
            $(".formvalidation")[0].reportValidity();
        }
        else {
            if ($(this).hasClass("saveCandidate")) {
                saveCandidate();
            }
            else if ($(this).hasClass("updatecandidate")) {
                var tr = $(this).closest('tr');
                updateCandidateFunc(tr);
            }
        }

    });
    saveCandidate = function () {
        var firstname = $(".newitem").children("td").children(".name").val();
        var surname = $(".newitem").children("td").children(".surname").val();
        var faculty = $(".newitem").children("td").children(".faculty").val();

        $.ajax
        ({
            url: '/admin/candidates',
            data: {
                "firstname": firstname, "surname": surname,
                "faculty": faculty
            },
            type: 'POST',
            success: function (data) {
                location.reload();
            }
        });

    }


    updateCandidateFunc = function (sRow) {
        try {
            var tr = sRow;
            var elem = tr.children("td").children("div");
            var id = tr.data("id");
            var firstname = elem.children(".name").val();
            var surname = elem.children(".surname").val();
            var faculty = elem.children(".faculty").val();

            $.ajax
            ({
                url: '/admin/candidates',
                data: {
                    "id":id, "firstname": firstname, "surname": surname,
                    "faculty": faculty
                },
                type: 'POST',
                success: function (data) {
                    location.reload();
                }
            });
        } catch (err) {
        }
    }


    $(".editcandidate").click(function () {
        var tr = $(this).closest('tr');
        var elem = tr.children("td").children("div");
        tr.children('.editCan').hide();
        tr.children('.deletecan').hide();
        tr.children('.updateCan').show();
        elem.children("input").removeAttr("readonly");
        elem.children("input").attr("required", "required");
        elem.children().removeAttr("disabled");

    });


    $(".deletecandidate").click(function () {
        var x = confirm("Are you sure you want to delete this candidate?");
        if (x) {
            var tr = $(this).closest('tr');
            var id = tr.data("id");
            $.ajax
            ({
                url: '/admin/candidates?id='+ id,
                contentType: "application/json; charset=utf-8",
                type: 'DELETE',
                success: function (data) {
                    location.reload();
                }
            });
        }
    });
</script>
</body>
</html>

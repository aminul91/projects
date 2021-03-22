<%@ page import="com.example.appengine.java8.DTO.Voter" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Voter Info</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">


</head>
<body>
    <h1 style="color: red;margin-left: 600px;">Voter Information List</h1>
    

<div style="width: 1000px;margin-left: 200px;">
    <p><br>

        <button style="background-color: bisque;" class="addNewvoter">ADD NEW VOTER</button><br><br>
        <button style="background-color: bisque;" class="voterNotification">VOTER NOTIFICATION</button><br><br>
        <button style="background-color: bisque;" id="uploadDir" >IMPORT VOTER LIST</button>
    </p>
    <form id="fileUpload"  action="/admin/uploadfile" method="post" enctype="multipart/form-data">
        <div class="custom-file">
            <input type="file" style="background-color: bisque;" class="custom-file-input" id="file_id" name="myFile"><br>
            <input type="submit" style="background-color: bisque;" style="display:inline;"  value="Upload voter list file">
           
        </div>

    </form>
    <p>&nbsp;</p>


    <%
        List<Voter> voterList=null;
        voterList = (List<Voter>) request.getAttribute("voterList");
    %>
    <form class="check">
        <table id="tableid" class="table table-responsive" border="3" >

            <tr>
                <th style="color: blueviolet;">Name</th>
                <th style="color: blueviolet;">Email</th>
                <th colspan="2" style="color: blueviolet;">Actions</th>
            </tr>


            <tbody>
            <%
                if (voterList != null) {
                    for (Voter voter : voterList) {
            %>
            <tr data-id="<%= voter.getKey()!=null?voter.getKey().getId():""%>">


                <td>
                    <div class="col-xs">
                        <input type="text" readonly class="row-values form-control plaintext name"
                               name="name"
                               value="<%= voter.getName()!=null?voter.getName():""%>">
                        <input type="text" style="display: none" class="token hidden" name="token"
                               value="<%= voter.getToken()!=null?voter.getToken():""%>"
                        >
                    </div>
                </td>

                <td>
                    <div class="col-xs">
                        <input type="text" readonly class="row-values form-control plaintext email"
                               name="email"
                               value="<%= voter.getEmail()!=null?voter.getEmail():""%>">
                    </div>
                </td>
               
                <td class="updateVot" style="display: none">
                    <div class="col-xs">
                        <button type="button" class=" voterInfoupdate submit">
                            UPDATE VOTER
                        </button>
                    </div>
                </td>
                <td class="updateVot" style="display: none">
                    <div class="col-xs">
                        <button type="button" id="canceledit" class="cancel">
                            CANCEL
                        </button>
                    </div>
                </td>
                <td class="voterEdit">
                    <div class="col-xs">
                        <button type="button" class="  editVoterinfo">EDIT
                        </button>
                    </div>
                </td>
                <td class="voterdel">
                    <div class="col-xs">
                        <button type="button" class="voterDelete">DELETE
                        </button>
                    </div>
                </td>
            </tr>
            <% }
            }%>
            <tr class="voterinfo" style="display: none">
                <td>
                    <input type="text" class="row-values form-control plaintext name hidden" name="name">
                </td>
                <td>
                    <input type="text" class="row-values form-control plaintext email hidden"
                           name="email">
                </td>

        
       
                <td>
                    <button type="button" class=" submit voterInfosave">Create</button>
                </td>

                <td>
                    <button type="button" class=" voterinfo cancel">Cancel</button>
                </td>
            </tr>


            </tbody>
        </table>
    </form>

    <div align="center"><button style="background-color: bisque;"><a style="text-decoration: none;color: black;" href="/">Home</a></button>


</div>


<script type="application/javascript">
    $(".addNewvoter").click(function () {
        $(this).attr("disabled", "disabled");
        $(".voterinfo").show();
        $(".voterinfo").children("td").children().removeClass("hidden");
        $(".voterinfo").children("td").children(".form-control").attr("required", "required");
    });


    $(".cancel").click(function () {
        location.reload();
    });
    $(".submit").click(function () {

        if (!$(".check")[0].checkValidity({ignore: ':readonly,:hidden'})) {
            $(".check")[0].reportValidity();
        }
        else {
            if ($(this).hasClass("voterInfosave")) {
                voterInfosave();
            }
            else if ($(this).hasClass("voterInfoupdate")) {
                var tr = $(this).closest('tr');
                voterupdate(tr);
            }
        }

    });
    voterInfosave = function () {
        var name = $(".voterinfo").children("td").children(".name").val();
        var email = $(".voterinfo").children("td").children(".email").val();

        $.ajax
        ({
            url: '/admin/voterlist',// Voter list url here
            data: {
                "name": name, "email": email
            },
            type: 'POST',
            success: function (data) {
                if(data.length==0)
                location.reload();
                else
                    alert(data);
            }
        });

    }


    voterupdate = function (objinfo) {
        try {
            var tr = objinfo;
            var elem = tr.children("td").children("div");
            var id = tr.data("id");
            var name = elem.children(".name").val();
            var email = elem.children(".email").val();
            var emailSent = elem.children(".emailsent").val();
            var reminder = elem.children(".reminder").val();
            var token = elem.children(".token").val();

            $.ajax
            ({
                url: '/admin/voterlist',// Voter list url here
                data: {
                    "id":id,"name": name, "email": email,"emailsent":emailSent,"reminder":reminder,"token":token
                },
                type: 'put',
                success: function (data) {
                    if(data=="true")
                    location.reload();
                    else
                        alert(data);
                    console.log(data);
                }
            });
        } catch (err) {
        }
    }


    $(".editVoterinfo").click(function () {
        var tr = $(this).closest('tr');
        var elem = tr.children("td").children("div");
        tr.children('.voterEdit').hide();
        tr.children('.voterdel').hide();
        tr.children('.updateVot').show();
        elem.children(".name,.email").removeAttr("readonly");
        elem.children(".name,.email").attr("required", "required");
        elem.children(".name,.email").removeAttr("disabled");

    });

    $(".voterNotification").click(function () {
        // alert("Notification has been sent to voters!!!!!!!");
        $.ajax
        ({
            url: '/admin/emailSender',// Voter list url here
            type: 'POST',
            success:function (data) {
                alert("EMAIL has been sent successfully");
                location.reload();
            }
        });
    });
 
    $(".voterDelete").click(function () {
        var x = confirm("Are you sure you want to delete this Voter?");
        if (x) {
            var tr = $(this).closest('tr');
            var id = tr.data("id");
            $.ajax
            ({
                url: '/admin/voterlist?id='+ id,// Voter list with ID here
                contentType: "application/json; charset=utf-8",
                type: 'DELETE',
                success: function (data) {
                    if(data=="true")
                   location.reload();
                    else
                        alert("Sorry this voter can not be deleted");
                }
            });
        }
    });

    $(document).ready(function () {
        $("#fileUpload").hide();
    });

    $("#uploadDir").click(function () {
        $("#fileUpload").show();
    });
</script>
</body>
</html>

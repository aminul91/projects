<!DOCTYPE html>
<!-- [START_EXCLUDE] -->
<%--
  ~ Copyright 2017 Google Inc.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License"); you
  ~ may not use this file except in compliance with the License. You may
  ~ obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
  ~ implied. See the License for the specific language governing
  ~ permissions and limitations under the License.
  --%>
<!-- [END_EXCLUDE] -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.appengine.java8.HelloAppEngine" %>
<html>
<head>
  <link href='//fonts.googleapis.com/css?family=Marmelad' rel='stylesheet' type='text/css'>
  <title>VOTE NOW</title>

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div  align="center">
 <marquee scrollamount="10"><h1 align="center" style="color:red">Welcome to the Electronic Voting System</h1></marquee> 
  <div>&nbsp;</div>
  <div>&nbsp;</div>
 <table>
   <tr>
   <td style="width:280px;height:80px" >
  <div >
    
      <a href="/admin/votingtime"><div style="width:250px;height:80px;background-color: bisque" align="center"><h2>Set Date and Time for voting</h2></div></a>
    

  </div>
</td>
  <div >
    <div>&nbsp;</div>
  </div>
  <td style="width:280px;height:80px">
      <div >
    
          <a href="/admin/voterlist"><div style="width:250px;height:80px;background-color: bisque" align="center"><h2>Manage Voter</h2></div></a>
        
    
      </div>
  
  <td style="width:280px;height:80px">
      <div >
    
          <a href="/admin/candidates"><div style="width:250px;height:80px;background-color: bisque" align="center"><h2>Manage Candidate</h2></div></a>
        
    
      </div>
</td>
  <div >
    <div>&nbsp;</div>
  </div>
    <td style="width:280px;height:80px">
        <div >
    
            <a href="/public/votecastingbooth"><div style="width:250px;height:80px;background-color: bisque" align="center"><h2>Voting Booth</h2></div></a>
          
      
        </div>
  </td>
  </tr>
</table>
  <div >
    <div>&nbsp;</div>
  </div>

</div>


</body>
</html>

package com.example.appengine.java8.Servlet;

import com.example.appengine.java8.DTO.VoteTime;
import com.example.appengine.java8.DTO.Voter;
import com.example.appengine.java8.Entity.VoteEntity;
import com.example.appengine.java8.Entity.VoteTimeEntity;
import com.example.appengine.java8.Management.VoteManagement;
import com.example.appengine.java8.Management.VoteTimeManagement;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EmailSender", value = "/admin/emailSender")
public class EmailServlet extends HttpServlet {

    private VoteManagement voterManaging = new VoteManagement();
    private VoteEntity voteEntity = new VoteEntity();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserService userService = UserServiceFactory.getUserService();
        String thisUrl = req.getRequestURI();
        if(req.getUserPrincipal() == null){
            userService.createLoginURL(thisUrl);
        }

        String votingBoothUrl = getBaseUrl(req);

        Query query = new Query(voteEntity.getVoterKind());
        List<Voter> voterList = voterManaging.get(query);

        sendReminderMail(voterList, votingBoothUrl);
    }

    private void sendReminderMail(List<Voter> voterList, String votingBoothUrl) {

        VoteTimeEntity voteTimeEntity = new VoteTimeEntity();
        Query query = new Query(voteTimeEntity.getVoteTimeKind());
        VoteTimeManagement voteTimeManagement = new VoteTimeManagement();
        List<VoteTime> voteTimes = voteTimeManagement.get(query);
        if(!voteTimes.isEmpty())
        {
            Date scheduledStartTime = voteTimes.get(voteTimes.size()-1).getStartdate();
            long scheduledStartTimeInMilli = scheduledStartTime.getTime();
            long currentTime = Calendar.getInstance().getTimeInMillis();

            if(currentTime < scheduledStartTimeInMilli && (scheduledStartTimeInMilli - currentTime) < 86400000){

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strDateTime = dateFormat.format(scheduledStartTime);

                Properties props = new Properties();
                Session session = Session.getDefaultInstance(props, null);

                try {
                    Message msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress("alnoman.cse@gmail.com", "Abdullah Al Noman"));
                    msg.setSubject("Reminder for voting");

                    for(Voter voter : voterList) {
                        msg.addRecipient(Message.RecipientType.TO,
                                new InternetAddress(voter.getEmail(), voter.getName()));
                    }
                    String message = "<h2>Dear voter, greetings from group 16!</h2><br>";
                    message += "<h3>Wish you a nice day!</h3><br>";
                    message += "<p>We wanted to remind you that your voting will start at: "+strDateTime+"</p></br></br></br></br>";
                    message += "<p>To cast your vote please go to <a href='"+votingBoothUrl+"/public/votecastingbooth' target='_blank'>Voting booth</a></p>";


                    msg.setContent(message, "text/html");
                    Transport.send(msg);

                } catch (AddressException e) {
                    // ...
                } catch (MessagingException e) {
                    // ...
                } catch (UnsupportedEncodingException e) {
                    // ...
                }
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String votingBoothUrl = getBaseUrl(req);

        Query query = new Query(voteEntity.getVoterKind());
        List<Voter> voterList = voterManaging.get(query);

        sendVotingInfoMail(voterList, votingBoothUrl);
    }

    private void sendVotingInfoMail(List<Voter> voterList, String votingBoothUrl) {

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            for(Voter voter : voterList){
//                System.out.println(voter.getName()+" "+voter.getEmail()+" "+voter.getToken());
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("alnoman.cse@gmail.com", "Abdullah Al Noman"));
                msg.setSubject("Your voter information");

                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(voter.getEmail(), voter.getName()));

                String message = "<h2>Greetings "+voter.getName()+"!</h2><br>";
                message += "<h3>Wish you a nice day!</h3><br>";
                message += "<p>Your voter token: <u>"+voter.getToken()+"</u></p></br></br></br></br>";
                message += "<p>To cast your vote please go to <a href='"+votingBoothUrl+"/public/votecastingbooth' target='_blank'>Voting booth</a></p>";


                msg.setContent(message, "text/html");
                Transport.send(msg);
            }
        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        } catch (UnsupportedEncodingException e) {
            // ...
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }
}
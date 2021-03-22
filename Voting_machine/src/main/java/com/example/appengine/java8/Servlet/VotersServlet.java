package com.example.appengine.java8.Servlet;

import com.example.appengine.java8.DTO.Voter;
import com.example.appengine.java8.Entity.VoteEntity;
import com.example.appengine.java8.Management.VoteManagement;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "VotersServlet", value = "/admin/voterlist")
public class VotersServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger
            (VotersServlet.class.getName());
    private VoteManagement voterManaging = new VoteManagement();
    private VoteEntity voteEntity = new VoteEntity();
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserService userService = UserServiceFactory.getUserService();
        String thisUrl = req.getRequestURI();
        if(req.getUserPrincipal() == null){
            userService.createLoginURL(thisUrl);
        }

        Query query = new Query(voteEntity.getVoterKind());
        List<Voter> voterList = voterManaging.get(query);
        if (voterList != null) {
            req.setAttribute("voterList", voterList);
        }
        req.getRequestDispatcher("/votermanagement.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        VoteManagement voterManaging = new VoteManagement();

        Voter voter = new Voter();
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String token = generateVoterToken();

        voter.setName(name);
        voter.setEmail(email);
        voter.setEmailSent(false);
        voter.setVoted(false);
        voter.setReminder(false);
        voter.setToken(token);
        try {
            voterManaging.create(voter);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

//        test fetch from saved voters
        Query query = new Query(voteEntity.getVoterKind());
        System.out.println(query.toString());
        List<Voter> voterList = voterManaging.get(query);
        System.out.println("fetching voters" + voterList.get(0).getEmail());
        System.out.println(voterList.get(0).getName());
    }

    public String generateVoterToken() {
        byte[] byteSequence = new byte[24];
        secureRandom.nextBytes(byteSequence);
        return base64Encoder.encodeToString(byteSequence);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean res = false;

        Voter voter = new Voter();
//        String token = req.getParameter(Constants.VOTER_TOKEN_PROPERTY);
        Boolean reminder = Boolean.valueOf(req.getParameter(voteEntity.getVOTER_REMINDER_PROPERTY()));
        Boolean emailSent = Boolean.valueOf(req.getParameter(voteEntity.getVOTER_EMAILSENT_PROPERTY()));
        String id = req.getParameter("id");
        String token = req.getParameter("token");
        String name = req.getParameter(voteEntity.getVOTER_NAME_PROPERTY());
        String email = req.getParameter(voteEntity.getVOTER_EMAIL_PROPERTY());

        voter.setName(name);
        voter.setEmail(email);
        voter.setId(Long.valueOf(id));
        voter.setEmailSent(emailSent);
        voter.setReminder(reminder);
        voter.setVoted(false);
        voter.setToken(token);
        try {
            if (voterManaging.update(voter) != null) {
                res = true;
            }
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        resp.getWriter().write(String.valueOf(res));
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        boolean res = true;
        Voter voter = new Voter();
        try {
            String id = req.getParameter("id");
            voter.setId(Long.valueOf(id));
            voterManaging.delete(voter);
            resp.getWriter().write(String.valueOf(res));
        } catch (Exception e) {
            logger.severe("Unable to delete a voter " + e.getMessage());
            resp.getWriter().write(e.getMessage());
        }
    }
}

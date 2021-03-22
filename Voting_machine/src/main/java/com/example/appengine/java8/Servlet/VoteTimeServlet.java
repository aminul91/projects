package com.example.appengine.java8.Servlet;

import com.example.appengine.java8.DTO.Candidates;
import com.example.appengine.java8.DTO.VoteTime;
import com.example.appengine.java8.Entity.VoteTimeEntity;
import com.example.appengine.java8.Management.VoteTimeManagement;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "VoteTime", value = "/admin/votingtime")
public class VoteTimeServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(VoteTimeServlet.class.getName());
    private VoteTime voteTime = new VoteTime();

    private VoteTimeManagement voteTimeManagement = new VoteTimeManagement();
    private VoteTimeEntity voteTimeEntity = new VoteTimeEntity();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserService userService = UserServiceFactory.getUserService();
        String thisUrl = req.getRequestURI();
        if(req.getUserPrincipal() == null){
            userService.createLoginURL(thisUrl);
        }

        VoteTime voteTime =new VoteTime();
        try {
            Query query = new Query(voteTimeEntity.getVoteTimeKind());
            List<VoteTime> voteTimes = voteTimeManagement.get(query);
            if (voteTimes != null) req.getSession().setAttribute("voteTimes", voteTimes);
            req.getRequestDispatcher("/votetimemanagement.jsp").forward(req,resp);
        } catch (Exception e){
            logger.severe("Unable to retrieve candidates " + e.getMessage());
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VoteTime voteTime =new VoteTime();

        try{
            String startdate = req.getParameter("startdate");
            String enddate = req.getParameter("enddate");
            String id= req.getParameter("id");
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            voteTime.setStartdate(simpleDateFormat.parse(startdate));
            voteTime.setEnddate(simpleDateFormat.parse(enddate));
            if(id == null) {
                voteTime.setKey(voteTimeEntity.getVoteTimeKey());
                voteTime = voteTimeManagement.create(voteTime);
            }
            else {
                voteTime.setId(Long.valueOf(id));
                voteTime = voteTimeManagement.update(voteTime);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/votingtime");
        }catch (Exception e){
            logger.severe("Unable to create or update voting time " + e.getMessage());
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

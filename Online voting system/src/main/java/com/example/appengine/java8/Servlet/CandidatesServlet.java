package com.example.appengine.java8.Servlet;

import com.example.appengine.java8.DTO.Candidates;
import com.example.appengine.java8.Entity.CandidatesEntity;
import com.example.appengine.java8.Management.CandidatesManagement;
import com.example.appengine.java8.Service.CandidateManagementService;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "Candidates", value = "/admin/candidates")
public class CandidatesServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(CandidatesServlet.class.getName());

    private CandidateManagementService<Candidates> candidateManagementService = new CandidatesManagement();
    private CandidatesEntity candidatesEntity = new CandidatesEntity();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserService userService = UserServiceFactory.getUserService();
        String thisUrl = req.getRequestURI();
        if(req.getUserPrincipal() == null){
            userService.createLoginURL(thisUrl);
        }

        try {
            Query query = new Query(candidatesEntity.getCandidateKind());
            List<Candidates> candidatesList = candidateManagementService.get(query);
            if (candidatesList != null) req.getSession().setAttribute("candidates", candidatesList);
            req.getRequestDispatcher("/candidatesManagementForm.jsp").forward(req, resp);
        } catch (Exception e){
            logger.severe("Unable to retrieve candidates " + e.getMessage());
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Candidates candidates = new Candidates();
        try {
            String id = req.getParameter("id");
            String firstName = req.getParameter("firstname");
            String surName = req.getParameter("surname");
            String faculty = req.getParameter("faculty");
            System.out.println(id + " " + firstName + " " + " " + surName + " " + faculty);
            candidates.setFirstName(firstName);
            candidates.setSurName(surName);
            candidates.setFaculty(faculty);
            if(id == null) {
                candidates.setKey(candidatesEntity.getCandidateKey());
                candidates = candidateManagementService.create(candidates);
            }
            else {
                candidates.setId(Long.valueOf(id));
                candidates = candidateManagementService.update(candidates);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/candidates");
        } catch (Exception e){
            logger.severe("Unable to create or update a candidate " + e.getMessage());
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Candidates candidates = new Candidates();
        try {
            String id = req.getParameter("id");
            candidates.setId(Long.valueOf(id));
            candidateManagementService.delete(candidates);
        } catch (Exception e){
            logger.severe("Unable to delete a candidate " + e.getMessage());
            resp.getWriter().write(e.getMessage());
        }
    }
}

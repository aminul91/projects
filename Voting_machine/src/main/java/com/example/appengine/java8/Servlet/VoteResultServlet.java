package com.example.appengine.java8.Servlet;

import com.example.appengine.java8.DTO.Candidates;
import com.example.appengine.java8.DTO.VoteTime;
import com.example.appengine.java8.DTO.Voter;
import com.example.appengine.java8.Entity.CandidatesEntity;
import com.example.appengine.java8.Entity.VoteEntity;
import com.example.appengine.java8.Entity.VoteTimeEntity;
import com.example.appengine.java8.Management.CandidatesManagement;
import com.example.appengine.java8.Management.VoteManagement;
import com.example.appengine.java8.Management.VoteTimeManagement;
import com.google.appengine.api.datastore.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "VoteResult", value = "/public/votingresult")
public class VoteResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Integer> votestats = new HashMap<>();

        VoteTimeEntity voteTimeEntity = new VoteTimeEntity();
        VoteTimeManagement voteTimeManagement = new VoteTimeManagement();
        Query query = new Query(voteTimeEntity.getVoteTimeKind());
        List<VoteTime> voteTimes = voteTimeManagement.get(query);

        CandidatesManagement candidatesManagement = new CandidatesManagement();
        CandidatesEntity candidatesEntity = new CandidatesEntity();
        List<Candidates> candidatesList = new ArrayList<>();
        try{
            Query query1 = new Query(candidatesEntity.getCandidateKind()).addSort(candidatesEntity.getCandidateEarnedVoteProperty(), Query.SortDirection.DESCENDING);
            candidatesList = candidatesManagement.get(query1);
            if (candidatesList != null) req.getSession().setAttribute("candidates", candidatesList);
        }catch (Exception e) {
            System.out.println("no candidates found."+e.getMessage());
            e.printStackTrace();
        }


        VoteManagement voteManagement = new VoteManagement();
        List<Voter> voterList = new ArrayList<>();
        VoteEntity voteEntity = new VoteEntity();
        Query query2 = null;
        try {
            query2 = new Query(voteEntity.getVoterKind());
            voterList= voteManagement.get(query2);
        } catch (Exception e) {
            System.out.println("no voters found."+e.getMessage());
            e.printStackTrace();
        }
        Date date = null;
        //Date enddate= null;
        VoteTime voteTime = new VoteTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        date = calendar.getTime();
        for (VoteTime votetime : voteTimes) {
            Date enddate = votetime.getEnddate();
            System.out.println(date);
            System.out.println(enddate);
            if(date.after(enddate)){
                Integer votecasted = voteManagement.getCastedVoterCount();
                Integer pendingvote= voteManagement.getPendingVoterCount();
                Integer votercount= voterList.size();
                if(votercount <= 0){
                    votercount =0;
                }
                votestats.put("votecasted",votecasted);
                votestats.put("pendingvote",pendingvote);
                votestats.put("votercount",votercount);
                float percentagevoter =0;
                if(votercount == 0 || (votecasted == 0 && pendingvote == 0)){
                    percentagevoter = 0;
                }
                else {
                    percentagevoter =  ((float) votecasted / (float) votercount) * 100;
                }
                req.setAttribute("percentagevoter", percentagevoter);
                req.setAttribute("candidatesList",candidatesList);
                req.setAttribute("votestats",votestats);
                req.getRequestDispatcher("/votingresults.jsp").forward(req, resp);
            }
            else {
                req.getRequestDispatcher("/periodnotice.jsp").forward(req, resp);
            }
        }


    }
}

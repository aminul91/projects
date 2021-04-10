package com.example.appengine.java8.Management;

import com.example.appengine.java8.DTO.Candidates;
import com.example.appengine.java8.DTO.Voter;
import com.example.appengine.java8.Entity.VoteEntity;
import com.example.appengine.java8.Service.VotService;
import com.example.appengine.java8.modifiedexceptions.CandidateException;
import com.example.appengine.java8.modifiedexceptions.VotException;
import com.google.appengine.api.datastore.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoteManagement extends AbstractCrudManagement<Voter> implements VotService<Voter> {

    private VoteEntity voteEntity = new VoteEntity();
    private Transaction txn;
    private DatastoreService ds;

    public VoteManagement(){
        ds = DatastoreServiceFactory.getDatastoreService();
    }

    @Override
    protected Entity convertDtoToEntity(Voter voter) throws EntityNotFoundException {
        Entity votingEntity = voter.getId() == null ? new Entity(voteEntity.getVoterKind(), voteEntity.getVoterKey()) :
        DatastoreServiceFactory.getDatastoreService().get(KeyFactory.createKey(voteEntity.getVoterKey(), voteEntity.getVoterKind(), voter.getId()));

        votingEntity.setProperty(voteEntity.getVOTER_NAME_PROPERTY(), voter.getName());
        votingEntity.setProperty(voteEntity.getVOTER_EMAIL_PROPERTY(), voter.getEmail());
        votingEntity.setProperty(voteEntity.getVOTER_EMAILSENT_PROPERTY(), voter.getEmailSent());
        votingEntity.setProperty(voteEntity.getVOTER_ISVOTED_PROPERTY(), voter.getVoted());
        votingEntity.setProperty(voteEntity.getVOTER_REMINDER_PROPERTY(), voter.getReminder());
        votingEntity.setProperty(voteEntity.getVOTER_TOKEN_PROPERTY(), voter.getToken());

        return votingEntity;
    }

    @Override
    protected Voter convertEntityToDto(Entity entity) {
        Voter voter = new Voter();
        voter.setKey(entity.getKey());
        voter.setId(entity.getKey().getId());
        voter.setName((String) entity.getProperty(voteEntity.getVOTER_NAME_PROPERTY()));
        voter.setEmail((String) entity.getProperty(voteEntity.getVOTER_EMAIL_PROPERTY()));
        voter.setEmailSent((boolean) entity.getProperty(voteEntity.getVOTER_EMAILSENT_PROPERTY()));
        voter.setVoted((boolean) entity.getProperty(voteEntity.getVOTER_ISVOTED_PROPERTY()));
        voter.setReminder((boolean) entity.getProperty(voteEntity.getVOTER_REMINDER_PROPERTY()));
        voter.setToken((String) entity.getProperty(voteEntity.getVOTER_TOKEN_PROPERTY()));

        return voter;
    }
    private boolean checkString(String stringval) {
        if (stringval.length() == 0 || stringval.equals(null))
            return false;
        else
            return true;
    }

    private void checkVoterId(String id) throws VotException {
        if (!checkString(id))
            throw new VotException("Voter id is invalid");
    }

    private void checkVoterEmail(String email) throws VotException {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            throw new VotException("voter email is invalid");
    }

    private void checkVoterName(String name) throws VotException {
        if (!checkString(name))
            throw new VotException("Voter name is invalid");
    }

    private List<Voter> convert(List<Entity> voters) throws VotException {

        List<Voter> voterList = new ArrayList<>();
        try {

            for (Entity voterEntity : voters) {
                voterList.add(convertEntityToDto(voterEntity));
            }

        } catch (Exception e) {
            throw new VotException("Could not convert entity to object " + e.getMessage());
        }
        return voterList;
    }

    @Override
    public List<Voter> getUnnotifiedVoters() throws VotException {
        Query.Filter keyFilter = new Query.FilterPredicate(voteEntity.getVOTER_EMAILSENT_PROPERTY(),
                Query.FilterOperator.EQUAL, false);
        Query query = new Query(voteEntity.getVoterKind()).setAncestor(voteEntity.getVoterKey()).setFilter(keyFilter);
        PreparedQuery pq1 = ds.prepare(query);
        List<Entity> voters = pq1.asList(FetchOptions.Builder.withDefaults());
        List<Voter> voterList = convert(voters);
        return voterList;
    }


    @Override
    public List<Voter> getVoterListForReminderEmail() throws VotException {
        Query.Filter keyFilter = new Query.FilterPredicate(voteEntity.getVOTER_REMINDER_PROPERTY(),
                Query.FilterOperator.EQUAL, false);
        Query q = new Query(voteEntity.getVoterKind()).setAncestor(voteEntity.getVoterKey()).setFilter(keyFilter);
        PreparedQuery pq1 = ds.prepare(q);
        List<Entity> voters = pq1.asList(FetchOptions.Builder.withDefaults());
        List<Voter> voterList = convert(voters);
        return voterList;
    }


    @Override
    public int getPendingVoterCount() {
        Query.Filter keyFilter = new Query.FilterPredicate(voteEntity.getVOTER_ISVOTED_PROPERTY(),
                Query.FilterOperator.EQUAL, false);
        Query q = new Query(voteEntity.getVoterKind()).setAncestor(voteEntity.getVoterKey()).setFilter(keyFilter);
        PreparedQuery pq1 = ds.prepare(q);
        List<Entity> voters = pq1.asList(FetchOptions.Builder.withDefaults());
        return voters.size();
    }


    @Override
    public int getCastedVoterCount() {
        Query.Filter keyFilter = new Query.FilterPredicate(voteEntity.getVOTER_ISVOTED_PROPERTY(),
                Query.FilterOperator.EQUAL, true);
        Query q = new Query(voteEntity.getVoterKind()).setAncestor(voteEntity.getVoterKey()).setFilter(keyFilter);
        PreparedQuery pq1 = ds.prepare(q);
        List<Entity> voters = pq1.asList(FetchOptions.Builder.withDefaults());
        return voters.size();
    }

    @Override
    public Voter getVoterByEmail(String email) throws VotException {
        checkVoterEmail(email);
        Query.FilterPredicate emailfilter=new Query.FilterPredicate(voteEntity.getVOTER_EMAIL_PROPERTY(), Query.FilterOperator.EQUAL,email);
        Query q = new Query(voteEntity.getVoterKind()).setAncestor(voteEntity.getVoterKey()).setFilter(emailfilter);
        PreparedQuery pq = ds.prepare(q);
        Entity voter = pq.asSingleEntity();

        Voter voter1=voter!=null?convert(Arrays.asList(voter)).get(0):null;
        return voter1;

    }
}

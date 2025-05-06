package com.yourname.ballot_x.models;

public class Candidate {
    private int id;
    private String name;
    private int electionId;

    public Candidate(int id, String name, int electionId) {
        this.id = id;
        this.name = name;
        this.electionId = electionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElectionId() {
        return electionId;
    }

    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }
}

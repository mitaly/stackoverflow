package com.craft.stackoverflow.entities;

public enum VoteType {
    UPVOTE("upvote"),
    DOWNVOTE( "downvote");

    String value;
    VoteType(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}

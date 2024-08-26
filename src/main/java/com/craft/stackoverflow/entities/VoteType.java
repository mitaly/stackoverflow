package com.craft.stackoverflow.entities;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1);

    public final int value;

    private VoteType(int value) {
        this.value = value;
    }
}

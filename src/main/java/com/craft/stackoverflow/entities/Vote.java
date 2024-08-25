package com.craft.stackoverflow.entities;

public enum Vote {
    UPVOTE(1),
    DOWNVOTE(-1);

    public final int value;

    private Vote(int value) {
        this.value = value;
    }
}

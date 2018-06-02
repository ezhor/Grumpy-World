package com.arensis_games.grumpyworld.model;

public class Ranking {
    private Amigo rolloRanked;
    private Amigo[] topRanking;

    public Ranking(Amigo rolloRanked, Amigo[] topRanking) {
        this.rolloRanked = rolloRanked;
        this.topRanking = topRanking;
    }

    public Amigo getRolloRanked() {
        return rolloRanked;
    }

    public void setRolloRanked(Amigo rolloRanked) {
        this.rolloRanked = rolloRanked;
    }

    public Amigo[] getTopRanking() {
        return topRanking;
    }

    public void setTopRanking(Amigo[] topRanking) {
        this.topRanking = topRanking;
    }
}

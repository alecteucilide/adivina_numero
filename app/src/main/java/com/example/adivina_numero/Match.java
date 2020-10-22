package com.example.adivina_numero;

public class Match implements Comparable<Match>{
    private String name;
    private int score;
    private int time;

    public Match(String name, int score, int time){
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int compareTo(Match m) {
        int scoreComp = this.getScore() - m.getScore();
        int timeComp = this.getTime() - m.getTime();
        if(scoreComp != 0){
            return scoreComp;
        }else{
            return timeComp;
        }
    }
}

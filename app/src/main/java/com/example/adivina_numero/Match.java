package com.example.adivina_numero;

public class Match implements Comparable<Match>{
    private String name;
    private int score;
    private int time;
    private String photoPath;

    public Match(String name, int score, int time, String photoPath){
        this.name = name;
        this.score = score;
        this.time = time;
        this.photoPath = photoPath;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = getPhotoPath();
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

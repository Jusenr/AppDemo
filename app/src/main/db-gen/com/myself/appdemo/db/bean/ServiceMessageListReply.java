package com.myself.appdemo.db.bean;

import java.io.Serializable;

/**
 * Created by zhanghao on 2015/12/8.
 */
public class ServiceMessageListReply implements Serializable {
    private String question;
    private String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

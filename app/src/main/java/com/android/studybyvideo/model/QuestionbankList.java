package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

public class QuestionbankList {


    @SerializedName("id")
    private String id;
    @SerializedName("ttname_id")
    private String ttname_id;
    @SerializedName("q_no")
    private String q_no;
    @SerializedName("book_id")
    private String book_id;
    @SerializedName("chapter_id")
    private String chapter_id;
    @SerializedName("question")
    private String question;
    @SerializedName("option_a")
    private String option_a;
    @SerializedName("option_b")
    private String option_b;
    @SerializedName("option_c")
    private String option_c;

    @SerializedName("option_d")
    private String option_d;
    @SerializedName("option_a_image")
    private String option_a_image;
    @SerializedName("option_b_image")
    private String option_b_image;
    @SerializedName("option_c_image")
    private String option_c_image;
    @SerializedName("option_d_image")
    private String option_d_image;
    @SerializedName("question_image")
    private String question_image;
    @SerializedName("explaination")
    private String explaination;
    @SerializedName("correct_answer")
    private String correct_answer;
    @SerializedName("aunswer_image")
    private String aunswer_image;
    @SerializedName("diff_index")
    private String diff_index;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;

    @SerializedName("answer_marked")
    private String answer_marked;
    @SerializedName("time_taken_to_solve")
    private String time_taken_to_solve;
    @SerializedName("is_attempted")
    private String is_attempted;

    public String getTtname_id() {
        return ttname_id;
    }

    public void setTtname_id(String ttname_id) {
        this.ttname_id = ttname_id;
    }

    public String getAnswer_marked() {
        return answer_marked;
    }

    public void setAnswer_marked(String answer_marked) {
        this.answer_marked = answer_marked;
    }

    public String getTime_taken_to_solve() {
        return time_taken_to_solve;
    }

    public void setTime_taken_to_solve(String time_taken_to_solve) {
        this.time_taken_to_solve = time_taken_to_solve;
    }

    public String getIs_attempted() {
        return is_attempted;
    }

    public void setIs_attempted(String is_attempted) {
        this.is_attempted = is_attempted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQ_no() {
        return q_no;
    }

    public void setQ_no(String q_no) {
        this.q_no = q_no;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }

    public String getOption_a_image() {
        return option_a_image;
    }

    public void setOption_a_image(String option_a_image) {
        this.option_a_image = option_a_image;
    }

    public String getOption_b_image() {
        return option_b_image;
    }

    public void setOption_b_image(String option_b_image) {
        this.option_b_image = option_b_image;
    }

    public String getOption_c_image() {
        return option_c_image;
    }

    public void setOption_c_image(String option_c_image) {
        this.option_c_image = option_c_image;
    }

    public String getOption_d_image() {
        return option_d_image;
    }

    public void setOption_d_image(String option_d_image) {
        this.option_d_image = option_d_image;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getAunswer_image() {
        return aunswer_image;
    }

    public void setAunswer_image(String aunswer_image) {
        this.aunswer_image = aunswer_image;
    }

    public String getDiff_index() {
        return diff_index;
    }

    public void setDiff_index(String diff_index) {
        this.diff_index = diff_index;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getQuestion_image() {
        return question_image;
    }

    public void setQuestion_image(String question_image) {
        this.question_image = question_image;
    }
}

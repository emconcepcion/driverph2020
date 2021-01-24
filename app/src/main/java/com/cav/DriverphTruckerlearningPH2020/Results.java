package com.cav.DriverphTruckerlearningPH2020;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Results implements Parcelable {

    public String askedQuestion;
    public String askedAnswer;
    public String askedImage;

    public Results() {
    }

    public Results(String askedQuestion, String askedAnswer, String askedImage) {
        this.askedQuestion = askedQuestion;
        this.askedAnswer = askedAnswer;
        this.askedImage = askedImage;
    }

    public Results(String askedQuestion, String askedImage) {
        this.askedQuestion = askedQuestion;
        this.askedImage = askedImage;
    }

    protected Results(Parcel in) {
        askedQuestion = in.readString();
        askedAnswer = in.readString();
        askedImage = in.readString();
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public String getAskedQuestion() {
        return askedQuestion;
    }

    public void setAskedQuestion(String askedQuestion) {
        this.askedQuestion = askedQuestion;
    }

    public String getAskedAnswer() {
        return askedAnswer;
    }

    public void setAskedAnswer(String askedAnswer) {
        this.askedAnswer = askedAnswer;
    }

    public String getAskedImage() {
        return askedImage;
    }

    public void setAskedImage(String askedImage) {
        this.askedImage = askedImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(askedQuestion);
        dest.writeString(askedAnswer);
        dest.writeString(askedImage);
    }
}

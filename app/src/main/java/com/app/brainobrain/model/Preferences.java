package com.app.brainobrain.model;

import com.google.gson.annotations.SerializedName;

public class Preferences {
    @SerializedName("max_value")
    private String maxValue;

    @SerializedName("min_value")
    private String minValue;

    @SerializedName("max_digits")
    private String maxDigits;

    @SerializedName("time_slider")
    private String timeSlider;

    @SerializedName("time_slider_ends_at")
    private String timeSliderEndsAt;

    @SerializedName("time_slider_start_at")
    private String timeSliderStartAt;

    @SerializedName("time_slider_increment")
    private String timeSliderIncrement;

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxDigits() {
        return maxDigits;
    }

    public void setMaxDigits(String maxDigits) {
        this.maxDigits = maxDigits;
    }

    public String getTimeSlider() {
        return timeSlider;
    }

    public void setTimeSlider(String timeSlider) {
        this.timeSlider = timeSlider;
    }

    public String getTimeSliderEndsAt() {
        return timeSliderEndsAt;
    }

    public void setTimeSliderEndsAt(String timeSliderEndsAt) {
        this.timeSliderEndsAt = timeSliderEndsAt;
    }

    public String getTimeSliderStartAt() {
        return timeSliderStartAt;
    }

    public void setTimeSliderStartAt(String timeSliderStartAt) {
        this.timeSliderStartAt = timeSliderStartAt;
    }

    public String getTimeSliderIncrement() {
        return timeSliderIncrement;
    }

    public void setTimeSliderIncrement(String timeSliderIncrement) {
        this.timeSliderIncrement = timeSliderIncrement;
    }
}

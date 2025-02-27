package com.hjc.CardAdventure.pojo.environment;

public enum TimeStatus {
    DAY, AFTERNOON, EVENING;

    public String timeStatusToString() {
        if (this.equals(TimeStatus.DAY)) {
            return "早上";
        } else if (this.equals(AFTERNOON)) {
            return "下午";
        } else if (this.equals(EVENING)) {
            return "晚上";
        } else return "";
    }

    public static TimeStatus turn(TimeStatus timeStatus) {
        if (timeStatus.equals(TimeStatus.DAY)) return TimeStatus.AFTERNOON;
        if (timeStatus.equals(TimeStatus.AFTERNOON)) return TimeStatus.EVENING;
        return TimeStatus.DAY;
    }
}

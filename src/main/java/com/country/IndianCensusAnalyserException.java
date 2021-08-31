package com.country;
public class IndianCensusAnalyserException extends Exception {
    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE;
    }

    ExceptionType type;

    public IndianCensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public IndianCensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

}

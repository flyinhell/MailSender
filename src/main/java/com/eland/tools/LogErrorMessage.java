package com.eland.tools;

/**
 * Created by sherrychou on 2018/2/5.
 */
public interface LogErrorMessage {

    //API search error
    public static final String INTERNAL_ERROR_CODE = "1.0";
    public static final String INTERNAL_ERROR_MESSAGE = "Internal error.";
    public static final String MailCheck_JSON_ERROR_CODE = "1.6";
    public static final String MailCheck_JSON_ERROR_MESSAGE = "MailCheck_JSON fail.";
    public static final String MailBlackListCheck_JSON_ERROR_CODE = "1.7";
    public static final String MailBlackListCheck_JSON_ERROR_MESSAGE = "MailBlackListCheck_JSON fail.";
    public static final String MailBlackListModify_JSON_ERROR_CODE = "1.8";
    public static final String MailBlackListModify_JSON_ERROR_MESSAGE = "MailBlackListModify_JSON fail.";


    //DB connection error
    public static final String SQL_EXCEPTION_ERROR_CODE = "3.0";
    public static final String SQL_EXCEPTION_ERROR_MESSAGE = "SQL Exception error.";
    public static final String SQL_SYNTAX_ERROR_CODE = "3.1";
    public static final String SQL_SYNTAX_ERROR_MESSAGE = "SQL Syntax Error.";
    public static final String CONNECTION_CLOSE_ERROR_CODE = "3.2";
    public static final String CONNECTION_CLOSE_ERROR_MESSAGE = "Connection close error.";
    public static final String CONNECTION_ERROR_CODE = "3.3";
    public static final String CONNECTION_ERROR_MESSAGE = "Connection error.";


}

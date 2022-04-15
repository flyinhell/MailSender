package com.eland.tools;


import com.eland.model.ProcessId;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 * Created by IntelliJ IDEA.
 * User: lambertyang
 * Date: 2011/12/20
 * Time: 上午 12:39
 * To change this template use File | Settings | File Templates.
 */
public class ConnectDB {
    private String opvApiDriverName, opvApiSourceName, opvApiAccount, opvApiPassword;
    private String opvGeneralDriverName, opvGeneralSourceName, opvGeneralAccount, opvGeneralPassword;
    private String opviewNotifyDriverName, opviewNotifySourceName, opviewNotifyAccount, opviewNotifyPassword;
    private String haproxy128DriverName, haproxy128SourceName, haproxy128Account, haproxy128Password;
    private String haproxy129DriverName, haproxy129SourceName, haproxy129Account, haproxy129Password;

    private static Logger log = Logger.getLogger("Log");
    private ProcessId pid;

    public ConnectDB(ImportConfig config,
                     ProcessId pid) {

        try {
            this.pid = pid;

            this.opvApiDriverName = config.opvApiDriverName;
            this.opvApiSourceName = config.opvApiSourceName;
            this.opvApiAccount = config.opvApiAccount;
            this.opvApiPassword = config.opvApiPassword;

            this.opvGeneralDriverName = config.opvGeneralDriverName;
            this.opvGeneralSourceName = config.opvGeneralSourceName;
            this.opvGeneralAccount = config.opvGeneralAccount;
            this.opvGeneralPassword = config.opvGeneralPassword;

            this.opviewNotifyDriverName = config.opviewNotifyDriverName;
            this.opviewNotifySourceName = config.opviewNotifySourceName;
            this.opviewNotifyAccount = config.opviewNotifyAccount;
            this.opviewNotifyPassword = config.opviewNotifyPassword;

            this.haproxy128DriverName = config.haproxy128DriverName;
            this.haproxy128SourceName = config.haproxy128SourceName;
            this.haproxy128Account = config.haproxy128Account;
            this.haproxy128Password = config.haproxy128Password;

            this.haproxy129DriverName = config.haproxy129DriverName;
            this.haproxy129SourceName = config.haproxy129SourceName;
            this.haproxy129Account = config.haproxy129Account;
            this.haproxy129Password = config.haproxy129Password;


        } catch (Exception e) {
            e.printStackTrace();
            log.error(pid.getId() + " - " + e.getMessage());
            pid.setQueryEndTime();
            pid.setErrorCode(LogErrorMessage.INTERNAL_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.INTERNAL_ERROR_MESSAGE);
            pid.setErrorMessageDetail("Internal error. ");
        }
    }

    public Connection loginConnectOpvApi() {
        Driver driver = null;
        Connection con = null;
        try {
            driver = (Driver) Class.forName(this.opvApiDriverName).newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(this.opvApiSourceName, this.opvApiAccount, this.opvApiPassword);

            return con;
        } catch (SQLServerException e) {
            log.error(pid.getId() + " - DB Information Sign In Fail " + e.getMessage(),e);
            pid.setQueryEndTime();
            pid.setErrorCode(LogErrorMessage.CONNECTION_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.CONNECTION_ERROR_MESSAGE);
            pid.setErrorMessageDetail("DB Connection error. ");
        } catch (Exception e) {
            log.error(pid.getId() + " - " + e.getMessage(), e);
            pid.setQueryEndTime();
            pid.setErrorCode(LogErrorMessage.INTERNAL_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.INTERNAL_ERROR_MESSAGE);
            pid.setErrorMessageDetail("Internal error. ");
        }
        return con;
    }

    public Connection loginConnectOpvGeneral() {
        Driver driver = null;
        Connection con = null;
        try {
            driver = (Driver) Class.forName(this.opvGeneralDriverName).newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(this.opvGeneralSourceName, this.opvGeneralAccount, this.opvGeneralPassword);

            return con;
        } catch (SQLServerException e) {
            log.error(pid.getId() + " - DB Information Sign In Fail " + e.getMessage(),e);
            pid.setQueryEndTime();
            pid.setErrorCode(LogErrorMessage.CONNECTION_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.CONNECTION_ERROR_MESSAGE);
            pid.setErrorMessageDetail("DB Connection error. ");
        } catch (Exception e) {
            log.error(pid.getId() + " - " + e.getMessage(), e);
            pid.setQueryEndTime();
            pid.setErrorCode(LogErrorMessage.INTERNAL_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.INTERNAL_ERROR_MESSAGE);
            pid.setErrorMessageDetail("Internal error. ");
        }
        return con;
    }

    public Connection loginConnectHaproxy128() {
        Driver driver = null;
        Connection con = null;
        try {
            driver = (Driver) Class.forName(this.haproxy128DriverName).newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(this.haproxy128SourceName, this.haproxy128Account, this.haproxy128Password);

            return con;
        } catch (SQLServerException e) {
            log.error(pid.getId() + " - DB Information Sign In Fail " + e.getMessage(),e);
            pid.setQueryEndTime();
            pid.setErrorCode(LogErrorMessage.CONNECTION_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.CONNECTION_ERROR_MESSAGE);
            pid.setErrorMessageDetail("DB Connection error. ");
        } catch (Exception e) {
            log.error(pid.getId() + " - " + e.getMessage(), e);
            pid.setQueryEndTime();
            pid.setErrorCode(LogErrorMessage.INTERNAL_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.INTERNAL_ERROR_MESSAGE);
            pid.setErrorMessageDetail("Internal error. ");
        }
        return con;
    }

    public Connection loginConnectHaproxy129() {
        Driver driver = null;
        Connection con = null;
        try {
            driver = (Driver) Class.forName(this.haproxy129DriverName).newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(this.haproxy129SourceName, this.haproxy129Account, this.haproxy129Password);

            return con;
        } catch (SQLServerException e) {
            log.error(pid.getId() + " - DB Information Sign In Fail " + e.getMessage(),e);
            pid.setQueryEndTime();
            pid.setErrorCode(LogErrorMessage.CONNECTION_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.CONNECTION_ERROR_MESSAGE);
            pid.setErrorMessageDetail("DB Connection error. ");
        } catch (Exception e) {
            log.error(pid.getId() + " - " + e.getMessage(), e);
            pid.setQueryEndTime();
            pid.setErrorCode(LogErrorMessage.INTERNAL_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.INTERNAL_ERROR_MESSAGE);
            pid.setErrorMessageDetail("Internal error. ");
        }
        return con;
    }

}

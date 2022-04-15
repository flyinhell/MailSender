package com.eland.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ImportConfig {

    @Value("${opv-api.datasource.driver-class-name}")
    public String opvApiDriverName;
    @Value("${opv-api.datasource.jdbc-url}")
    public String opvApiSourceName;
    @Value("${opv-api.datasource.username}")
    public String opvApiAccount;
    @Value("${opv-api.datasource.password}")
    public String opvApiPassword;
    @Value("${opv-api.datasource.log-table}")
    public String epaSourceDataLogTable;


    @Value("${opv-general.datasource.driver-class-name}")
    public String opvGeneralDriverName;
    @Value("${opv-general.datasource.jdbc-url}")
    public String opvGeneralSourceName;
    @Value("${opv-general.datasource.username}")
    public String opvGeneralAccount;
    @Value("${opv-general.datasource.password}")
    public String opvGeneralPassword;


    @Value("${opview-notify.datasource.driver-class-name}")
    public String opviewNotifyDriverName;
    @Value("${opview-notify.datasource.jdbc-url}")
    public String opviewNotifySourceName;
    @Value("${opview-notify.datasource.username}")
    public String opviewNotifyAccount;
    @Value("${opview-notify.datasource.password}")
    public String opviewNotifyPassword;

    @Value("${haproxy128.datasource.driver-class-name}")
    public String haproxy128DriverName;
    @Value("${haproxy128.datasource.jdbc-url}")
    public String haproxy128SourceName;
    @Value("${haproxy128.datasource.username}")
    public String haproxy128Account;
    @Value("${haproxy128.datasource.password}")
    public String haproxy128Password;

    @Value("${haproxy129.datasource.driver-class-name}")
    public String haproxy129DriverName;
    @Value("${haproxy129.datasource.jdbc-url}")
    public String haproxy129SourceName;
    @Value("${haproxy129.datasource.username}")
    public String haproxy129Account;
    @Value("${haproxy129.datasource.password}")
    public String haproxy129Password;

    @Value("${article_list.max_record}")
    public int maxRecord;

   // #-------公司架設的mail伺服器設定-----------

    @Value("${mail.option}")
    public  String mailOption;
   //         # mail host server
   @Value("${mail.host}")
   public  String defaultMailServer;
    @Value("${mail.transport.protocol}")
    public  String defaultMailProtocol;
    @Value("${default.from.mail}")
    public  String defaultFromMail;
    @Value("${default.from.name}")
    public  String defaultFromName;
    @Value("${charset}")
    public  String charset;

    //-------AWS的SES及SNS相關設定-----------
    //        #notify固定使用的的form.mail及from.name

    @Value("${sns.host.switch}")
    public String snsHostSwitch;
    @Value("${sns.host.list}")
    public String snsHostList;

    public List<String> hostList;

    @Value("${aws.mail.host}")
    public String awsMailServer;
    @Value("${aws.port}")
    public String awsMailProtocol;
    @Value("${aws.smtp.username}")
    public String awsSmtpUserName;
    @Value("${aws.smtp.psssword}")
    public String awsSmtpPassword;
    @Value("${aws.attachment.file.path}")
    public String attachmentFilePath;
    @Value("${allow.form.mail.list}")
    public String ALLOW_FROM_MAIL_LIST;

    public List<String> allowFromMailList;

    @Value("${sns.reduceMail.switch}")
    public String snsReduceMailSwitch;
    @Value("${sns.reduceMail.count}")
    public String snsReduceMailCount;
    @Value("${aws.form.mail}")
    public String awsFromMail;
    @Value("${aws.from.name}")
    public String awsFromName;

    public void importConfig() {
        if(snsHostList.length()>0){
            hostList = Arrays.asList(snsHostList.split(","));
        }

    }
    public void importAllowFromMailList() {
   //     System.out.println("ALLOW_FROM_MAIL_LIST:"+ALLOW_FROM_MAIL_LIST);
        allowFromMailList = Arrays.asList(ALLOW_FROM_MAIL_LIST.split(","));
    }
}

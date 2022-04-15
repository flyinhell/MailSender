package com.eland.EmailSender;

import com.eland.DbProcess.OpvGeneralService;

import com.eland.DbProcess.OpviewNotifyService;
import com.eland.JSON.SendMailRequestJSON.SendMailJSON;
import com.eland.JSON.SendMailResponseJSON.SendMailResponseJSON;
import com.eland.aws.AmazonSES;
import com.eland.aws.AmazonSESv2;
import com.eland.aws.MailSMTP;
import com.eland.aws.SimpleNotificationService;
import com.eland.model.ProcessId;

import com.eland.tools.ImportConfig;
import com.eland.tools.LogErrorMessage;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailSender extends HttpServlet {
    private ProcessId pid = new ProcessId();
    Logger logger = Logger.getLogger("Log");
    @Autowired
    ImportConfig config;
    @Autowired
    OpvGeneralService opvGeneralService;
    @Autowired
    OpviewNotifyService opviewNotifyService;
    /**
     * 寄信
     *
     * @param sendMailJSON
     * @param request
     * @return
     */
    @PostMapping("/aws/v2/AwsMailSender")
    @ResponseBody
    protected SendMailResponseJSON doPost(@RequestBody SendMailJSON sendMailJSON, HttpServletRequest request) {


        SendMailResponseJSON sendMailResponseJSON = new SendMailResponseJSON();
        Gson gson = new Gson();

        try {

            //初始化
            pid = new ProcessId();
            Calendar start = Calendar.getInstance();
            pid.setRequestJson(gson.toJson(sendMailJSON));
            pid.setQueryStartTime(start);

            if(config.mailOption.equalsIgnoreCase("AWS")) {
                AmazonSESv2 amazonSESv2 = new AmazonSESv2(config, opvGeneralService, opviewNotifyService);
                amazonSESv2.sendMailBySES(request, gson.toJson(sendMailJSON));
            }else{
                MailSMTP amazonSESv2 = new MailSMTP(config, opvGeneralService, opviewNotifyService);
                amazonSESv2.sendMailBySES(request, gson.toJson(sendMailJSON));
            }

            sendMailResponseJSON.setErrorCode(pid.getErrorCode());
            sendMailResponseJSON.setErrorMessage(pid.getErrorMessage());
        } catch (Exception e) {
            logger.error(pid.getId() + " - " + e.getMessage(), e);
            pid.setErrorCode(LogErrorMessage.INTERNAL_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.INTERNAL_ERROR_MESSAGE);
        }
        return sendMailResponseJSON;
    }


    /**
     * 寄信
     *
     * @param sendMailJSON
     * @param request
     * @return
     */
    @PostMapping("/MailSender")
    @ResponseBody
    protected SendMailResponseJSON doPost5(@RequestBody SendMailJSON sendMailJSON, HttpServletRequest request) {


        SendMailResponseJSON sendMailResponseJSON = new SendMailResponseJSON();
        Gson gson = new Gson();

        try {

            //初始化
            pid = new ProcessId();
            Calendar start = Calendar.getInstance();
            pid.setRequestJson(gson.toJson(sendMailJSON));
            pid.setQueryStartTime(start);


            MailSMTP amazonSESv2 = new MailSMTP(config,opvGeneralService,opviewNotifyService);
            amazonSESv2.sendMailBySES(request, gson.toJson(sendMailJSON));

            sendMailResponseJSON.setErrorCode(pid.getErrorCode());
            sendMailResponseJSON.setErrorMessage(pid.getErrorMessage());
        } catch (Exception e) {
            logger.error(pid.getId() + " - " + e.getMessage(), e);
            pid.setErrorCode(LogErrorMessage.INTERNAL_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.INTERNAL_ERROR_MESSAGE);
        }
        return sendMailResponseJSON;
    }

    /**
     * 更新黑名單
     *
     * @param request
     * @return
     */

    @GetMapping("/Test/blacklist")
    protected void doPost2(HttpServletRequest request) {

        try {

            AmazonSES amazonSES = new AmazonSES(config,opvGeneralService,opviewNotifyService);
            amazonSES.loadBlacklist();

            AmazonSESv2 amazonSESv2 = new AmazonSESv2(config,opvGeneralService,opviewNotifyService);
            amazonSESv2.loadBlacklist();

        } catch (Exception e) {
            logger.error(pid.getId() + " - " + e.getMessage(), e);
            pid.setErrorCode(LogErrorMessage.INTERNAL_ERROR_CODE);
            pid.setErrorMessage(LogErrorMessage.INTERNAL_ERROR_MESSAGE);
        }

    }

    @PostMapping("/Test/aws/sns_receiver")
    public void doPost4(HttpServletRequest request, HttpServletResponse response)throws ServletException,
            IOException,
            SecurityException {


        String remoteHost = request.getRemoteHost(); // 取出對方之主機名稱
        //是否開啟阻擋HOST的開關
        if (config.snsHostSwitch.equals("ON")) {
            config.importConfig();
            //判斷該HOST是否在設定的清單中
            if (config.snsHostList.contains(remoteHost)) {

                Scanner scan = new Scanner(new InputStreamReader(request.getInputStream(), "UTF-8"));

                StringBuilder builder = new StringBuilder();
                while (scan.hasNextLine()) {
                    builder.append(scan.nextLine());
                }
                PrintWriter out = response.getWriter();
                out.println(builder.toString());

                logger.info("sns_receive : " + builder.toString());
                SimpleNotificationService simpleNotificationService =new SimpleNotificationService(config,opviewNotifyService);
                simpleNotificationService.receiveSNS(builder.toString(),remoteHost);
            } else {
                logger.info("sns_receive : Host不在清單中.");
            }
        } else {

            Scanner scan = new Scanner(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            while (scan.hasNextLine()) {
                builder.append(scan.nextLine());
            }
            PrintWriter out = response.getWriter();
            out.println(builder.toString());
            logger.info("sns_receive : " + builder.toString());
            SimpleNotificationService simpleNotificationService =new SimpleNotificationService(config,opviewNotifyService);
            simpleNotificationService.receiveSNS(builder.toString(),remoteHost);
        }

    }


    @GetMapping("/Test/Test")
    public void doPost5(HttpServletRequest request) {
        //  查詢所有的實體

        AmazonSES amazonSES = new AmazonSES(config,opvGeneralService,opviewNotifyService);
       // amazonSES.getBlacklist().forEach(System.out::println);
        amazonSES.getBlacklist().forEach(System.out::println);
        logger.info("log test");
        amazonSES.Test();
    }


}

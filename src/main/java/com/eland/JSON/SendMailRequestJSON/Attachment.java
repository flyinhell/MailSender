package com.eland.JSON.SendMailRequestJSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ccyang on 2020/3/19.
 */
public class Attachment {

    @SerializedName("AttachmentName")
    @Expose
    private String attachmentName;
    @SerializedName("base64")
    @Expose
    private String base64;

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

}

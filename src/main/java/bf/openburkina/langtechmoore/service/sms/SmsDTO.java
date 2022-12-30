package bf.openburkina.langtechmoore.service.sms;

import java.util.List;

public class SmsDTO {
    private String campaignType;
    private String destination;
    private String dlrUrl;
    private String fileName;
    private float groupId;
    private String message;
    private String name;
    private String protocol;
    private String recipientSource;
    private List<RecipientDTO> recipients;
    private String responseUrl;
    private String saveAsModel;
    private List<Object> sendAt;
    private String sender;
    private float step;
    private float totalInterSms;
    private float totalNatSms;

    public String getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(String campaignType) {
        this.campaignType = campaignType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDlrUrl() {
        return dlrUrl;
    }

    public void setDlrUrl(String dlrUrl) {
        this.dlrUrl = dlrUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public float getGroupId() {
        return groupId;
    }

    public void setGroupId(float groupId) {
        this.groupId = groupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRecipientSource() {
        return recipientSource;
    }

    public void setRecipientSource(String recipientSource) {
        this.recipientSource = recipientSource;
    }

    public List<RecipientDTO> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<RecipientDTO> recipients) {
        this.recipients = recipients;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String getSaveAsModel() {
        return saveAsModel;
    }

    public void setSaveAsModel(String saveAsModel) {
        this.saveAsModel = saveAsModel;
    }

    public List<Object> getSendAt() {
        return sendAt;
    }

    public void setSendAt(List<Object> sendAt) {
        this.sendAt = sendAt;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public float getTotalInterSms() {
        return totalInterSms;
    }

    public void setTotalInterSms(float totalInterSms) {
        this.totalInterSms = totalInterSms;
    }

    public float getTotalNatSms() {
        return totalNatSms;
    }

    public void setTotalNatSms(float totalNatSms) {
        this.totalNatSms = totalNatSms;
    }
}

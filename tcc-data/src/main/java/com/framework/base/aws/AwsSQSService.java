package com.framework.base.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * aws sqs 操作类
 *
 */
public class AwsSQSService {
    private AmazonSQS sqsClient;

    public void setSqsClient(AmazonSQS  sqsClient){
        this.sqsClient= sqsClient;
        this.sqsClient.setRegion(Region.getRegion(Regions.CN_NORTH_1));
    }

    public List<Message> receiveMessage(String queueUrl) {
        List<Message> messages=null;
        try{
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
            messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();

        } catch (AmazonClientException ace) {
            ace.printStackTrace();
        }

        return messages;
    }


    public boolean sendMessage(String msg, String queueUrl) {
        boolean flag=true;

        try{
            SendMessageRequest sendReq = new SendMessageRequest(queueUrl, msg);
            SendMessageResult rs = sqsClient.sendMessage(sendReq);
            rs.getMessageId();

        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
            flag=false;
        } catch (AmazonClientException ace) {
            ace.printStackTrace();
            flag=false;
        }
        return flag;
    }

    public void deleteMessage(String sqsUrl, String receiptHandle){
        try{
            sqsClient.deleteMessage(new DeleteMessageRequest().withQueueUrl(sqsUrl).withReceiptHandle(receiptHandle));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ListQueuesResult searchQueueUrl(String urlPrefix){
        ListQueuesResult listQueueRs = null;
        try{

            if(urlPrefix!=null && !urlPrefix.trim().isEmpty()){
                listQueueRs =  sqsClient.listQueues(urlPrefix);
            }else{
                listQueueRs = sqsClient.listQueues();
            }

        } catch (AmazonServiceException ase) {
            ase.printStackTrace();

        } catch (AmazonClientException ace) {
            ace.printStackTrace();
        }
        return listQueueRs;
    }

    public String createQueue(String queue, Integer maxSize, Integer retentionPeriod, Integer waitTimeSecond, Integer visiblitityTimeout){
        ListQueuesResult listQueues = searchQueueUrl(queue);
        if(listQueues != null && listQueues.getQueueUrls() != null && listQueues.getQueueUrls().size() > 0){
            return listQueues.getQueueUrls().get(0).toString();
        }
        try{
            CreateQueueRequest createQueueRequest = new CreateQueueRequest(queue);
            HashMap<String, String> attributes = new HashMap<String,String>();
            attributes.put("DelaySeconds", "0");
            attributes.put("MaximumMessageSize", String.valueOf(maxSize));
            attributes.put("MessageRetentionPeriod", String.valueOf(retentionPeriod));
            attributes.put("ReceiveMessageWaitTimeSeconds", String.valueOf(waitTimeSecond));
            attributes.put("VisibilityTimeout", String.valueOf(visiblitityTimeout));
            createQueueRequest.setAttributes(attributes);
            System.out.println("create SQS queue: " + queue + " with attribute: " + attributes.toString());
            return sqsClient.createQueue(createQueueRequest).getQueueUrl();
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();

        } catch (AmazonClientException ace) {
            ace.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getQueueAttribute(String sqsUrl, List<String> attributes){
        try{
            GetQueueAttributesResult result = sqsClient.getQueueAttributes(new GetQueueAttributesRequest(sqsUrl, attributes));
            if(result != null){
                return result.getAttributes();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}

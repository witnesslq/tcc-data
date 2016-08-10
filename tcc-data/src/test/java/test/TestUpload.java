package test;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;


/**
 * Created by peizj on 16-6-28.
 */
public class TestUpload {

    public static void main(String[] args) {
        String url = "http://172.16.203.181/interface/v1/enterpriseVoice/create";
        String filePath = "/home/peizj/Desktop/1.mp3";

        RestTemplate rest = new RestTemplate();

        FileSystemResource resource = new FileSystemResource(new File(filePath));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add("voiceName", "1,mp3");
        param.add("enterpriseId", "7000001");
        param.add("description","1111111");
        param.add("audit_status","1");
        param.add("audit_comment","");
        param.add("expired_hour","");

        String string = rest.postForObject(url, param, String.class);
        System.out.println(string);
    }
}

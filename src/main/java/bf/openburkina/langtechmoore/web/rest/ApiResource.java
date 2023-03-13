package bf.openburkina.langtechmoore.web.rest;

import bf.openburkina.langtechmoore.service.sms.RecipientDTO;
import bf.openburkina.langtechmoore.service.sms.SmsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiResource {
    private final RestTemplate restTemplate;
    private static final String TOKEN = "abbcaa5cc6ec072a257910c52c5258";

    private final Logger log = LoggerFactory.getLogger(ApiResource.class);

    public ApiResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/sms/{phone}")
    public void sendSMS(@PathVariable String phone) {

        SmsDTO smsDTO = new SmsDTO();
        List<RecipientDTO> recipients = new ArrayList<>();
        recipients.add(new RecipientDTO(null, phone, null));
        smsDTO.setName("Password");
        smsDTO.setSender("LangTech");
        smsDTO.setCampaignType("SIMPLE");
        smsDTO.setRecipientSource("UNIQUE");
        smsDTO.setDestination("NAT_INTER");
        smsDTO.setMessage("Bonjour, un sms de test");
        smsDTO.setRecipients(recipients);
        smsDTO.setSendAt(null);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Content-type", "application/json");
        headers.add("Authorization", "Bearer " + TOKEN);
        HttpEntity<SmsDTO> entity = new HttpEntity<>(smsDTO, headers);

        String response =  restTemplate.exchange(
            "https://api.letexto.com/v1/campaigns", HttpMethod.POST, entity, String.class).getBody();

        log.debug(response);
    }
}

package org.example.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Random;

@RestController
public class MyController {
    private static final Log log = LogFactory.getLog(MyController.class);

    @Autowired
    private RestTemplate restTemplate;
    private final Random random = new Random();

    @GetMapping("/serviceA/api/s1")
    public String service1() {
        if (random.nextInt(4) == 0) {
            throw new NullPointerException("OPOPOPOP- NullPointerException for testing");
        }
        String s2Response = restTemplate.getForObject("http://localhost:8081/serviceB/api/s1", String.class);
        return "Response from ServiceA - api1 and " + s2Response;
    }

    @GetMapping("/serviceA/api/s2")
    public String service2() {
        String s2Response = restTemplate.getForObject("https://saiteja1.free.beeceptor.com/hello", String.class);
        return "Response from ServiceA - api2 and " + s2Response;
    }

    @GetMapping("/serviceA/api/s3")
    public String service3() {
        String s2Response = restTemplate.getForObject("http://localhost:8081/serviceB/api/s3", String.class);
        return "Response from ServiceA - api3 and " + s2Response;
    }

    @GetMapping("/serviceA/api/s4")
    public String service4() {
       try{
           String s2Response = restTemplate.getForObject("http://localhost:8081/serviceB/api/s4", String.class);
           return "Response from ServiceA - api4 and " + s2Response;
       }catch (Exception e){
           throw new ArrayIndexOutOfBoundsException();
       }


    }

    @GetMapping("/serviceA/api/s5")
    public String service5() {
        try{
            String s2Response = restTemplate.getForObject("http://localhost:8081/serviceB/api/s5", String.class);
            return "Response from ServiceA - api5 and " + s2Response;
        }catch (Exception e){
            return  "Dont worry";
        }
    }

    @GetMapping("/serviceA/api/s6")
    public Map<String, Object> service6() {
        try{
            Map<String, Object> res = restTemplate.getForObject("http://localhost:8081/serviceB/api/s6", Map.class);
            Integer resCode = (Integer)res.get("status");
            System.out.println("Response code is " + resCode);
            log.info("Response code is " + resCode);
           return res;
        }catch (Exception e){
            throw new RuntimeException("Dont worry");
        }
    }


}

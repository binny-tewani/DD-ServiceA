package org.example.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
        String s2Response = restTemplate.getForObject("https://binny.free.beeceptor.com/hello", String.class);
        return "Response from ServiceA - api2 and " + s2Response;
    }

    @GetMapping("/serviceA/api/s3")
    public String service3() {
        String s2Response = restTemplate.getForObject("http://localhost:8081/serviceB/api/s3", String.class);
        return "Response from ServiceA - api3 and " + s2Response;
    }

    @GetMapping("/serviceA222/api/s4")
    public String service4() {
       return myHelper();
    }

    public String myHelper(){
       try{
           String s2Response = restTemplate.getForObject("http://localhost:8081/serviceB/api/s4", String.class);
           return "Response from ServiceA - api4 and " + s2Response;
       }catch (Exception e){
           return null;
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
            log.error(e.getMessage());
            throw new RuntimeException("Dont worry");
        }
    }

    @GetMapping("/serviceA/api/s7")
    public String service7() {
        String s2Response = restTemplate.getForObject("https://binny3.free.beeceptor.com/", String.class);
        return "Response from ServiceA - api2 and " + s2Response;
    }

    @PostMapping("/upload")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected for uploading");
        }
        String tempDir = System.getProperty("java.io.tmpdir");
        String tempFilePath = tempDir + File.separator + file.getOriginalFilename();

        try {
            Path tempFile = Files.createTempFile("upload_", "_" + file.getOriginalFilename());
            file.transferTo(tempFile.toFile());
            return ResponseEntity.ok("File successfully uploaded: " + tempFile.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing the file");
        }
    }


}

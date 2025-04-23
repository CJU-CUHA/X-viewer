package org.example.xviewer.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.xviewer.model.EventData;
import org.example.xviewer.security.dto.ResMessage;
import org.example.xviewer.service.EventDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final EventDataService eventDataService;
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResMessage> uploadFile(@RequestBody MultipartFile file, @RequestParam Long CaseId, @RequestParam String pcName) throws IOException {
        System.out.println("uploadFile");
        System.out.println("file = " + file.getOriginalFilename());
        // 예: src/main/resources/static/evtx
        String basePath = new File("src/main/resources/static/evtx").getAbsolutePath();
        File destFile = new File(basePath + "/" + file.getOriginalFilename());

        file.transferTo(destFile);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://127.0.0.1:5000/?filename=" + file.getOriginalFilename(), String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResMessage("File upload failed"));
        }

        //System.out.println("response.getBody() = " + response.getBody());
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> events = mapper.readValue(response.getBody(), new TypeReference<List<Map<String, Object>>>() {
        });

        for (Map<String, Object> event : events) {
            String eventId = (String)event.get("event_id");
            String timeCreated = (String) event.get("time_created");

            List<Map<String, String>> summaryList = (List<Map<String, String>>) event.get("summary");

            EventData eventData = new EventData();
            eventData.setEventData(summaryList);
            eventDataService.createEventData(eventData,timeCreated,eventId);
//            System.out.println("Event ID: " + eventId);

//            System.out.println("Time Created: " + timeCreated);
//            System.out.println("Flattened Summary: " + summaryList);
//            System.out.println("---------------------------------------------------");
        }
        ResMessage resMessage = new ResMessage();
        resMessage.setMessage("File upload successful");
        return ResponseEntity.status(HttpStatus.OK).body(resMessage);
    }
}

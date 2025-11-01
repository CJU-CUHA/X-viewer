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
import java.sql.Time;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final EventDataService eventDataService;
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResMessage> uploadFile(
            @RequestParam MultipartFile file,
            @RequestParam Long CaseId,
            @RequestParam String pcName) throws IOException {

        long startTime = System.currentTimeMillis();

        // 공유 폴더 경로
        String basePath = "/app/static/evtx";
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs(); // 디렉토리 없으면 생성
        }

        File destFile = new File(dir, file.getOriginalFilename());
        file.transferTo(destFile);

        // Flask에 GET 요청 (파일명 전달)
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://X-viewer-flask:5000/?filename=" + file.getOriginalFilename(), String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResMessage("File upload failed"));
        }

        // JSON 파싱
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> events = mapper.readValue(response.getBody(),
                new TypeReference<List<Map<String, Object>>>() {});

        for (Map<String, Object> event : events) {
            String eventId = (String) event.get("event_id");
            String timeCreated = (String) event.get("time_created");
            List<Map<String, String>> summaryList = (List<Map<String, String>>) event.get("summary");

            EventData eventData = new EventData();
            eventData.setEventData(summaryList);
            eventDataService.createEventData(eventData, timeCreated, eventId);
        }

        long endTime = System.currentTimeMillis();
        ResMessage resMessage = new ResMessage();
        resMessage.setMessage((endTime - startTime) + "ms");
        return ResponseEntity.ok(resMessage);
    }

}

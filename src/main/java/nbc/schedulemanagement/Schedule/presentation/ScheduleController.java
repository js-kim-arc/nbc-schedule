package nbc.schedulemanagement.Schedule.presentation;

import lombok.RequiredArgsConstructor;
import nbc.schedulemanagement.Schedule.application.service.ScheduleService;
import nbc.schedulemanagement.Schedule.presentation.dto.ScheduleRequest;
import nbc.schedulemanagement.Schedule.presentation.dto.ScheduleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(
            @RequestBody ScheduleRequest.Create request
                                                  ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(scheduleService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findAll(
            @RequestParam(required = false) String author
                                                         ) {
        return ResponseEntity.ok(scheduleService.findAll(author));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(
            @PathVariable Long id
                                                    ) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(
            @PathVariable Long id,
            @RequestBody ScheduleRequest.Update request
                                                  ) {
        return ResponseEntity.ok(scheduleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Long id,
            @RequestBody ScheduleRequest.Delete request
                                                     ) {
        scheduleService.delete(id, request);
        return ResponseEntity.ok(Map.of("message", "일정이 삭제되었습니다."));
    }

}
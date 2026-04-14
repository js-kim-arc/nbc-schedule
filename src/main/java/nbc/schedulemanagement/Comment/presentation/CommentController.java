package nbc.schedulemanagement.Comment.presentation;


import lombok.RequiredArgsConstructor;
import nbc.schedulemanagement.Comment.application.service.CommentService;
import nbc.schedulemanagement.Comment.presentation.dto.CommentRequest;
import nbc.schedulemanagement.Comment.presentation.dto.CommentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @PathVariable Long scheduleId,
            @RequestBody CommentRequest.Create request
                                                 ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commentService.create(scheduleId, request));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAll(
            @PathVariable Long scheduleId
                                                        ) {
        return ResponseEntity.ok(commentService.findAllByScheduleId(scheduleId));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest.Update request
                                                 ) {
        return ResponseEntity.ok(commentService.update(scheduleId, commentId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest.Delete request
                                                     ) {
        commentService.delete(scheduleId, commentId, request);
        return ResponseEntity.ok(Map.of("message", "댓글이 삭제되었습니다."));
    }
}

package nbc.schedulemanagement.Schedule.presentation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import nbc.schedulemanagement.Schedule.domain.model.Schedule;

import java.time.LocalDateTime;

public record ScheduleResponse(
        Long id,
        String title,
        String content,
        String author,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt
) {
    public static ScheduleResponse from(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}

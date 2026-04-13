package nbc.schedulemanagement.Schedule.presentation.dto;

public class ScheduleRequest {
    public record Create(
            String title,
            String content,
            String author,
            String password
    ) {}

}
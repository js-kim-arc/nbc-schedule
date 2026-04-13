package nbc.schedulemanagement.Schedule.presentation.dto;

public class ScheduleRequest {
    public record Create(
            String title,
            String content,
            String author,
            String password
    ) {}

    // ─── 2. 일정 수정 ─────────────────────────────────────
    public record Update(
            String title,
            String author,
            String password
    ) {}

    // ─── 3. 일정 삭제 ─────────────────────────────────────
    public record Delete(
            String password
    ) {}

}
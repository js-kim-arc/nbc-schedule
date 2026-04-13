package nbc.schedulemanagement.Schedule.infrastructure;

import nbc.schedulemanagement.Schedule.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByOrderByUpdatedAtDesc();

    List<Schedule> findByAuthorOrderByUpdatedAtDesc(String author);
}

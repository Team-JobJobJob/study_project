package team01.studyCm.alarm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team01.studyCm.alarm.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}

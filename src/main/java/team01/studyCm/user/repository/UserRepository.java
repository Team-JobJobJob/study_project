package team01.studyCm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team01.studyCm.user.entity.User;

public interface UserRepository  extends JpaRepository<User, Long> {
}

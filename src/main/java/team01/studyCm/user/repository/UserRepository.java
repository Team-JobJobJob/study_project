package team01.studyCm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team01.studyCm.user.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
}

package team01.studyCm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team01.studyCm.user.entity.User;
import java.util.Optional;
import team01.studyCm.user.entity.status.SocialType;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmailAndPassword(String id, String password);

  Optional<User> findByUserName(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

  Optional<User> findByRefreshToken(String refreshToken);

}

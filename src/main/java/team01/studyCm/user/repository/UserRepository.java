package team01.studyCm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.entity.status.SocialType;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmailAndPassword(String userName, String password);
  Optional<User> findByEmail(String email);
  Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
  Optional<User> findByRefreshToken(String refreshToken);


}
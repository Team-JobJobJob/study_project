package team01.studyCm.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team01.studyCm.user.entity.PrincipalDetails;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원."));

    return PrincipalDetails.create(user);
  }
}

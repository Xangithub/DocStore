package home.local.vtbtest.service;

import home.local.vtbtest.dto.UserDto;
import home.local.vtbtest.entity.User;
import home.local.vtbtest.mapper.UserMapper;
import home.local.vtbtest.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserDto> getUser(Long id) {
        final Optional<User> optUser = userRepository.findById(id);
        return optUser.map(user -> Optional.of(userMapper.toDto(user))).orElse(Optional.empty());
    }

    public Long saveUser(UserDto userDto) {
        final User user = userMapper.toEntity(userDto);
        final User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public List<UserDto> getAll() {
        final List<UserDto> dtoList = new ArrayList<>();
        userRepository.findAll().forEach(user -> dtoList.add(userMapper.toDto(user)));
        return dtoList;
    }

    public Boolean auth(UserDto user) {
        @NotNull final String login = user.getLogin();
        final Optional<User> userOpt = userRepository.findByUsername(login);
        if (userOpt.isPresent()) {
            final String passFromBase = userOpt.get().getPassword();
            if (passFromBase == null) return false;
            return  passwordEncoder.matches(user.getPass(), passFromBase);
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
     return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }
}

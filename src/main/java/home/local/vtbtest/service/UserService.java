package home.local.vtbtest.service;

import home.local.vtbtest.dto.UserDto;
import home.local.vtbtest.entity.User;
import home.local.vtbtest.mapper.UserMapper;
import home.local.vtbtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        final Optional<User> userOpt = userRepository.findByLogin(login);
        if (userOpt.isPresent()) {
            final String passFromBase = userOpt.get().getPassHash();
            if (passFromBase == null) return false;
            final String passFromDto = DigestUtils.md5DigestAsHex(user.getPass().getBytes());
            return passFromBase.equals(passFromDto);
        }
        return false;
    }
}

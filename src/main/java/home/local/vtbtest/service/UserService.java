package home.local.vtbtest.service;

import home.local.vtbtest.dto.DocumentDto;
import home.local.vtbtest.dto.UserDto;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.entity.User;
import home.local.vtbtest.mapper.DocumentMapper;
import home.local.vtbtest.mapper.UserMapper;
import home.local.vtbtest.repository.DocumentRepository;
import home.local.vtbtest.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

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
        return userRepository.findAll().stream().map(user -> userMapper.toDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username)  {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " was not found!")); //String.format("Username [%username] not found")
    }

    public List<DocumentDto> getDocuments(Long userId) {
        final Optional<User> userOpt = userRepository.findById(userId);
        final User user = userOpt.orElseThrow(() -> new UsernameNotFoundException(userId + " was not found!"));
        final List<Document> userDocs = documentRepository.findAllByUser(user);
        return userDocs.stream().map(document -> documentMapper.toDto(document)).collect(Collectors.toList());
    }
}

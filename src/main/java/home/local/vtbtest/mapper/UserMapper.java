package home.local.vtbtest.mapper;

import home.local.vtbtest.dto.UserDto;
import home.local.vtbtest.entity.User;
import home.local.vtbtest.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Component
public class UserMapper extends AbstractMapper<User, UserDto> {

    private final ModelMapper mapper;
    private final UserRepository userRepository;

    UserMapper(ModelMapper mapper, UserRepository userRepository) {
        super(User.class, UserDto.class);
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setPass)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(UserDto.class, User.class)
                .addMappings(m -> m.skip(User::setPassword)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(User source, UserDto destination) {
    }

    @Override
    void mapSpecificFields(UserDto source, User destination) {
        @NotNull final String pass = source.getPass();
        final String passHash = DigestUtils.md5DigestAsHex(pass.getBytes());
        destination.setPassword(passHash);
    }
}

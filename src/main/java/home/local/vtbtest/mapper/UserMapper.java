package home.local.vtbtest.mapper;

import home.local.vtbtest.dto.UserDto;
import home.local.vtbtest.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Component
public class UserMapper extends AbstractMapper<User, UserDto> {

    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;


    UserMapper(ModelMapper mapper, PasswordEncoder passwordEncoder) {
        super(User.class, UserDto.class);
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setPassword)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(UserDto.class, User.class)
                .addMappings(m -> m.skip(User::setPassword)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(User source, UserDto destination) {
        // Хеш пароля не нуждается в отображении на фронт
    }

    @Override
    void mapSpecificFields(UserDto source, User destination) {
        @NotNull final String rawPass = source.getPassword();
        final String passHash = passwordEncoder.encode(rawPass);
        destination.setPassword(passHash);
    }
}

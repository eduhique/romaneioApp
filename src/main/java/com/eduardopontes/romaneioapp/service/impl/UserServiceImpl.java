package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.CredentialsDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.UserDto;
import com.eduardopontes.romaneioapp.dto.UserPasswordChangeRequest;
import com.eduardopontes.romaneioapp.dto.mapper.UserMapper;
import com.eduardopontes.romaneioapp.exception.BadRequestException;
import com.eduardopontes.romaneioapp.exception.InvalidPasswordException;
import com.eduardopontes.romaneioapp.model.user.Function;
import com.eduardopontes.romaneioapp.model.user.User;
import com.eduardopontes.romaneioapp.repository.UserRepository;
import com.eduardopontes.romaneioapp.service.UserRoleService;
import com.eduardopontes.romaneioapp.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado.";

    public static final String SENHA_DE_OUTRO_ADMINISTRADOR = "Não é possível alterar a senha de outro administrador";

    public static final String A_SENHA_DEVE_TER_NO_MINIMO_6_CARACTERES = "A senha deve ter no mínimo 6 caracteres.";

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        if (userRepository.existsByNickname(userDto.getNickname()))
            throw new BadRequestException(String.format("NickName %s já cadastrado.", userDto.getNickname()));

        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncode(user.getPassword()));
        userRepository.save(user);
        userRoleService.save(user);
        return userMapper.fromUser(user);
    }

    @Transactional
    @Override
    public void update(Long id, UserDto userDto) {
        userRepository.findById(id)
                .map(user -> {
                    user.setName(userDto.getName());
                    user.setActive(userDto.isActive());
                    user.setFunction(userDto.getFunction());
                    userRepository.save(user);

                    userRoleService.update(user);
                    return user;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO));
    }

    @Override
    @Transactional
    public void changePassword(Long id, UserPasswordChangeRequest changePassword) {
        userRepository.findById(id)
                .map(user -> {
                    if (user.getFunction() == Function.ADMINISTRADOR)
                        throw new BadRequestException(SENHA_DE_OUTRO_ADMINISTRADOR);
                    user.setPassword(passwordEncode(changePassword.getNewPassword()));
                    userRepository.save(user);
                    return user;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO));
    }

    @Override
    public User auth(CredentialsDto credentialsDto) {
        User user = findByNickname((credentialsDto.getLogin()));

        if (!passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return user;
    }

    @Override
    public PageDto<UserDto> findAll(Example<User> filter, Integer page, Integer size, Sort.Order order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Page<User> result = userRepository.findAll(filter, pageable);

        return PageDto.<UserDto>builder()
                .data(result.getContent().stream().map(userMapper::fromUser).collect(Collectors.toList()))
                .pageNumber(result.getNumber())
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .last(result.isLast())
                .build();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO));
    }

    @Override
    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);

                    return user;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByNickname(username);

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getNickname())
                .password(user.getPassword())
                .roles(new String[]{})
                .build();
    }

    private String passwordEncode(String password) {
        if (password.trim().length() < 6)
            throw new BadRequestException(A_SENHA_DEVE_TER_NO_MINIMO_6_CARACTERES);

        return passwordEncoder.encode(password);

    }
}

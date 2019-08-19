package com.canx.cebulax.service;

import com.canx.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.model.JwtAuthenticationResponse;
import com.canx.cebulax.model.Role;
import com.canx.cebulax.model.User;
import com.canx.cebulax.model.UserRole;
import com.canx.cebulax.repository.RoleRepository;
import com.canx.cebulax.repository.UserRepository;
import com.canx.cebulax.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(UserCreateDTO userCreateDTO) {
        userRepository.findByName(userCreateDTO.getName()).ifPresent(user -> {
            throw new EntityAlreadyExistsException("User ", userCreateDTO.getName());
        });

        List<Role> roles = roleRepository.findByRole(UserRole.USER);
        User user = new User();
        user.setName(userCreateDTO.getName());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse authenticate(UserAuthenticateDTO userAuthenticateDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthenticateDTO.getName(),
                        userAuthenticateDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User", id.toString()));
    }

}

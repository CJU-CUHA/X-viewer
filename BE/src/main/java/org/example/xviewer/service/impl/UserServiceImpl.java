package org.example.xviewer.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.xviewer.model.User;
import org.example.xviewer.repository.UserRepository;
import org.example.xviewer.security.dto.ResMessage;
import org.example.xviewer.security.dto.user.JoinRequest;
import org.example.xviewer.security.dto.user.LoginRequest;
import org.example.xviewer.security.jwt.JWTUtil;
import org.example.xviewer.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public ResMessage join(JoinRequest joinRequest) {
        ResMessage resMessage = new ResMessage();
        if(userRepository.findByUsername(joinRequest.getUsername()).isPresent()){

           resMessage.setMessage("사용할 수 없는 이름입니다.");
           return resMessage;
        }
        if(userRepository.findByEmail(joinRequest.getEmail()).isPresent()){
            resMessage.setMessage("사용할 수 없는 이메일입니다.");
            return resMessage;
        }
        User user = User.builder()
               .username(joinRequest.getUsername())
               .email(joinRequest.getEmail())
               .password(bCryptPasswordEncoder.encode(joinRequest.getPassword())).build();
        userRepository.save(user);
        resMessage.setMessage("가입을 환영합니다.");
        return resMessage;
    }

    @Override
    public ResMessage login(LoginRequest loginRequest) {
        Optional<User> user=userRepository.findByUsername(loginRequest.getUsername());
        ResMessage resMessage = new ResMessage();
        if(!user.isPresent()){
            resMessage.setMessage("등록되지 않은 유저입니다.");
            return resMessage;
        }
        if(bCryptPasswordEncoder.matches(loginRequest.getPassword(),user.get().getPassword())){
            resMessage.setMessage(jwtUtil.createJWT(user.get().getUsername(), loginRequest.getUsername(), 30 * 60 * 1000L));
            return resMessage;
        }
        else{
            resMessage.setMessage("잘못된 아이디 혹은 비번입니다.");
            return resMessage;
        }

    }
}

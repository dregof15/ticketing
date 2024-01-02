package com.example.ticketing.web.api.user;

import com.example.ticketing.domain.user.User;
import com.example.ticketing.service.user.UserService;
import com.example.ticketing.web.dto.user.JoinUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinApiController {
    private final UserService userService;

    @PostMapping("/idCheck")
    public ResponseEntity idCheck(@RequestBody Map<String, String> map) {
        String result = userService.idCheck(map.get("mberId"));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/member")
    public ResponseEntity saveMember(@RequestBody JoinUserDto joinUserDto) throws IOException {
        User user = userService.saveMember(joinUserDto);
        return ResponseEntity.ok(user);
    }
}

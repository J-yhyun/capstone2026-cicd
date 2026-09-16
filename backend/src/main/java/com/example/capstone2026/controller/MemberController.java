package com.example.capstone2026.controller;

import com.example.capstone2026.entity.Member;
import com.example.capstone2026.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:63352", "http://localhost:63342"}) // 도커 프론트(63352) + IntelliJ 내장서버(63342) 둘 다 허용
public class MemberController {

    private final MemberRepository memberRepository;
    /**
     * 회원가입 API
     * POST <http://localhost:8080/api/members/register>
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Member member) {
        try {
            Member savedMember = memberRepository.save(member);
            return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("회원가입 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * 로그인 API
     * POST <http://localhost:8080/api/members/login>
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member loginRequest) {
        // 아이디로 DB 조회
        Optional<Member> member = memberRepository.findByUserId(loginRequest.getUserId());

        // 비밀번호 비교 (실습용 평문 비교)
        if (member.isPresent() && member.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(member.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("아이디 또는 비밀번호가 틀렸습니다.");
        }
    }

}
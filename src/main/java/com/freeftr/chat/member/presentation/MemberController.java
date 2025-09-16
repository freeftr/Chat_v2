package com.freeftr.chat.member.presentation;

import com.freeftr.chat.member.application.MemberService;
import com.freeftr.chat.member.dto.request.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<Void> createMember(@RequestBody MemberCreateRequest request) {
		memberService.createMember(request);
		return ResponseEntity.noContent().build();
	}
}

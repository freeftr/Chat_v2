package com.freeftr.chat.member.application;

import com.freeftr.chat.member.domain.Member;
import com.freeftr.chat.member.domain.repository.MemberJpaRepository;
import com.freeftr.chat.member.dto.request.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberJpaRepository memberJpaRepository;

	public void createMember(MemberCreateRequest request) {
		Member member = Member.builder()
				.nickname(request.nickname())
				.build();
		memberJpaRepository.save(member);
	}
}

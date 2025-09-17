package com.freeftr.chat.chat.application;

import com.freeftr.chat.chat.domain.ChatRoom;
import com.freeftr.chat.chat.domain.repository.ChatRoomJpaRepository;
import com.freeftr.chat.chat.dto.request.ChatRoomCreateRequest;
import com.freeftr.chat.chat.dto.response.ChatRoomCreateResponse;
import com.freeftr.chat.common.exception.BadRequestException;
import com.freeftr.chat.common.exception.ErrorCode;
import com.freeftr.chat.member.domain.Member;
import com.freeftr.chat.member.domain.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomJpaRepository chatRoomJpaRepository;
	private final MemberJpaRepository memberJpaRepository;

	@Transactional
	public ChatRoomCreateResponse create(ChatRoomCreateRequest request, Long memberId) {
		Member member = getMember(memberId);


		// TODO: 1대1 채팅인 경우 이미 있는지 검증

		ChatRoom chatRoom = ChatRoom.builder()
				.chatRoomName(request.chatRoomName())
				.build();

		chatRoomJpaRepository.save(chatRoom);
	}

	private Member getMember(Long memberId) {
		return memberJpaRepository.findById(memberId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
	}
}

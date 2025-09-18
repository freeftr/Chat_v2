package com.freeftr.chat.chat.application;

import com.freeftr.chat.chat.domain.ChatRoom;
import com.freeftr.chat.chat.domain.repository.ChatJpaRepository;
import com.freeftr.chat.chat.domain.repository.ChatMemberJpaRepository;
import com.freeftr.chat.chat.domain.repository.ChatRoomJpaRepository;
import com.freeftr.chat.chat.dto.query.ChatRoomName;
import com.freeftr.chat.chat.dto.query.ChatRoomWIthDisplayIdx;
import com.freeftr.chat.chat.dto.query.LastChats;
import com.freeftr.chat.chat.dto.request.ChatRoomCreateRequest;
import com.freeftr.chat.chat.dto.response.ChatRoomCreateResponse;
import com.freeftr.chat.chat.dto.response.ChatRoomResponse;
import com.freeftr.chat.common.application.RedisService;
import com.freeftr.chat.common.exception.BadRequestException;
import com.freeftr.chat.common.exception.ErrorCode;
import com.freeftr.chat.member.domain.Member;
import com.freeftr.chat.member.domain.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private static final String MEMBERS_CHAT_ROOMS_KEY = "members:%s:rooms";

	private final ChatRoomJpaRepository chatRoomJpaRepository;
	private final ChatJpaRepository chatJpaRepository;
	private final MemberJpaRepository memberJpaRepository;
	private final ChatMemberJpaRepository chatMemberJpaRepository;
	private final RedisService redisService;

	@Transactional
	public ChatRoomCreateResponse createOneOnOne(ChatRoomCreateRequest request, Long memberId) {
		Member host = getMember(memberId);
		Member guest = getMember(request.guestId());
		// TODO: 1대1 채팅인 경우 이미 있는지 검증

		ChatRoom chatRoom = ChatRoom.builder()
				.chatRoomName(request.chatRoomName())
				.build();

		chatRoomJpaRepository.save(chatRoom);
		return null;
	}

	public List<ChatRoomResponse> findAll(Long memberId) {
		Member member = getMember(memberId);

		String key = String.format(MEMBERS_CHAT_ROOMS_KEY, memberId);
		// TODO: Sorted Set에 캐싱

		// Cache Miss
		List<ChatRoomWIthDisplayIdx> chatRoomWIthDisplayIdxes = chatMemberJpaRepository
				.findChatRoomIdsByMemberId(memberId);
		List<Long> chatRoomIds = chatRoomWIthDisplayIdxes.stream()
				.map(ChatRoomWIthDisplayIdx::chatRoomId)
				.toList();
		List<Long> lastChatIds = chatRoomWIthDisplayIdxes.stream()
				.map(ChatRoomWIthDisplayIdx::displayIdx)
				.toList();

		Map<Long, String> nameByRoomId = chatRoomJpaRepository.findChatRoomNameIn(chatRoomIds)
				.stream()
				.collect(Collectors.toMap(ChatRoomName::id, ChatRoomName::name));

		Map<Long, LastChats> lastByRoomId = chatJpaRepository.findLastChats(lastChatIds)
				.stream()
				.collect(Collectors.toMap(LastChats::chatRoomId, Function.identity()));

		return chatRoomWIthDisplayIdxes.stream()
				.map(queryResponse -> {
					Long roomId = queryResponse.chatRoomId();
					LastChats lastChats = lastByRoomId.get(roomId);
					return new ChatRoomResponse(
							roomId,
							nameByRoomId.get(roomId),
							queryResponse.displayIdx(),
							lastChats.content(),
							lastChats.authorName(),
							lastChats.lastChatAt()
					);
				})
				.toList();
	}

	private Member getMember(Long memberId) {
		return memberJpaRepository.findById(memberId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
	}
}

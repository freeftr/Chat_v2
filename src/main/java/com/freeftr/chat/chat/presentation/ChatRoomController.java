package com.freeftr.chat.chat.presentation;

import com.freeftr.chat.chat.application.ChatRoomService;
import com.freeftr.chat.chat.dto.request.ChatRoomCreateRequest;
import com.freeftr.chat.chat.dto.response.ChatRoomCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

	@PostMapping
	public ResponseEntity<ChatRoomCreateResponse> createChatRoom(
			@RequestBody ChatRoomCreateRequest request,
			@RequestParam Long memberId
	) {
		return ResponseEntity.ok(chatRoomService.create(request, memberId));
	}
}

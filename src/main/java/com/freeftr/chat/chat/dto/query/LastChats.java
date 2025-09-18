package com.freeftr.chat.chat.dto.query;

import java.time.LocalDateTime;

public record LastChats(
		Long chatRoomId,
		String content,
		String authorName,
		LocalDateTime lastChatAt
) {
}

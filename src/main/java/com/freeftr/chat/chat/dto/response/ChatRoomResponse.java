package com.freeftr.chat.chat.dto.response;

import java.time.LocalDateTime;

public record ChatRoomResponse (
		Long id,
		String name,
		Long displayIdx,
		String senderName,
		String lastContent,
		LocalDateTime lastChatAt
){
}

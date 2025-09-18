package com.freeftr.chat.chat.dto.request;

import java.util.List;

public record ChatRoomCreateRequest(
		String chatRoomName,
		Long guestId
) {
}

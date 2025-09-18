package com.freeftr.chat.chat.domain.repository;

import com.freeftr.chat.chat.dto.query.ChatRoomName;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepositoryCustom {

	List<ChatRoomName> findChatRoomNameIn(List<Long> chatRoomIds);
}

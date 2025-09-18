package com.freeftr.chat.chat.domain.repository;

import com.freeftr.chat.chat.dto.query.ChatRoomWIthDisplayIdx;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMemberRepositoryCustom {

	List<ChatRoomWIthDisplayIdx> findChatRoomIdsByMemberId(Long memberId);
}

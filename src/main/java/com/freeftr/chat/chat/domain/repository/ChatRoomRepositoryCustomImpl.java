package com.freeftr.chat.chat.domain.repository;

import com.freeftr.chat.chat.dto.query.ChatRoomName;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.freeftr.chat.chat.domain.QChatRoom.chatRoom;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

	private final JPAQueryFactory queryFactory;


	@Override
	public List<ChatRoomName> findChatRoomNameIn(List<Long> chatRoomIds) {
		return queryFactory.select(
				Projections.constructor(
						ChatRoomName.class,
						chatRoom.id,
						chatRoom.chatRoomName
				))
				.from(chatRoom)
				.where(chatRoom.id.in(chatRoomIds))
				.fetch();
	}
}

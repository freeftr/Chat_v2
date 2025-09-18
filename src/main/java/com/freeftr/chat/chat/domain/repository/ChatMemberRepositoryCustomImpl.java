package com.freeftr.chat.chat.domain.repository;

import com.freeftr.chat.chat.dto.query.ChatRoomWIthDisplayIdx;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.freeftr.chat.chat.domain.QChatMember.chatMember;

@Repository
@RequiredArgsConstructor
public class ChatMemberRepositoryCustomImpl implements ChatMemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ChatRoomWIthDisplayIdx> findChatRoomIdsByMemberId(Long memberId) {
		return queryFactory.select(
				Projections.constructor(
						ChatRoomWIthDisplayIdx.class,
						chatMember.id,
						chatMember.displayIdx
				))
				.from(chatMember)
				.where(chatMember.memberId.eq(memberId))
				.orderBy(chatMember.displayIdx.desc())
				.fetch();
	}
}

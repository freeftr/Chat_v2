package com.freeftr.chat.chat.domain.repository;

import com.freeftr.chat.chat.dto.query.LastChats;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.freeftr.chat.chat.domain.QChat.chat;
import static com.freeftr.chat.member.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryCustomImpl implements ChatRepositoryCustom {

	private final JPAQueryFactory queryFactory;


	@Override
	public List<LastChats> findLastChats(List<Long> chatIds) {
		return queryFactory.select(
				Projections.constructor(
						LastChats.class,
						chat.chatRoomId,
						chat.content,
						member.nickname,
						chat.createdAt
				))
				.leftJoin(member).on(chat.memberId.eq(member.id))
				.from(chat)
				.where(chat.id.in(chatIds))
				.fetch();
	}
}

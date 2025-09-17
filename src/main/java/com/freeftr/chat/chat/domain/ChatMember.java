package com.freeftr.chat.chat.domain;

import com.freeftr.chat.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatMember extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_member_id", nullable = false)
	private Long id;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "chat_room_id", nullable = false)
	private Long chatRoomId;

	@Column(name = "display_idx")
	private Long displayIdx;

	@Builder
	public ChatMember(
			Long memberId,
			Long chatRoomId
	) {
		this.memberId = memberId;
		this.chatRoomId = chatRoomId;
		this.displayIdx = 0L;
	}

	public void updateDisplayIdx(Long lastChatId) {
		this.displayIdx = displayIdx;
	}
}

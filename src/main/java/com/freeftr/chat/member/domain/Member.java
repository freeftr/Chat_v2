package com.freeftr.chat.member.domain;

import com.freeftr.chat.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
		name = "member",
		uniqueConstraints = {
				@UniqueConstraint(
						name = "unq_nickname",
						columnNames = "nickname"
				)
		}
)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(name = "nickname")
	private String nickname;

	@Builder
	public Member(String nickname) {
		this.nickname = nickname;
	}
}

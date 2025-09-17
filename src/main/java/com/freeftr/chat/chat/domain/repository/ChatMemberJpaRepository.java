package com.freeftr.chat.chat.domain.repository;

import com.freeftr.chat.chat.domain.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberJpaRepository extends JpaRepository<ChatMember, Long> {
}

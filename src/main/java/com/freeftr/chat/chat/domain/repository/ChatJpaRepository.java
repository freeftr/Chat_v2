package com.freeftr.chat.chat.domain.repository;

import com.freeftr.chat.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatJpaRepository extends JpaRepository<Chat, Long> {
}

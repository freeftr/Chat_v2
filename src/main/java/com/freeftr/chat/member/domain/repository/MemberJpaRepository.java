package com.freeftr.chat.member.domain.repository;

import com.freeftr.chat.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}

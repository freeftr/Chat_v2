package com.freeftr.chat.chat.domain.repository;

import com.freeftr.chat.chat.dto.query.LastChats;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepositoryCustom {

	List<LastChats> findLastChats(List<Long> chatIds);
}

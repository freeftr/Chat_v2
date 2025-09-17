package com.freeftr.chat.chat.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationChannelInterceptor implements ChannelInterceptor {
	private static final String SERVER_ADDRESS = "serverAddress";
	private static final String MEMBER_KEY_PREFIX = "member:";
	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public Message<?> preSend(
			Message<?> message,
			MessageChannel channel
	) {
		var headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (headerAccessor.getCommand() == null) {
			return message;
		}

		if (headerAccessor.getCommand() == StompCommand.CONNECT
				|| headerAccessor.getCommand() == StompCommand.SEND
		) {
			Long memberId = getMemberIdFromWebSockerSession(headerAccessor);
			log.info("{} memberId: {}", headerAccessor.getCommand().toString(), memberId);
		}

		if (headerAccessor.getCommand() == StompCommand.SUBSCRIBE) {
			log.info("SUBSCRIBE");
			Long memberId = getMemberIdFromWebSockerSession(headerAccessor);
			log.info("SUBSCRIBE MEMBER ID: {}", memberId);
			String serverAddress = headerAccessor.getSessionAttributes()
					.get(SERVER_ADDRESS)
					.toString();

			redisTemplate.opsForSet().add(MEMBER_KEY_PREFIX + memberId, serverAddress);
		}

		if (headerAccessor.getCommand() == StompCommand.DISCONNECT) {
			log.info("DISCONNECT");
		}

		return message;
	}

	private static Long getMemberIdFromWebSockerSession(StompHeaderAccessor headerAccessor) {
		return (Long) headerAccessor.getSessionAttributes().get("memberId");
	}
}

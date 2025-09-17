package com.freeftr.chat.common.config;

import com.freeftr.chat.chat.interceptor.AuthenticationChannelInterceptor;
import com.freeftr.chat.chat.interceptor.ClientIpHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final AuthenticationChannelInterceptor authenticationChannelInterceptor;
	private final ClientIpHandshakeInterceptor clientIpHandshakeInterceptor;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws-stomp")
				.setAllowedOrigins("*")
				.addInterceptors(clientIpHandshakeInterceptor);
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		long[] heartbeat = {3000l, 3000l};
		config.setApplicationDestinationPrefixes("/app");
		config.enableSimpleBroker("/topic")
				.setTaskScheduler(heartBeatScheduler())
				.setHeartbeatValue(heartbeat);
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(authenticationChannelInterceptor);
	}

	@Bean
	public TaskScheduler heartBeatScheduler() {
		return new ThreadPoolTaskScheduler();
	}
}

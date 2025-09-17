package com.freeftr.chat.chat.interceptor;

import com.freeftr.chat.common.exception.BadRequestException;
import com.freeftr.chat.common.exception.ErrorCode;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientIpHandshakeInterceptor implements HandshakeInterceptor {

	private static final String SERVER_ADDRESS = "serverAddress";
	private final Environment environment;

	@Override
	public boolean beforeHandshake(
			ServerHttpRequest request,
			ServerHttpResponse response,
			WebSocketHandler wsHandler,
			Map<String, Object> attributes
	) {
		String serverIp = request.getLocalAddress().getAddress().getHostAddress();
		String port = environment.getProperty("server.port");

		HttpServletRequest http = ((ServletServerHttpRequest) request).getServletRequest();
		Long memberId = Long.parseLong(http.getParameter("memberId"));

		if (memberId == null) {
			throw new BadRequestException(ErrorCode.NO_MEMBER_ID_REQUEST);
		}

		log.info("serverIP: {} port: {} memberId: {}", serverIp, port, memberId);
		attributes.put(SERVER_ADDRESS, serverIp + ":" + port);
		attributes.put("memberId", memberId);

		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

	}
}

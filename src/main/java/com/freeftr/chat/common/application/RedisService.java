package com.freeftr.chat.common.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, Object> redisTemplate;

	/**
	 * 값 저장
	 * @param key Redis Key
	 * @param value 저장할 값
	 */
	public void setValue(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 값 저장 (만료시간 포함)
	 * @param key Redis Key
	 * @param value 저장할 값
	 * @param timeout 만료시간
	 * @param unit 시간 단위
	 */
	public void setValue(String key, Object value, long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	/**
	 * 값 조회
	 * @param key Redis Key
	 * @return 저장된 값 (없으면 null)
	 */
	public Object getValue(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 값 삭제
	 * @param key Redis Key
	 */
	public void deleteValue(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 키 존재 여부 확인
	 * @param key Redis Key
	 * @return 존재하면 true
	 */
	public boolean hasKey(String key) {
		Boolean result = redisTemplate.hasKey(key);
		return result != null && result;
	}
}

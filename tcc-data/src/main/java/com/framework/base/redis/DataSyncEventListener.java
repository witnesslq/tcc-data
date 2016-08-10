package com.framework.base.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;

/**
 * 数据同步事件监听器，通过Redis的pub/sub监听biz事件进行本地操作，例如同步本地缓存等
 * 
 * @author Jiangsl
 *
 */
public class DataSyncEventListener implements MessageListener {

	/**
	 * 通过Spring注入的事件处理器列表
	 */
	@Autowired
	private List<DataSyncEventHandler> handlers;

	@Autowired
	private RedisSerializer<String> stringRedisSerializer;

	@Autowired
	private RedisSerializer<Object> jsonRedisSerializer;

	/**
	 * 接收Redis的广播消息，调用事件处理器进行处理
	 * 
	 * @param message
	 *            从Redis事件广播接收的对象
	 * @param pattern
	 *            Redis的广播Channel，在配置文件里通过通配符匹配多个Channel
	 */
	@Override
	public void onMessage(Message message, byte[] pattern) {
		String channel = stringRedisSerializer.deserialize(message.getChannel());
		Object object = jsonRedisSerializer.deserialize(message.getBody());

		// 遍历每一个事件处理器（相同的channel可能会对应多个本地数据同步处理器）
		for (DataSyncEventHandler handler : handlers) {
			if (handler.getChannel().equals(channel)) {
				handler.handle(object);
			}
		}

	}

}

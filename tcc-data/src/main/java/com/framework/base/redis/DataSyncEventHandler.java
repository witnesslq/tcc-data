package com.framework.base.redis;

/**
 * 数据同步事件处理器接口
 * 
 * @author Jiangsl
 *
 */
public interface DataSyncEventHandler {

	/**
	 * 返回对应的Redis Channel，需在Const中定义
	 * 
	 * @return
	 */
	public String getChannel();

	/**
	 * 事件处理的具体逻辑
	 * 
	 * @param object 从Redis事件广播接收的对象
	 */
	public void handle(Object object);
}

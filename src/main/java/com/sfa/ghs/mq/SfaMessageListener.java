package com.sfa.ghs.mq;

import org.apache.log4j.Logger;

import com.sfa.ghs.bar.domain.BarRecordTempTo;
import com.sfa.ghs.bar.service.IBarRecordTempToService;

/**
 * MQ消息自动接收监听器，通过消息转换器：<br/>
 * <ol>
 * <li>非TextMessage消息，均为""，本监听器无需处理，也无需提示，消息直接忽略</li>
 * <li>TextMessage消息，需要XML TO JavaBean，并且将消息保存到JavaBean中</li>
 * </ol>
 * 
 * @since 2015-02-12
 * @author 431520
 * 
 * @see com.sfa.ghs.mq.SfaMessageConverter
 * @see com.sfa.ghs.mq.SfaMessageUtil
 * @see com.sfa.ghs.bar.domain.BarRecordTempTo
 */
public class SfaMessageListener {
	public static final Logger logger = Logger
			.getLogger(SfaMessageListener.class);

	private IBarRecordTempToService barRecordTempToServiceImpl;

	public void setBarRecordTempToServiceImpl(
			IBarRecordTempToService barRecordTempToServiceImpl) {
		this.barRecordTempToServiceImpl = barRecordTempToServiceImpl;
	}

	/**
	 * 接收消息，并将消息保存到数据库
	 * 
	 * @param xml
	 *            总部WQS系统发送的MQ消息
	 */
	public void recevieMessage(String xml) {
		try {
			if (null == xml || "".equals(xml)) {
				throw new SfaMQException("MSG_TYPE_ERROR",
						"Message Type必须为TextMessage, 并且getText()不能为null。");
			}
			BarRecordTempTo record = SfaMessageUtil.fromXML(xml);
			this.barRecordTempToServiceImpl.insert(record);
		} catch (SfaMQException e) {
			// 无需log.error
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("接受并保存MQ消息异常", e);
		}
	}
}

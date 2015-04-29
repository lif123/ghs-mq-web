package com.sfa.ghs.mq;

import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * MQ消息转换器，目前只有接收MQ消息，发送的MQ消息暂不实现。<br/>
 * 接收到的MQ消息特点：
 * <ol>
 * <li>Type: TextMessage</li>
 * <li>内容: XML格式</li>
 * </ol>
 * 所以，本转换器只处理类型为TestMessage的MQ消息，XML解析交由{@link SfaMessageUtil}做进一步处理。
 * 
 * @since 2015-02-12
 * @author 431520
 * 
 * @see com.sfa.ghs.mq.SfaMessageListener
 * @see com.sfa.ghs.mq.SfaMessageUtil
 */
public class SfaMessageConverter implements MessageConverter {
	public static final Logger logger = Logger
			.getLogger(SfaMessageConverter.class);

	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		if (message instanceof BytesMessage) {
			BytesMessage bytesMessage = (BytesMessage) message;
			this.saveMessage(message,
					SfaMessageUtil.fromBytesMessage(bytesMessage));
		} else if (message instanceof MapMessage) {
			MapMessage mapMessage = (MapMessage) message;
			this.saveMessage(message, mapMessage.toString());
		} else if (message instanceof ObjectMessage) {
			ObjectMessage objMessage = (ObjectMessage) message;
			this.saveMessage(message, objMessage.getObject());
		} else if (message instanceof StreamMessage) {
			StreamMessage streamMessage = (StreamMessage) message;
			this.saveMessage(message, streamMessage.toString());
		} else if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			this.saveMessage(message, textMessage.getText());
			return textMessage.getText();
		} else {
			this.saveMessage(message, null);
		}
		return "";
	}

	@Override
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		return null;
	}

	private void saveMessage(Message message, Object msgObj)
			throws JMSException {
		try {
			String context = "Message ID:\t" + message.getJMSMessageID()
					+ "\nMessage Type:\t" + message.getJMSType()
					+ "\nMessage Receive TimeStamp:\t"
					+ message.getJMSTimestamp() + "\nMessage Context:\n"
					+ msgObj + "\n\n";

			FileUtil.writeFile(
					message.getJMSType() + "-" + message.getJMSTimestamp()
							+ ".log", context);
		} catch (IOException e) {
			logger.error("MQ Message写入文件异常，MQ JMS Message ID:"
					+ message.getJMSMessageID());
		}
	}
}

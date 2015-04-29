package com.sfa.ghs.mq;

import java.io.ByteArrayOutputStream;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.dom4j.Document;
import org.dom4j.Element;

import com.sf.module.ws_reader.util.XmlDomRead;
import com.sfa.ghs.bar.domain.BarRecordTempTo;
import com.sfa.ghs.bar.domain.BarRecordTempToVO;

/**
 * MQ工具类，用于解析MQ消息，目前支持的类型：
 * <ol>
 * <li>XML TO BarRecordTempTo</li>
 * <li>BytesMessage TO String</li>
 * </ol>
 * 具体解析由sfa-esb-client_jdk16-x.xx.jar中的{@link XmlDomRead}完成。
 * 
 * @since 2015-02-12
 * @author 431520
 * 
 * @see com.sf.module.security.util.XmlDomRead
 * @see com.sf.module.ghs.domain.BarRecordTempTo
 * @see com.sfa.ghs.mq.sf.module.ghs.mq.SfaMessageListener
 */
public class SfaMessageUtil {
	/**
	 * 将XML转换为BarRecordTempTo
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static BarRecordTempTo fromXML(String xml) throws Exception {
		Document doc = XmlDomRead.getDocument(xml);
		Element e = doc.getRootElement();

		BarRecordTempToVO vo = (BarRecordTempToVO) XmlDomRead.getNodeObj(e,
				BarRecordTempToVO.class);
		BarRecordTempTo obj = new BarRecordTempTo(vo);

		return obj;
	}

	public static String fromBytesMessage(BytesMessage bytesMessage)
			throws JMSException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = bytesMessage.readBytes(buffer)) != -1) {
			byteStream.write(buffer, 0, length);
		}
		return new String(byteStream.toByteArray());
	}
}

package it.nextworks.nsicontest.messagelogger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Holder;

import net.geant.nsicontest.cts.outbound.CtsNotify;
import net.geant.nsicontest.cts.outbound.EventEnum;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.primitives.Primitives;
import com.rabbitmq.client.Channel;


/**
 * this is a simple implementation of a CTS message sender which uses introspection to 
 * figure out what to send inside the JSON message body
 */
public class MessageLogger implements CtsNotify {
	protected enum LOG_LEVEL {
		DEBUG,
		INFO,
		WARN,
		ERROR
	};
	public final static String EXCHANGE_NAME = "cts";
	public final static String MSGKEY_PREFIX = "nsi.cts_v1";
	public final int MAX_DEPTH = 3;
	
    JsonFactory jf = new JsonFactory();
    Channel queueChannel;
    String nsiEmitter;
	

	@SuppressWarnings({ "rawtypes", "unchecked" })	
	public void send(LOG_LEVEL l, EventEnum messageType, Object data, Map attributes) throws Exception {
		ByteArrayOutputStream bufs = new ByteArrayOutputStream();
		Map<String, Object> cache = new HashMap<String, Object>();
		
		JsonGenerator j = jf.createGenerator(bufs, JsonEncoding.UTF8);
		j.useDefaultPrettyPrinter();
		
		serializePojo(j, data, attributes, 0);
		j.close();
			
		
		String msgKey = String.format("%s.%s.%s", MSGKEY_PREFIX, nsiEmitter, messageType);
	    queueChannel.basicPublish(EXCHANGE_NAME, msgKey, null, bufs.toByteArray());
		
	}
	
	@SuppressWarnings("rawtypes")
	private void serialize(JsonGenerator j, Object o, int depth) throws Exception {
		if ((o instanceof Holder)) {
			o = ((Holder)o).value;
		}
		
		if (o == null) {
			j.writeNull();
			return;
		}
			
		if (Primitives.isWrapperType(o.getClass())) {
			j.writeObject(o);
			return;
		}
		
		if (o instanceof String) {
			j.writeString((String)o);
			return;
		}
		
		serializePojo(j, o, null, depth+1);
	}
	
	@SuppressWarnings("rawtypes")
	private void serializePojo(JsonGenerator j, Object o, Map<String, Object> attributes, int depth) throws Exception {
		if (depth >= MAX_DEPTH) {
			j.writeNull();
			return;
		}
		if (attributes == null)
			attributes = new HashMap<String, Object>();
		
		Map<String, Object> cache = new HashMap<String, Object>();

		if ((o instanceof Holder)) {
			o = ((Holder)o).value;
		}
		
		for (Map.Entry<String, String> entry: BeanUtils.describe(o).entrySet()) {
			if ("class".equals(entry.getKey()))
				continue;
			Object val;
			try {
				val = PropertyUtils.getProperty(o, entry.getKey());
				cache.put(entry.getKey(), val);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (Map.Entry<String, Object> entry: attributes.entrySet()) {
			if (cache.get(entry.getKey()) == null)
				cache.put(entry.getKey(), entry.getValue());
		}
		
		j.writeStartObject();
		try {
			for (Map.Entry<String, Object> entry: cache.entrySet()) {
				j.writeFieldName(entry.getKey());
				serialize(j, entry.getValue(), depth);
			}
			
		}
		finally {
			j.writeEndObject();
		}
		
	}
	
	
	
	public MessageLogger(String nsiEmitter, Channel queueChannel) throws IOException {
		super();
		this.nsiEmitter = nsiEmitter;
		this.queueChannel = queueChannel;;
		this.queueChannel.exchangeDeclare(EXCHANGE_NAME, "topic");
	}

	@Override
	public void debug(EventEnum messageType, Object data) throws Exception {
		send(LOG_LEVEL.DEBUG, messageType, data, null);
	}

	@Override
	public void debug(EventEnum messageType, Object data, Map attributes)
			throws Exception {
		send(LOG_LEVEL.DEBUG, messageType, data, attributes);
	}

	@Override
	public void info(EventEnum messageType, Object data) throws Exception {
		send(LOG_LEVEL.INFO, messageType, data, null);
	}

	@Override
	public void info(EventEnum messageType, Object data, Map attributes)
			throws Exception {
		send(LOG_LEVEL.INFO, messageType, data, attributes);
	}

	@Override
	public void warn(EventEnum messageType, Object data) throws Exception {
		send(LOG_LEVEL.WARN, messageType, data, null);
	}

	@Override
	public void warn(EventEnum messageType, Object data, Map attributes)
			throws Exception {
		send(LOG_LEVEL.WARN, messageType, data, attributes);
	}

	@Override
	public void error(EventEnum messageType, Object data) throws Exception {
		send(LOG_LEVEL.ERROR, messageType, data, null);
	}

	@Override
	public void error(EventEnum messageType, Object data, Map attributes)
			throws Exception {
		send(LOG_LEVEL.ERROR, messageType, data, attributes);
	}
	
	
}

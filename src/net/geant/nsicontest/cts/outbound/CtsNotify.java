/**
 * 
 */
package net.geant.nsicontest.cts.outbound;

import java.util.Map;

/**
 * This interface is used by NSI-Ri to put json notification messages (i.e. scenario progress)
 * on Rabbit MQ.
 */
public interface CtsNotify {
	void debug(EventEnum messageType, Object data) throws Exception;
	void debug(EventEnum messageType, Object data, Map attributes) throws Exception;
	void info(EventEnum messageType, Object data) throws Exception;
	void info(EventEnum messageType, Object data, Map attributes) throws Exception;
	void warn(EventEnum messageType, Object data) throws Exception;
	void warn(EventEnum messageType, Object data, Map attributes) throws Exception;
	void error(EventEnum messageType, Object data) throws Exception;
	void error(EventEnum messageType, Object data, Map attributes) throws Exception;
}

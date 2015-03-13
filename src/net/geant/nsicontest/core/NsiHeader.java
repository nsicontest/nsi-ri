/**
 * 
 */
package net.geant.nsicontest.core;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Helper class designated to validate CommonHeaderType.
 */
final public class NsiHeader {
	
	/**
	 * Checks header for replyTo occurrence. Note that it is not resolved to real address.
	 * @param header
	 * @return
	 */
	static public boolean hasReplyTo(Holder<CommonHeaderType> header) {
		
		if (header == null || header.value == null)
			return false;
		
		String replyTo = header.value.getReplyTo();
		
		if (replyTo == null || replyTo.isEmpty())
			return false;
		
		// TODO check with regex if it resembles an url
		return true;		
	}

	/**
	 * Gets replyTo field out of header
	 * @param header
	 * @return
	 */
	static public String getReplyTo(Holder<CommonHeaderType> header) {
		
		if (header == null || header.value == null)
			return null;
		
		return header.value.getReplyTo();
	}
	
	static private boolean isNullOrEmpty(String s) {
		
		return s == null || s.isEmpty();		
	}
	
	/**
	 * Validates header.
	 * TODO improvie NsiError to add more details on error.
	 * @param holder
	 * @param connectionId
	 * @param nsaId
	 * @param serviceType
	 * @return
	 */
	static public NsiError check(final Holder<CommonHeaderType> holder, String connectionId, String nsaId, String serviceType) {
		
		if (holder == null) 
			return new NsiError(nsaId, connectionId, serviceType, NsiErrorId.MISSING_PARAMETER);
				
		final CommonHeaderType header = holder.value;
		
		if (header == null) 
			return new NsiError(nsaId, connectionId, serviceType, NsiErrorId.MISSING_PARAMETER);
		
		if (isNullOrEmpty(header.getProtocolVersion())) {
			return new NsiError(nsaId, connectionId, serviceType, NsiErrorId.MISSING_PARAMETER);
        }

        if (!header.getProtocolVersion().equalsIgnoreCase(Nsi.REQUESTER_VERSION)) {
        	return new NsiError(nsaId, connectionId, serviceType, NsiErrorId.MISSING_PARAMETER);
		}

        if (isNullOrEmpty(header.getCorrelationId())) {
        	return new NsiError(nsaId, connectionId, serviceType, NsiErrorId.MISSING_PARAMETER);
        }

        if (isNullOrEmpty(header.getProviderNSA())) {
        	return new NsiError(nsaId, connectionId, serviceType, NsiErrorId.MISSING_PARAMETER);
        }

        if (!header.getProviderNSA().equals(nsaId)) {
        	return new NsiError(nsaId, connectionId, serviceType, NsiErrorId.MISSING_PARAMETER);
        }

        if (isNullOrEmpty(header.getRequesterNSA())) {
        	return new NsiError(nsaId, connectionId, serviceType, NsiErrorId.MISSING_PARAMETER);
		}

		// we should check if requester can be found in the topology service
		return null; // no error
	}
}

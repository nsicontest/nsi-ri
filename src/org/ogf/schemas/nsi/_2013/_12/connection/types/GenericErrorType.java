
package org.ogf.schemas.nsi._2013._12.connection.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType;


/**
 * 
 *                 A generic "Error" message type sent in response to a previous
 *                 protocol "Request" message.  An error message is generated when
 *                 an error condition occurs that does not result in a state
 *                 machine transition.  This type is used in response to all
 *                 request types that can return an error.
 *                 
 *                 The correlationId carried in the NSI header will identify the
 *                 original request associated with this error message.
 *                 
 *                 Elements:
 *                 
 *                 serviceException - Specific error condition indicating the
 *                 reason for the failure.
 *             
 * 
 * <p>Java class for GenericErrorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GenericErrorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serviceException" type="{http://schemas.ogf.org/nsi/2013/12/framework/types}ServiceExceptionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericErrorType", propOrder = {
    "serviceException"
})
public class GenericErrorType {

    @XmlElement(required = true)
    protected ServiceExceptionType serviceException;

    /**
     * Gets the value of the serviceException property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceExceptionType }
     *     
     */
    public ServiceExceptionType getServiceException() {
        return serviceException;
    }

    /**
     * Sets the value of the serviceException property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceExceptionType }
     *     
     */
    public void setServiceException(ServiceExceptionType value) {
        this.serviceException = value;
    }

}

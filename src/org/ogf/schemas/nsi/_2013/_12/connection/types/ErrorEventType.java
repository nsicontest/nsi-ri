
package org.ogf.schemas.nsi._2013._12.connection.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType;
import org.ogf.schemas.nsi._2013._12.framework.types.TypeValuePairListType;


/**
 * 
 *                 Type definition for an autonomous message issued from a
 *                 Provider NSA to a Requester NSA when an existing reservation
 *                 encounters an autonomous error condition such as being
 *                 administratively terminated before the reservation's scheduled
 *                 end-time.
 *                 
 *                 Elements:
 * 
 *                 connectionId - The Provider NSA assigned connectionId that this
 *                 notification is against.
 *                 
 *                 notificationId - A notification identifier that is unique in the
 *                 context of a connectionId.  This is a linearly increasing
 *                 identifier that can be used for ordering notifications in the
 *                 context of the connectionId.
 *                 
 *                 timeStamp - Time the event was generated on the originating NSA.
 * 
 *                 event - The type of event that generated this notification.
 *                 
 *                 originatingConnectionId - The connectionId that triggered the
 *                 error event.
 *                 
 *                 originatingNSA - The NSA originating the error event.
 *                 
 *                 additionalInfo - Type/value pairs that can provide additional
 *                 error context as needed.
 *                 
 *                 serviceException - Specific error condition - the reason for the
 *                 generation of the error event.
 *             
 * 
 * <p>Java class for ErrorEventType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorEventType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.ogf.org/nsi/2013/12/connection/types}NotificationBaseType">
 *       &lt;sequence>
 *         &lt;element name="event" type="{http://schemas.ogf.org/nsi/2013/12/connection/types}EventEnumType"/>
 *         &lt;element name="originatingConnectionId" type="{http://schemas.ogf.org/nsi/2013/12/framework/types}ConnectionIdType"/>
 *         &lt;element name="originatingNSA" type="{http://schemas.ogf.org/nsi/2013/12/framework/types}NsaIdType"/>
 *         &lt;element name="additionalInfo" type="{http://schemas.ogf.org/nsi/2013/12/framework/types}TypeValuePairListType" minOccurs="0"/>
 *         &lt;element name="serviceException" type="{http://schemas.ogf.org/nsi/2013/12/framework/types}ServiceExceptionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorEventType", propOrder = {
    "event",
    "originatingConnectionId",
    "originatingNSA",
    "additionalInfo",
    "serviceException"
})
public class ErrorEventType
    extends NotificationBaseType
{

    @XmlElement(required = true)
    protected EventEnumType event;
    @XmlElement(required = true)
    protected String originatingConnectionId;
    @XmlElement(required = true)
    protected String originatingNSA;
    protected TypeValuePairListType additionalInfo;
    protected ServiceExceptionType serviceException;

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link EventEnumType }
     *     
     */
    public EventEnumType getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventEnumType }
     *     
     */
    public void setEvent(EventEnumType value) {
        this.event = value;
    }

    /**
     * Gets the value of the originatingConnectionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginatingConnectionId() {
        return originatingConnectionId;
    }

    /**
     * Sets the value of the originatingConnectionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginatingConnectionId(String value) {
        this.originatingConnectionId = value;
    }

    /**
     * Gets the value of the originatingNSA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginatingNSA() {
        return originatingNSA;
    }

    /**
     * Sets the value of the originatingNSA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginatingNSA(String value) {
        this.originatingNSA = value;
    }

    /**
     * Gets the value of the additionalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TypeValuePairListType }
     *     
     */
    public TypeValuePairListType getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the value of the additionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeValuePairListType }
     *     
     */
    public void setAdditionalInfo(TypeValuePairListType value) {
        this.additionalInfo = value;
    }

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

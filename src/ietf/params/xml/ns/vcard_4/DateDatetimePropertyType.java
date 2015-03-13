
package ietf.params.xml.ns.vcard_4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DateDatetimePropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DateDatetimePropertyType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ietf:params:xml:ns:vcard-4.0}BasePropertyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{urn:ietf:params:xml:ns:vcard-4.0}date-time"/>
 *           &lt;element ref="{urn:ietf:params:xml:ns:vcard-4.0}date"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateDatetimePropertyType", propOrder = {
    "dateTime",
    "date"
})
@XmlSeeAlso({
    BdayPropType.class,
    AnniversaryPropType.class
})
public class DateDatetimePropertyType
    extends BasePropertyType
{

    @XmlElement(name = "date-time")
    protected String dateTime;
    protected String date;

    /**
     * Gets the value of the dateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateTime(String value) {
        this.dateTime = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

}

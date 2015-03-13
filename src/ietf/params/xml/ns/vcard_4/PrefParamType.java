
package ietf.params.xml.ns.vcard_4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for prefParamType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="prefParamType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ietf:params:xml:ns:vcard-4.0}BaseParameterType">
 *       &lt;sequence>
 *         &lt;element name="integer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prefParamType", propOrder = {
    "integer"
})
public class PrefParamType
    extends BaseParameterType
{

    @XmlElement(required = true)
    protected String integer;

    /**
     * Gets the value of the integer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInteger() {
        return integer;
    }

    /**
     * Sets the value of the integer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInteger(String value) {
        this.integer = value;
    }

}


package ietf.params.xml.ns.vcard_4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UriPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UriPropertyType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ietf:params:xml:ns:vcard-4.0}BasePropertyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:ietf:params:xml:ns:vcard-4.0}uri"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UriPropertyType", propOrder = {
    "uri"
})
@XmlSeeAlso({
    UidPropType.class,
    ImppPropType.class,
    UrlPropType.class,
    KeyPropType.class,
    PhotoPropType.class,
    MemberPropType.class,
    GeoPropType.class,
    FburlPropType.class
})
public class UriPropertyType
    extends BasePropertyType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String uri;

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

}

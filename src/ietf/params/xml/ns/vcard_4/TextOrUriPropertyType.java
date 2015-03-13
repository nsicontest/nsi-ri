
package ietf.params.xml.ns.vcard_4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TextOrUriPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TextOrUriPropertyType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ietf:params:xml:ns:vcard-4.0}BasePropertyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{urn:ietf:params:xml:ns:vcard-4.0}text"/>
 *           &lt;element ref="{urn:ietf:params:xml:ns:vcard-4.0}uri"/>
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
@XmlType(name = "TextOrUriPropertyType", propOrder = {
    "text",
    "uri"
})
@XmlSeeAlso({
    RelatedPropType.class
})
public class TextOrUriPropertyType
    extends BasePropertyType
{

    protected String text;
    @XmlSchemaType(name = "anyURI")
    protected String uri;

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

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

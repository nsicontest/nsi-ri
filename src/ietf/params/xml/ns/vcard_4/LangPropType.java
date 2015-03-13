
package ietf.params.xml.ns.vcard_4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for langPropType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="langPropType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ietf:params:xml:ns:vcard-4.0}BasePropertyType">
 *       &lt;sequence>
 *         &lt;element name="language-tag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "langPropType", propOrder = {
    "languageTag"
})
public class LangPropType
    extends BasePropertyType
{

    @XmlElement(name = "language-tag", required = true)
    protected String languageTag;

    /**
     * Gets the value of the languageTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageTag() {
        return languageTag;
    }

    /**
     * Sets the value of the languageTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageTag(String value) {
        this.languageTag = value;
    }

}

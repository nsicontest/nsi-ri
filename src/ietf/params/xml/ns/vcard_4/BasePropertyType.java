
package ietf.params.xml.ns.vcard_4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BasePropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BasePropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:ietf:params:xml:ns:vcard-4.0}parameters" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BasePropertyType", propOrder = {
    "parameters"
})
@XmlSeeAlso({
    NPropType.class,
    DateDatetimePropertyType.class,
    TextListPropertyType.class,
    NonEmptyTextListPropertyType.class,
    AffiliationPropType.class,
    TextOrUriPropertyType.class,
    RevPropType.class,
    UriPropertyType.class,
    TextPropertyType.class,
    LangPropType.class,
    AdrPropType.class
})
public abstract class BasePropertyType {

    protected ArrayOfParameters parameters;

    /**
     * Gets the value of the parameters property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfParameters }
     *     
     */
    public ArrayOfParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfParameters }
     *     
     */
    public void setParameters(ArrayOfParameters value) {
        this.parameters = value;
    }

}

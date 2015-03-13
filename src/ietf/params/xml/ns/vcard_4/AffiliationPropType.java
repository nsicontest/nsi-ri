
package ietf.params.xml.ns.vcard_4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for affiliationPropType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="affiliationPropType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ietf:params:xml:ns:vcard-4.0}BasePropertyType">
 *       &lt;sequence>
 *         &lt;element name="org" type="{urn:ietf:params:xml:ns:vcard-4.0}NonEmptyTextListPropertyType"/>
 *         &lt;element name="logo" type="{urn:ietf:params:xml:ns:vcard-4.0}UriPropertyType" minOccurs="0"/>
 *         &lt;element name="role" type="{urn:ietf:params:xml:ns:vcard-4.0}TextPropertyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="related" type="{urn:ietf:params:xml:ns:vcard-4.0}relatedPropType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="adr" type="{urn:ietf:params:xml:ns:vcard-4.0}adrPropType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" fixed="Affiliation" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "affiliationPropType", propOrder = {
    "org",
    "logo",
    "role",
    "related",
    "adr"
})
public class AffiliationPropType
    extends BasePropertyType
{

    @XmlElement(required = true)
    protected NonEmptyTextListPropertyType org;
    protected UriPropertyType logo;
    protected List<TextPropertyType> role;
    protected List<RelatedPropType> related;
    protected List<AdrPropType> adr;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the org property.
     * 
     * @return
     *     possible object is
     *     {@link NonEmptyTextListPropertyType }
     *     
     */
    public NonEmptyTextListPropertyType getOrg() {
        return org;
    }

    /**
     * Sets the value of the org property.
     * 
     * @param value
     *     allowed object is
     *     {@link NonEmptyTextListPropertyType }
     *     
     */
    public void setOrg(NonEmptyTextListPropertyType value) {
        this.org = value;
    }

    /**
     * Gets the value of the logo property.
     * 
     * @return
     *     possible object is
     *     {@link UriPropertyType }
     *     
     */
    public UriPropertyType getLogo() {
        return logo;
    }

    /**
     * Sets the value of the logo property.
     * 
     * @param value
     *     allowed object is
     *     {@link UriPropertyType }
     *     
     */
    public void setLogo(UriPropertyType value) {
        this.logo = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the role property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextPropertyType }
     * 
     * 
     */
    public List<TextPropertyType> getRole() {
        if (role == null) {
            role = new ArrayList<TextPropertyType>();
        }
        return this.role;
    }

    /**
     * Gets the value of the related property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the related property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelated().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedPropType }
     * 
     * 
     */
    public List<RelatedPropType> getRelated() {
        if (related == null) {
            related = new ArrayList<RelatedPropType>();
        }
        return this.related;
    }

    /**
     * Gets the value of the adr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdrPropType }
     * 
     * 
     */
    public List<AdrPropType> getAdr() {
        if (adr == null) {
            adr = new ArrayList<AdrPropType>();
        }
        return this.adr;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        if (name == null) {
            return "Affiliation";
        } else {
            return name;
        }
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}

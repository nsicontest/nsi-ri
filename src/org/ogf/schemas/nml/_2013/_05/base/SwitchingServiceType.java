
package org.ogf.schemas.nml._2013._05.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for SwitchingServiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SwitchingServiceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.ogf.org/nml/2013/05/base#}NetworkObject">
 *       &lt;sequence>
 *         &lt;element name="Relation" type="{http://schemas.ogf.org/nml/2013/05/base#}SwitchingServiceRelationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="encoding" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="labelSwapping" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="labelType" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SwitchingServiceType", propOrder = {
    "relation",
    "any"
})
public class SwitchingServiceType
    extends NetworkObject
{

    @XmlElement(name = "Relation")
    protected List<SwitchingServiceRelationType> relation;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "encoding")
    @XmlSchemaType(name = "anyURI")
    protected String encoding;
    @XmlAttribute(name = "labelSwapping")
    protected Boolean labelSwapping;
    @XmlAttribute(name = "labelType")
    @XmlSchemaType(name = "anyURI")
    protected String labelType;

    /**
     * Gets the value of the relation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SwitchingServiceRelationType }
     * 
     * 
     */
    public List<SwitchingServiceRelationType> getRelation() {
        if (relation == null) {
            relation = new ArrayList<SwitchingServiceRelationType>();
        }
        return this.relation;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

    /**
     * Gets the value of the encoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the value of the encoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncoding(String value) {
        this.encoding = value;
    }

    /**
     * Gets the value of the labelSwapping property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLabelSwapping() {
        return labelSwapping;
    }

    /**
     * Sets the value of the labelSwapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLabelSwapping(Boolean value) {
        this.labelSwapping = value;
    }

    /**
     * Gets the value of the labelType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelType() {
        return labelType;
    }

    /**
     * Sets the value of the labelType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelType(String value) {
        this.labelType = value;
    }

}

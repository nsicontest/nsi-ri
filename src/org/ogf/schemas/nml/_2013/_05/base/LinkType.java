
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
 * <p>Java class for LinkType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LinkType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.ogf.org/nml/2013/05/base#}NetworkObject">
 *       &lt;sequence>
 *         &lt;group ref="{http://schemas.ogf.org/nml/2013/05/base#}BaseLinkContent"/>
 *         &lt;element name="Relation" type="{http://schemas.ogf.org/nml/2013/05/base#}LinkRelationType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="encoding" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="noReturnTraffic" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LinkType", propOrder = {
    "label",
    "any",
    "relation"
})
public class LinkType
    extends NetworkObject
{

    @XmlElement(name = "Label")
    protected LabelType label;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlElement(name = "Relation")
    protected List<LinkRelationType> relation;
    @XmlAttribute(name = "encoding")
    @XmlSchemaType(name = "anyURI")
    protected String encoding;
    @XmlAttribute(name = "noReturnTraffic")
    protected Boolean noReturnTraffic;

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link LabelType }
     *     
     */
    public LabelType getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelType }
     *     
     */
    public void setLabel(LabelType value) {
        this.label = value;
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
     * {@link LinkRelationType }
     * 
     * 
     */
    public List<LinkRelationType> getRelation() {
        if (relation == null) {
            relation = new ArrayList<LinkRelationType>();
        }
        return this.relation;
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
     * Gets the value of the noReturnTraffic property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoReturnTraffic() {
        return noReturnTraffic;
    }

    /**
     * Sets the value of the noReturnTraffic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoReturnTraffic(Boolean value) {
        this.noReturnTraffic = value;
    }

}

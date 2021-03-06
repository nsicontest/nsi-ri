
package org.ogf.schemas.nml._2013._05.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PortGroupRelationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PortGroupRelationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://schemas.ogf.org/nml/2013/05/base#}PortGroup" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://schemas.ogf.org/nml/2013/05/base#}LinkGroup" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="http://schemas.ogf.org/nml/2013/05/base#isAlias"/>
 *             &lt;enumeration value="http://schemas.ogf.org/nml/2013/05/base#isSink"/>
 *             &lt;enumeration value="http://schemas.ogf.org/nml/2013/05/base#isSource"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PortGroupRelationType", propOrder = {
    "portGroup",
    "linkGroup"
})
public class PortGroupRelationType {

    @XmlElement(name = "PortGroup")
    protected List<PortGroupType> portGroup;
    @XmlElement(name = "LinkGroup")
    protected List<LinkGroupType> linkGroup;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    /**
     * Gets the value of the portGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the portGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPortGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PortGroupType }
     * 
     * 
     */
    public List<PortGroupType> getPortGroup() {
        if (portGroup == null) {
            portGroup = new ArrayList<PortGroupType>();
        }
        return this.portGroup;
    }

    /**
     * Gets the value of the linkGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the linkGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLinkGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LinkGroupType }
     * 
     * 
     */
    public List<LinkGroupType> getLinkGroup() {
        if (linkGroup == null) {
            linkGroup = new ArrayList<LinkGroupType>();
        }
        return this.linkGroup;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}

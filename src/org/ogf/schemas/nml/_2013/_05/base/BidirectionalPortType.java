
package org.ogf.schemas.nml._2013._05.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for BidirectionalPortType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BidirectionalPortType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.ogf.org/nml/2013/05/base#}NetworkObject">
 *       &lt;sequence>
 *         &lt;group ref="{http://schemas.ogf.org/nml/2013/05/base#}BaseBidirectionalPort"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BidirectionalPortType", propOrder = {
    "rest"
})
public class BidirectionalPortType
    extends NetworkObject
{

    @XmlElementRefs({
        @XmlElementRef(name = "Port", namespace = "http://schemas.ogf.org/nml/2013/05/base#", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "PortGroup", namespace = "http://schemas.ogf.org/nml/2013/05/base#", type = JAXBElement.class, required = false)
    })
    @XmlAnyElement(lax = true)
    protected List<Object> rest;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Port" is used by two different parts of a schema. See: 
     * line 524 of file:/C:/Users/PCSS/Documents/Workspace%20Nsi/Nsi%20Final/etc/wsdl/TopologyService/nmlbase.xsd
     * line 523 of file:/C:/Users/PCSS/Documents/Workspace%20Nsi/Nsi%20Final/etc/wsdl/TopologyService/nmlbase.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the rest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link JAXBElement }{@code <}{@link PortType }{@code >}
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link PortGroupType }{@code >}
     * 
     * 
     */
    public List<Object> getRest() {
        if (rest == null) {
            rest = new ArrayList<Object>();
        }
        return this.rest;
    }

}

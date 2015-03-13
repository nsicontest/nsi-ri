
package org.ogf.schemas.nml._2013._05.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for LinkGroupType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LinkGroupType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.ogf.org/nml/2013/05/base#}NetworkObject">
 *       &lt;sequence>
 *         &lt;group ref="{http://schemas.ogf.org/nml/2013/05/base#}BaseLinkGroup"/>
 *         &lt;element name="Relation" type="{http://schemas.ogf.org/nml/2013/05/base#}LinkGroupRelationType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "LinkGroupType", propOrder = {
    "labelGroup",
    "link",
    "linkGroup",
    "relation",
    "any"
})
public class LinkGroupType
    extends NetworkObject
{

    @XmlElement(name = "LabelGroup")
    protected List<LabelGroupType> labelGroup;
    @XmlElement(name = "Link")
    protected List<LinkType> link;
    @XmlElement(name = "LinkGroup")
    protected List<LinkGroupType> linkGroup;
    @XmlElement(name = "Relation")
    protected List<LinkGroupRelationType> relation;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the labelGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the labelGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLabelGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LabelGroupType }
     * 
     * 
     */
    public List<LabelGroupType> getLabelGroup() {
        if (labelGroup == null) {
            labelGroup = new ArrayList<LabelGroupType>();
        }
        return this.labelGroup;
    }

    /**
     * Gets the value of the link property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the link property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LinkType }
     * 
     * 
     */
    public List<LinkType> getLink() {
        if (link == null) {
            link = new ArrayList<LinkType>();
        }
        return this.link;
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
     * {@link LinkGroupRelationType }
     * 
     * 
     */
    public List<LinkGroupRelationType> getRelation() {
        if (relation == null) {
            relation = new ArrayList<LinkGroupRelationType>();
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

}

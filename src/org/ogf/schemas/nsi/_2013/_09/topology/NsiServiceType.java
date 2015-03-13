
package org.ogf.schemas.nsi._2013._09.topology;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.ogf.schemas.nml._2013._05.base.NetworkObject;


/**
 * <p>Java class for NsiServiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NsiServiceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.ogf.org/nml/2013/05/base#}NetworkObject">
 *       &lt;sequence>
 *         &lt;group ref="{http://schemas.ogf.org/nsi/2013/09/topology#}BaseNsiServiceContent"/>
 *         &lt;element name="Relation" type="{http://schemas.ogf.org/nsi/2013/09/topology#}NsiServiceRelationType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NsiServiceType", propOrder = {
    "link",
    "describedBy",
    "type",
    "relation"
})
public class NsiServiceType
    extends NetworkObject
{

    @XmlSchemaType(name = "anyURI")
    protected String link;
    @XmlSchemaType(name = "anyURI")
    protected String describedBy;
    protected String type;
    @XmlElement(name = "Relation")
    protected List<NsiServiceRelationType> relation;

    /**
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLink(String value) {
        this.link = value;
    }

    /**
     * Gets the value of the describedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescribedBy() {
        return describedBy;
    }

    /**
     * Sets the value of the describedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescribedBy(String value) {
        this.describedBy = value;
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
     * {@link NsiServiceRelationType }
     * 
     * 
     */
    public List<NsiServiceRelationType> getRelation() {
        if (relation == null) {
            relation = new ArrayList<NsiServiceRelationType>();
        }
        return this.relation;
    }

}

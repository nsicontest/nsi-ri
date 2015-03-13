
package org.ogf.schemas.nsi._2013._09.topology;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.ogf.schemas.nml._2013._05.base.NetworkObject;
import org.ogf.schemas.nml._2013._05.base.TopologyType;


/**
 * <p>Java class for NSAType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NSAType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.ogf.org/nml/2013/05/base#}NetworkObject">
 *       &lt;sequence>
 *         &lt;element ref="{http://schemas.ogf.org/nsi/2013/09/topology#}Service" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://schemas.ogf.org/nsi/2013/09/topology#}Relation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://schemas.ogf.org/nml/2013/05/base#}Topology" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="extension" type="{http://schemas.ogf.org/nsi/2013/09/topology#}ExtensionType" minOccurs="0"/>
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
@XmlType(name = "NSAType", propOrder = {
    "service",
    "relation",
    "topology",
    "extension"
})
public class NSAType
    extends NetworkObject
{

    @XmlElement(name = "Service")
    protected List<ServiceType> service;
    @XmlElement(name = "Relation")
    protected List<NSARelationType> relation;
    @XmlElement(name = "Topology", namespace = "http://schemas.ogf.org/nml/2013/05/base#")
    protected List<TopologyType> topology;
    protected ExtensionType extension;

    /**
     * Gets the value of the service property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the service property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceType }
     * 
     * 
     */
    public List<ServiceType> getService() {
        if (service == null) {
            service = new ArrayList<ServiceType>();
        }
        return this.service;
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
     * {@link NSARelationType }
     * 
     * 
     */
    public List<NSARelationType> getRelation() {
        if (relation == null) {
            relation = new ArrayList<NSARelationType>();
        }
        return this.relation;
    }

    /**
     * Gets the value of the topology property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the topology property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTopology().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TopologyType }
     * 
     * 
     */
    public List<TopologyType> getTopology() {
        if (topology == null) {
            topology = new ArrayList<TopologyType>();
        }
        return this.topology;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionType }
     *     
     */
    public ExtensionType getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *     
     */
    public void setExtension(ExtensionType value) {
        this.extension = value;
    }

}

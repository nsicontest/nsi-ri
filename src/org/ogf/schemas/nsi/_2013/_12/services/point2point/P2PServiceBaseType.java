
package org.ogf.schemas.nsi._2013._12.services.point2point;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.ogf.schemas.nsi._2013._12.services.types.DirectionalityType;
import org.ogf.schemas.nsi._2013._12.services.types.StpListType;
import org.ogf.schemas.nsi._2013._12.services.types.TypeValueType;
import org.w3c.dom.Element;


/**
 * 
 *                 Type defining a generic point-to-point service specification.
 *                 At the moment this type supports a unidirectional or
 *                 bidirectional service.
 *                 
 *                 Elements:
 *                 
 *                 capacity - Capacity of the service.
 *                 
 *                 directionality - The (uni or bi) directionality of the service.
 *                 
 *                 symmetricPath - An indication that both directions of a
 *                 bidirectional circuit must follow the same path.  Only
 *                 applicable when directionality is "Bidirectional".  If not
 *                 specified then value is assumed to be false.
 *                 
 *                 sourceSTP - Source STP identifier of the service.
 *                 
 *                 destSTP - Destination STP identifier of the service.
 *                 
 *                 ero - A hop-by-hop ordered list of STP from sourceSTP to
 *                 destSTP representing a path that the connection must follow.
 *                 This list does not include sourceSTP or destSTP.
 *                 
 *                 parameter - A flexible non-specific parameters definition
 *                 allowing for specification of parameters in the Service
 *                 Definition that are not defined directly in the service
 *                 specific schema.
 *                 
 *                 ##other - For future expansion and extensibility.
 *             
 * 
 * <p>Java class for P2PServiceBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="P2PServiceBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="directionality" type="{http://schemas.ogf.org/nsi/2013/12/services/types}DirectionalityType"/>
 *         &lt;element name="symmetricPath" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sourceSTP" type="{http://schemas.ogf.org/nsi/2013/12/services/types}StpIdType"/>
 *         &lt;element name="destSTP" type="{http://schemas.ogf.org/nsi/2013/12/services/types}StpIdType"/>
 *         &lt;element name="ero" type="{http://schemas.ogf.org/nsi/2013/12/services/types}StpListType" minOccurs="0"/>
 *         &lt;element name="parameter" type="{http://schemas.ogf.org/nsi/2013/12/services/types}TypeValueType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "P2PServiceBaseType", propOrder = {
    "capacity",
    "directionality",
    "symmetricPath",
    "sourceSTP",
    "destSTP",
    "ero",
    "parameter",
    "any"
})
public class P2PServiceBaseType {

    protected long capacity;
    @XmlElement(required = true, defaultValue = "Bidirectional")
    protected DirectionalityType directionality;
    protected Boolean symmetricPath;
    @XmlElement(required = true)
    protected String sourceSTP;
    @XmlElement(required = true)
    protected String destSTP;
    protected StpListType ero;
    protected List<TypeValueType> parameter;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the capacity property.
     * 
     */
    public long getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     */
    public void setCapacity(long value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the directionality property.
     * 
     * @return
     *     possible object is
     *     {@link DirectionalityType }
     *     
     */
    public DirectionalityType getDirectionality() {
        return directionality;
    }

    /**
     * Sets the value of the directionality property.
     * 
     * @param value
     *     allowed object is
     *     {@link DirectionalityType }
     *     
     */
    public void setDirectionality(DirectionalityType value) {
        this.directionality = value;
    }

    /**
     * Gets the value of the symmetricPath property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSymmetricPath() {
        return symmetricPath;
    }

    /**
     * Sets the value of the symmetricPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSymmetricPath(Boolean value) {
        this.symmetricPath = value;
    }

    /**
     * Gets the value of the sourceSTP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSTP() {
        return sourceSTP;
    }

    /**
     * Sets the value of the sourceSTP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSTP(String value) {
        this.sourceSTP = value;
    }

    /**
     * Gets the value of the destSTP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestSTP() {
        return destSTP;
    }

    /**
     * Sets the value of the destSTP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestSTP(String value) {
        this.destSTP = value;
    }

    /**
     * Gets the value of the ero property.
     * 
     * @return
     *     possible object is
     *     {@link StpListType }
     *     
     */
    public StpListType getEro() {
        return ero;
    }

    /**
     * Sets the value of the ero property.
     * 
     * @param value
     *     allowed object is
     *     {@link StpListType }
     *     
     */
    public void setEro(StpListType value) {
        this.ero = value;
    }

    /**
     * Gets the value of the parameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeValueType }
     * 
     * 
     */
    public List<TypeValueType> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<TypeValueType>();
        }
        return this.parameter;
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


package org.ogf.schemas.nsi._2013._12.services.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * 
 *                 Type defining the structure and content of a Service
 *                 definition document.
 *                 
 *                 Attributes:
 *                 
 *                 id - A service definition must have a unique identifier within
 *                 the network sourcing the definition.
 *                 
 *                 Elements:
 *                 
 *                 comment - A comment describes the basic service offered in this
 *                 service definition.  Any differences from the global service
 *                 definition should be identified here.
 *                 
 *                 serviceType - This is the standard service type as defined in
 *                 the associated global service definition. We allocate a
 *                 standard namespace for each template so they are globally
 *                 unique.
 *                 
 *                 adaptation - If this element is present then the Service Definition
 *                 is providing service adaptation functionality.  A service
 *                 adaptation allows services from two incompatible Service Domains
 *                 to be interconnected through a special adaptation function.  This
 *                 element will identify the type of adaptation, and the serviceTypes
 *                 compatible with the adaptation.
 *                 
 *                 schema - This is the schema elements specified in a reservation.
 *                 There can be multiple schema entries here for services if they
 *                 requiring multiple schema in a reserve request.
 *                 
 *                 parameter - Parameter definitions for the service and their
 *                 values.  These reflect the XML schema definitions and any local
 *                 range restrictions.
 *                 
 *                 attribute - Attribute definitions for the service and their
 *                 values.  Attributes are aspects of the service that are not
 *                 specified in the XML schema for the service.
 *                 
 *                 error - Errors defined for this service.             
 *             
 * 
 * <p>Java class for ServiceDefinitionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceDefinitionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adaptation" type="{http://schemas.ogf.org/nsi/2013/12/services/definition}AdaptationType" minOccurs="0"/>
 *         &lt;element name="schema" type="{http://schemas.ogf.org/nsi/2013/12/services/definition}SchemaType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="attribute" type="{http://schemas.ogf.org/nsi/2013/12/services/definition}AttributeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="error" type="{http://schemas.ogf.org/nsi/2013/12/services/definition}ErrorType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceDefinitionType", propOrder = {
    "name",
    "comment",
    "serviceType",
    "adaptation",
    "schema",
    "attribute",
    "error",
    "any"
})
public class ServiceDefinitionType {

    protected String name;
    protected String comment;
    protected String serviceType;
    protected AdaptationType adaptation;
    protected List<SchemaType> schema;
    protected List<AttributeType> attribute;
    protected List<ErrorType> error;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String id;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
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

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the serviceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceType(String value) {
        this.serviceType = value;
    }

    /**
     * Gets the value of the adaptation property.
     * 
     * @return
     *     possible object is
     *     {@link AdaptationType }
     *     
     */
    public AdaptationType getAdaptation() {
        return adaptation;
    }

    /**
     * Sets the value of the adaptation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdaptationType }
     *     
     */
    public void setAdaptation(AdaptationType value) {
        this.adaptation = value;
    }

    /**
     * Gets the value of the schema property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schema property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchema().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SchemaType }
     * 
     * 
     */
    public List<SchemaType> getSchema() {
        if (schema == null) {
            schema = new ArrayList<SchemaType>();
        }
        return this.schema;
    }

    /**
     * Gets the value of the attribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeType }
     * 
     * 
     */
    public List<AttributeType> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<AttributeType>();
        }
        return this.attribute;
    }

    /**
     * Gets the value of the error property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the error property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorType }
     * 
     * 
     */
    public List<ErrorType> getError() {
        if (error == null) {
            error = new ArrayList<ErrorType>();
        }
        return this.error;
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
     * {@link Object }
     * {@link Element }
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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}

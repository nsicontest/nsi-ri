
package org.ogf.schemas.nml._2013._05.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for LabelType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LabelType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="labeltype" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LabelType", propOrder = {
    "value"
})
public class LabelType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "labeltype", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String labeltype;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the labeltype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabeltype() {
        return labeltype;
    }

    /**
     * Sets the value of the labeltype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabeltype(String value) {
        this.labeltype = value;
    }

}

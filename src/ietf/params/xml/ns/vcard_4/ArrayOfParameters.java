
package ietf.params.xml.ns.vcard_4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:ietf:params:xml:ns:vcard-4.0}baseParameter" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfParameters", propOrder = {
    "baseParameter"
})
public class ArrayOfParameters {

    @XmlElementRef(name = "baseParameter", namespace = "urn:ietf:params:xml:ns:vcard-4.0", type = JAXBElement.class, required = false)
    protected List<JAXBElement<? extends BaseParameterType>> baseParameter;

    /**
     * Gets the value of the baseParameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baseParameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaseParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link PrefParamType }{@code >}
     * {@link JAXBElement }{@code <}{@link BaseParameterType }{@code >}
     * {@link JAXBElement }{@code <}{@link XuidParamType }{@code >}
     * {@link JAXBElement }{@code <}{@link LabelParamType }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeParamType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends BaseParameterType>> getBaseParameter() {
        if (baseParameter == null) {
            baseParameter = new ArrayList<JAXBElement<? extends BaseParameterType>>();
        }
        return this.baseParameter;
    }

}

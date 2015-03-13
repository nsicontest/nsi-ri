
package org.ogf.schemas.nsi._2013._12.connection.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Type definition for the queryResultConfirmedType message
 *                 providing a mechanism for a Requester NSA to get a list
 *                 of Confirmed, Failed, or Error results against a specific
 *                 connectionId.
 *                 
 *                 Elements:
 *                 
 *                 result - Zero or more result elements based on the results
 *                 matching the specified query.
 *             
 * 
 * <p>Java class for QueryResultConfirmedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryResultConfirmedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://schemas.ogf.org/nsi/2013/12/connection/types}QueryResultResponseType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryResultConfirmedType", propOrder = {
    "result"
})
public class QueryResultConfirmedType {

    protected List<QueryResultResponseType> result;

    /**
     * Gets the value of the result property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the result property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryResultResponseType }
     * 
     * 
     */
    public List<QueryResultResponseType> getResult() {
        if (result == null) {
            result = new ArrayList<QueryResultResponseType>();
        }
        return this.result;
    }

}

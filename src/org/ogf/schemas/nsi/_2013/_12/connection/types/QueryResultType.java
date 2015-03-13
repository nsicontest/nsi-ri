
package org.ogf.schemas.nsi._2013._12.connection.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 The queryResultType message provides a mechanism for a Requester
 *                 NSA to query a Provider NSA for a set of Confirmed, Failed, or
 *                 Errors results against a specific connectionId.
 *                 
 *                 Elements compose a filter for specifying the results to
 *                 return in response to the query operation.  The filter query
 *                 provides an inclusive range of result identifiers based
 *                 on connectionId.
 *                 
 *                 Elements:
 *                 
 *                 connectionId - Retrieve results for this connectionId.
 *                 
 *                 startResultId - The start of the range of result Ids to return.
 *                 If not present, then the query should start from oldest result
 *                 available.
 *                 
 *                 endResultId - The end of the range of result Ids to return.  If
 *                 not present then the query should end with the newest result
 *                 available.
 *             
 * 
 * <p>Java class for QueryResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="connectionId" type="{http://schemas.ogf.org/nsi/2013/12/framework/types}ConnectionIdType"/>
 *         &lt;element name="startResultId" type="{http://schemas.ogf.org/nsi/2013/12/connection/types}ResultIdType" minOccurs="0"/>
 *         &lt;element name="endResultId" type="{http://schemas.ogf.org/nsi/2013/12/connection/types}ResultIdType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryResultType", propOrder = {
    "connectionId",
    "startResultId",
    "endResultId"
})
public class QueryResultType {

    @XmlElement(required = true)
    protected String connectionId;
    protected Long startResultId;
    protected Long endResultId;

    /**
     * Gets the value of the connectionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Sets the value of the connectionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectionId(String value) {
        this.connectionId = value;
    }

    /**
     * Gets the value of the startResultId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStartResultId() {
        return startResultId;
    }

    /**
     * Sets the value of the startResultId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStartResultId(Long value) {
        this.startResultId = value;
    }

    /**
     * Gets the value of the endResultId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEndResultId() {
        return endResultId;
    }

    /**
     * Sets the value of the endResultId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEndResultId(Long value) {
        this.endResultId = value;
    }

}

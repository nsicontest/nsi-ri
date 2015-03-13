
package org.ogf.schemas.nsi._2013._09.topology;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.ogf.schemas.nsi._2013._09.topology package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Service_QNAME = new QName("http://schemas.ogf.org/nsi/2013/09/topology#", "Service");
    private final static QName _NSA_QNAME = new QName("http://schemas.ogf.org/nsi/2013/09/topology#", "NSA");
    private final static QName _Relation_QNAME = new QName("http://schemas.ogf.org/nsi/2013/09/topology#", "Relation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.ogf.schemas.nsi._2013._09.topology
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NSAType }
     * 
     */
    public NSAType createNSAType() {
        return new NSAType();
    }

    /**
     * Create an instance of {@link NSARelationType }
     * 
     */
    public NSARelationType createNSARelationType() {
        return new NSARelationType();
    }

    /**
     * Create an instance of {@link ServiceType }
     * 
     */
    public ServiceType createServiceType() {
        return new ServiceType();
    }

    /**
     * Create an instance of {@link ExtensionType }
     * 
     */
    public ExtensionType createExtensionType() {
        return new ExtensionType();
    }

    /**
     * Create an instance of {@link ServiceRelationType }
     * 
     */
    public ServiceRelationType createServiceRelationType() {
        return new ServiceRelationType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.ogf.org/nsi/2013/09/topology#", name = "Service")
    public JAXBElement<ServiceType> createService(ServiceType value) {
        return new JAXBElement<ServiceType>(_Service_QNAME, ServiceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NSAType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.ogf.org/nsi/2013/09/topology#", name = "NSA")
    public JAXBElement<NSAType> createNSA(NSAType value) {
        return new JAXBElement<NSAType>(_NSA_QNAME, NSAType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NSARelationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.ogf.org/nsi/2013/09/topology#", name = "Relation")
    public JAXBElement<NSARelationType> createRelation(NSARelationType value) {
        return new JAXBElement<NSARelationType>(_Relation_QNAME, NSARelationType.class, null, value);
    }

}


package org.ogf.schemas.nsi._2013._12.services.definition;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModifiableType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ModifiableType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="false"/>
 *     &lt;enumeration value="pre"/>
 *     &lt;enumeration value="post"/>
 *     &lt;enumeration value="true"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ModifiableType")
@XmlEnum
public enum ModifiableType {

    @XmlEnumValue("false")
    FALSE("false"),
    @XmlEnumValue("pre")
    PRE("pre"),
    @XmlEnumValue("post")
    POST("post"),
    @XmlEnumValue("true")
    TRUE("true");
    private final String value;

    ModifiableType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ModifiableType fromValue(String v) {
        for (ModifiableType c: ModifiableType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

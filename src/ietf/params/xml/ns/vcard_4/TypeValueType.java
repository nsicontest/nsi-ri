
package ietf.params.xml.ns.vcard_4;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TypeValueType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TypeValueType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="work"/>
 *     &lt;enumeration value="home"/>
 *     &lt;enumeration value="text"/>
 *     &lt;enumeration value="voice"/>
 *     &lt;enumeration value="fax"/>
 *     &lt;enumeration value="cell"/>
 *     &lt;enumeration value="video"/>
 *     &lt;enumeration value="pager"/>
 *     &lt;enumeration value="textphone"/>
 *     &lt;enumeration value="x-car"/>
 *     &lt;enumeration value="spouse"/>
 *     &lt;enumeration value="child"/>
 *     &lt;enumeration value="x-assistant"/>
 *     &lt;enumeration value="x-manager"/>
 *     &lt;enumeration value="x-blog"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TypeValueType")
@XmlEnum
public enum TypeValueType {

    @XmlEnumValue("work")
    WORK("work"),
    @XmlEnumValue("home")
    HOME("home"),
    @XmlEnumValue("text")
    TEXT("text"),
    @XmlEnumValue("voice")
    VOICE("voice"),
    @XmlEnumValue("fax")
    FAX("fax"),
    @XmlEnumValue("cell")
    CELL("cell"),
    @XmlEnumValue("video")
    VIDEO("video"),
    @XmlEnumValue("pager")
    PAGER("pager"),
    @XmlEnumValue("textphone")
    TEXTPHONE("textphone"),
    @XmlEnumValue("x-car")
    X_CAR("x-car"),
    @XmlEnumValue("spouse")
    SPOUSE("spouse"),
    @XmlEnumValue("child")
    CHILD("child"),
    @XmlEnumValue("x-assistant")
    X_ASSISTANT("x-assistant"),
    @XmlEnumValue("x-manager")
    X_MANAGER("x-manager"),
    @XmlEnumValue("x-blog")
    X_BLOG("x-blog");
    private final String value;

    TypeValueType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeValueType fromValue(String v) {
        for (TypeValueType c: TypeValueType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

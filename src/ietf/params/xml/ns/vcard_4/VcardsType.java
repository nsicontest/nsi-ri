
package ietf.params.xml.ns.vcard_4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VcardsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VcardsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="vcard">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="uid" type="{urn:ietf:params:xml:ns:vcard-4.0}uidPropType"/>
 *                   &lt;element name="prodid" type="{urn:ietf:params:xml:ns:vcard-4.0}prodidPropType"/>
 *                   &lt;element name="rev" type="{urn:ietf:params:xml:ns:vcard-4.0}revPropType"/>
 *                   &lt;element name="categories" type="{urn:ietf:params:xml:ns:vcard-4.0}categoriesPropType" minOccurs="0"/>
 *                   &lt;element name="kind" type="{urn:ietf:params:xml:ns:vcard-4.0}kindPropType"/>
 *                   &lt;element name="fn" type="{urn:ietf:params:xml:ns:vcard-4.0}fnPropType"/>
 *                   &lt;element name="n" type="{urn:ietf:params:xml:ns:vcard-4.0}nPropType" minOccurs="0"/>
 *                   &lt;element name="note" type="{urn:ietf:params:xml:ns:vcard-4.0}notePropType" minOccurs="0"/>
 *                   &lt;element name="fburl" type="{urn:ietf:params:xml:ns:vcard-4.0}fburlPropType" minOccurs="0"/>
 *                   &lt;element name="title" type="{urn:ietf:params:xml:ns:vcard-4.0}titlePropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="group" type="{urn:ietf:params:xml:ns:vcard-4.0}affiliationPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="url" type="{urn:ietf:params:xml:ns:vcard-4.0}urlPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="adr" type="{urn:ietf:params:xml:ns:vcard-4.0}adrPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="nickname" type="{urn:ietf:params:xml:ns:vcard-4.0}nicknamePropType" minOccurs="0"/>
 *                   &lt;element name="related" type="{urn:ietf:params:xml:ns:vcard-4.0}relatedPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="bday" type="{urn:ietf:params:xml:ns:vcard-4.0}bdayPropType" minOccurs="0"/>
 *                   &lt;element name="anniversary" type="{urn:ietf:params:xml:ns:vcard-4.0}anniversaryPropType" minOccurs="0"/>
 *                   &lt;element name="photo" type="{urn:ietf:params:xml:ns:vcard-4.0}photoPropType" minOccurs="0"/>
 *                   &lt;element name="gender" type="{urn:ietf:params:xml:ns:vcard-4.0}genderPropType" minOccurs="0"/>
 *                   &lt;element name="lang" type="{urn:ietf:params:xml:ns:vcard-4.0}langPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="tel" type="{urn:ietf:params:xml:ns:vcard-4.0}telPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="impp" type="{urn:ietf:params:xml:ns:vcard-4.0}imppPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="email" type="{urn:ietf:params:xml:ns:vcard-4.0}emailPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="geo" type="{urn:ietf:params:xml:ns:vcard-4.0}geoPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="key" type="{urn:ietf:params:xml:ns:vcard-4.0}keyPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="member" type="{urn:ietf:params:xml:ns:vcard-4.0}memberPropType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VcardsType", propOrder = {
    "vcard"
})
public class VcardsType {

    @XmlElement(required = true)
    protected VcardsType.Vcard vcard;

    /**
     * Gets the value of the vcard property.
     * 
     * @return
     *     possible object is
     *     {@link VcardsType.Vcard }
     *     
     */
    public VcardsType.Vcard getVcard() {
        return vcard;
    }

    /**
     * Sets the value of the vcard property.
     * 
     * @param value
     *     allowed object is
     *     {@link VcardsType.Vcard }
     *     
     */
    public void setVcard(VcardsType.Vcard value) {
        this.vcard = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="uid" type="{urn:ietf:params:xml:ns:vcard-4.0}uidPropType"/>
     *         &lt;element name="prodid" type="{urn:ietf:params:xml:ns:vcard-4.0}prodidPropType"/>
     *         &lt;element name="rev" type="{urn:ietf:params:xml:ns:vcard-4.0}revPropType"/>
     *         &lt;element name="categories" type="{urn:ietf:params:xml:ns:vcard-4.0}categoriesPropType" minOccurs="0"/>
     *         &lt;element name="kind" type="{urn:ietf:params:xml:ns:vcard-4.0}kindPropType"/>
     *         &lt;element name="fn" type="{urn:ietf:params:xml:ns:vcard-4.0}fnPropType"/>
     *         &lt;element name="n" type="{urn:ietf:params:xml:ns:vcard-4.0}nPropType" minOccurs="0"/>
     *         &lt;element name="note" type="{urn:ietf:params:xml:ns:vcard-4.0}notePropType" minOccurs="0"/>
     *         &lt;element name="fburl" type="{urn:ietf:params:xml:ns:vcard-4.0}fburlPropType" minOccurs="0"/>
     *         &lt;element name="title" type="{urn:ietf:params:xml:ns:vcard-4.0}titlePropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="group" type="{urn:ietf:params:xml:ns:vcard-4.0}affiliationPropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="url" type="{urn:ietf:params:xml:ns:vcard-4.0}urlPropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="adr" type="{urn:ietf:params:xml:ns:vcard-4.0}adrPropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="nickname" type="{urn:ietf:params:xml:ns:vcard-4.0}nicknamePropType" minOccurs="0"/>
     *         &lt;element name="related" type="{urn:ietf:params:xml:ns:vcard-4.0}relatedPropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="bday" type="{urn:ietf:params:xml:ns:vcard-4.0}bdayPropType" minOccurs="0"/>
     *         &lt;element name="anniversary" type="{urn:ietf:params:xml:ns:vcard-4.0}anniversaryPropType" minOccurs="0"/>
     *         &lt;element name="photo" type="{urn:ietf:params:xml:ns:vcard-4.0}photoPropType" minOccurs="0"/>
     *         &lt;element name="gender" type="{urn:ietf:params:xml:ns:vcard-4.0}genderPropType" minOccurs="0"/>
     *         &lt;element name="lang" type="{urn:ietf:params:xml:ns:vcard-4.0}langPropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="tel" type="{urn:ietf:params:xml:ns:vcard-4.0}telPropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="impp" type="{urn:ietf:params:xml:ns:vcard-4.0}imppPropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="email" type="{urn:ietf:params:xml:ns:vcard-4.0}emailPropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="geo" type="{urn:ietf:params:xml:ns:vcard-4.0}geoPropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="key" type="{urn:ietf:params:xml:ns:vcard-4.0}keyPropType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="member" type="{urn:ietf:params:xml:ns:vcard-4.0}memberPropType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "uid",
        "prodid",
        "rev",
        "categories",
        "kind",
        "fn",
        "n",
        "note",
        "fburl",
        "title",
        "group",
        "url",
        "adr",
        "nickname",
        "related",
        "bday",
        "anniversary",
        "photo",
        "gender",
        "lang",
        "tel",
        "impp",
        "email",
        "geo",
        "key",
        "member"
    })
    public static class Vcard {

        @XmlElement(required = true)
        protected UidPropType uid;
        @XmlElement(required = true)
        protected ProdidPropType prodid;
        @XmlElement(required = true)
        protected RevPropType rev;
        protected CategoriesPropType categories;
        @XmlElement(required = true)
        protected KindPropType kind;
        @XmlElement(required = true)
        protected FnPropType fn;
        protected NPropType n;
        protected NotePropType note;
        protected FburlPropType fburl;
        protected List<TitlePropType> title;
        protected List<AffiliationPropType> group;
        protected List<UrlPropType> url;
        protected List<AdrPropType> adr;
        protected NicknamePropType nickname;
        protected List<RelatedPropType> related;
        protected BdayPropType bday;
        protected AnniversaryPropType anniversary;
        protected PhotoPropType photo;
        protected GenderPropType gender;
        protected List<LangPropType> lang;
        protected List<TelPropType> tel;
        protected List<ImppPropType> impp;
        protected List<EmailPropType> email;
        protected List<GeoPropType> geo;
        protected List<KeyPropType> key;
        protected List<MemberPropType> member;

        /**
         * Gets the value of the uid property.
         * 
         * @return
         *     possible object is
         *     {@link UidPropType }
         *     
         */
        public UidPropType getUid() {
            return uid;
        }

        /**
         * Sets the value of the uid property.
         * 
         * @param value
         *     allowed object is
         *     {@link UidPropType }
         *     
         */
        public void setUid(UidPropType value) {
            this.uid = value;
        }

        /**
         * Gets the value of the prodid property.
         * 
         * @return
         *     possible object is
         *     {@link ProdidPropType }
         *     
         */
        public ProdidPropType getProdid() {
            return prodid;
        }

        /**
         * Sets the value of the prodid property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProdidPropType }
         *     
         */
        public void setProdid(ProdidPropType value) {
            this.prodid = value;
        }

        /**
         * Gets the value of the rev property.
         * 
         * @return
         *     possible object is
         *     {@link RevPropType }
         *     
         */
        public RevPropType getRev() {
            return rev;
        }

        /**
         * Sets the value of the rev property.
         * 
         * @param value
         *     allowed object is
         *     {@link RevPropType }
         *     
         */
        public void setRev(RevPropType value) {
            this.rev = value;
        }

        /**
         * Gets the value of the categories property.
         * 
         * @return
         *     possible object is
         *     {@link CategoriesPropType }
         *     
         */
        public CategoriesPropType getCategories() {
            return categories;
        }

        /**
         * Sets the value of the categories property.
         * 
         * @param value
         *     allowed object is
         *     {@link CategoriesPropType }
         *     
         */
        public void setCategories(CategoriesPropType value) {
            this.categories = value;
        }

        /**
         * Gets the value of the kind property.
         * 
         * @return
         *     possible object is
         *     {@link KindPropType }
         *     
         */
        public KindPropType getKind() {
            return kind;
        }

        /**
         * Sets the value of the kind property.
         * 
         * @param value
         *     allowed object is
         *     {@link KindPropType }
         *     
         */
        public void setKind(KindPropType value) {
            this.kind = value;
        }

        /**
         * Gets the value of the fn property.
         * 
         * @return
         *     possible object is
         *     {@link FnPropType }
         *     
         */
        public FnPropType getFn() {
            return fn;
        }

        /**
         * Sets the value of the fn property.
         * 
         * @param value
         *     allowed object is
         *     {@link FnPropType }
         *     
         */
        public void setFn(FnPropType value) {
            this.fn = value;
        }

        /**
         * Gets the value of the n property.
         * 
         * @return
         *     possible object is
         *     {@link NPropType }
         *     
         */
        public NPropType getN() {
            return n;
        }

        /**
         * Sets the value of the n property.
         * 
         * @param value
         *     allowed object is
         *     {@link NPropType }
         *     
         */
        public void setN(NPropType value) {
            this.n = value;
        }

        /**
         * Gets the value of the note property.
         * 
         * @return
         *     possible object is
         *     {@link NotePropType }
         *     
         */
        public NotePropType getNote() {
            return note;
        }

        /**
         * Sets the value of the note property.
         * 
         * @param value
         *     allowed object is
         *     {@link NotePropType }
         *     
         */
        public void setNote(NotePropType value) {
            this.note = value;
        }

        /**
         * Gets the value of the fburl property.
         * 
         * @return
         *     possible object is
         *     {@link FburlPropType }
         *     
         */
        public FburlPropType getFburl() {
            return fburl;
        }

        /**
         * Sets the value of the fburl property.
         * 
         * @param value
         *     allowed object is
         *     {@link FburlPropType }
         *     
         */
        public void setFburl(FburlPropType value) {
            this.fburl = value;
        }

        /**
         * Gets the value of the title property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the title property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTitle().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TitlePropType }
         * 
         * 
         */
        public List<TitlePropType> getTitle() {
            if (title == null) {
                title = new ArrayList<TitlePropType>();
            }
            return this.title;
        }

        /**
         * Gets the value of the group property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the group property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGroup().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AffiliationPropType }
         * 
         * 
         */
        public List<AffiliationPropType> getGroup() {
            if (group == null) {
                group = new ArrayList<AffiliationPropType>();
            }
            return this.group;
        }

        /**
         * Gets the value of the url property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the url property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUrl().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link UrlPropType }
         * 
         * 
         */
        public List<UrlPropType> getUrl() {
            if (url == null) {
                url = new ArrayList<UrlPropType>();
            }
            return this.url;
        }

        /**
         * Gets the value of the adr property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the adr property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdr().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AdrPropType }
         * 
         * 
         */
        public List<AdrPropType> getAdr() {
            if (adr == null) {
                adr = new ArrayList<AdrPropType>();
            }
            return this.adr;
        }

        /**
         * Gets the value of the nickname property.
         * 
         * @return
         *     possible object is
         *     {@link NicknamePropType }
         *     
         */
        public NicknamePropType getNickname() {
            return nickname;
        }

        /**
         * Sets the value of the nickname property.
         * 
         * @param value
         *     allowed object is
         *     {@link NicknamePropType }
         *     
         */
        public void setNickname(NicknamePropType value) {
            this.nickname = value;
        }

        /**
         * Gets the value of the related property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the related property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRelated().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RelatedPropType }
         * 
         * 
         */
        public List<RelatedPropType> getRelated() {
            if (related == null) {
                related = new ArrayList<RelatedPropType>();
            }
            return this.related;
        }

        /**
         * Gets the value of the bday property.
         * 
         * @return
         *     possible object is
         *     {@link BdayPropType }
         *     
         */
        public BdayPropType getBday() {
            return bday;
        }

        /**
         * Sets the value of the bday property.
         * 
         * @param value
         *     allowed object is
         *     {@link BdayPropType }
         *     
         */
        public void setBday(BdayPropType value) {
            this.bday = value;
        }

        /**
         * Gets the value of the anniversary property.
         * 
         * @return
         *     possible object is
         *     {@link AnniversaryPropType }
         *     
         */
        public AnniversaryPropType getAnniversary() {
            return anniversary;
        }

        /**
         * Sets the value of the anniversary property.
         * 
         * @param value
         *     allowed object is
         *     {@link AnniversaryPropType }
         *     
         */
        public void setAnniversary(AnniversaryPropType value) {
            this.anniversary = value;
        }

        /**
         * Gets the value of the photo property.
         * 
         * @return
         *     possible object is
         *     {@link PhotoPropType }
         *     
         */
        public PhotoPropType getPhoto() {
            return photo;
        }

        /**
         * Sets the value of the photo property.
         * 
         * @param value
         *     allowed object is
         *     {@link PhotoPropType }
         *     
         */
        public void setPhoto(PhotoPropType value) {
            this.photo = value;
        }

        /**
         * Gets the value of the gender property.
         * 
         * @return
         *     possible object is
         *     {@link GenderPropType }
         *     
         */
        public GenderPropType getGender() {
            return gender;
        }

        /**
         * Sets the value of the gender property.
         * 
         * @param value
         *     allowed object is
         *     {@link GenderPropType }
         *     
         */
        public void setGender(GenderPropType value) {
            this.gender = value;
        }

        /**
         * Gets the value of the lang property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the lang property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLang().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link LangPropType }
         * 
         * 
         */
        public List<LangPropType> getLang() {
            if (lang == null) {
                lang = new ArrayList<LangPropType>();
            }
            return this.lang;
        }

        /**
         * Gets the value of the tel property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the tel property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTel().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TelPropType }
         * 
         * 
         */
        public List<TelPropType> getTel() {
            if (tel == null) {
                tel = new ArrayList<TelPropType>();
            }
            return this.tel;
        }

        /**
         * Gets the value of the impp property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the impp property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getImpp().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ImppPropType }
         * 
         * 
         */
        public List<ImppPropType> getImpp() {
            if (impp == null) {
                impp = new ArrayList<ImppPropType>();
            }
            return this.impp;
        }

        /**
         * Gets the value of the email property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the email property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEmail().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EmailPropType }
         * 
         * 
         */
        public List<EmailPropType> getEmail() {
            if (email == null) {
                email = new ArrayList<EmailPropType>();
            }
            return this.email;
        }

        /**
         * Gets the value of the geo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the geo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGeo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link GeoPropType }
         * 
         * 
         */
        public List<GeoPropType> getGeo() {
            if (geo == null) {
                geo = new ArrayList<GeoPropType>();
            }
            return this.geo;
        }

        /**
         * Gets the value of the key property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the key property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKey().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link KeyPropType }
         * 
         * 
         */
        public List<KeyPropType> getKey() {
            if (key == null) {
                key = new ArrayList<KeyPropType>();
            }
            return this.key;
        }

        /**
         * Gets the value of the member property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the member property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMember().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MemberPropType }
         * 
         * 
         */
        public List<MemberPropType> getMember() {
            if (member == null) {
                member = new ArrayList<MemberPropType>();
            }
            return this.member;
        }

    }

}

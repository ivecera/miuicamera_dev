package com.adobe.xmp.impl;

import com.adobe.xmp.XMPConst;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPSchemaRegistry;
import com.adobe.xmp.options.AliasOptions;
import com.adobe.xmp.properties.XMPAliasInfo;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public final class XMPSchemaRegistryImpl implements XMPSchemaRegistry, XMPConst {
    private Map aliasMap = new HashMap();
    private Map namespaceToPrefixMap = new HashMap();
    private Pattern p = Pattern.compile("[/*?\\[\\]]");
    private Map prefixToNamespaceMap = new HashMap();

    public XMPSchemaRegistryImpl() {
        try {
            registerStandardNamespaces();
            registerStandardAliases();
        } catch (XMPException unused) {
            throw new RuntimeException("The XMPSchemaRegistry cannot be initialized!");
        }
    }

    private void registerStandardAliases() throws XMPException {
        AliasOptions arrayOrdered = new AliasOptions().setArrayOrdered(true);
        AliasOptions arrayAltText = new AliasOptions().setArrayAltText(true);
        registerAlias(XMPConst.NS_XMP, "Author", XMPConst.NS_DC, "creator", arrayOrdered);
        registerAlias(XMPConst.NS_XMP, "Authors", XMPConst.NS_DC, "creator", null);
        registerAlias(XMPConst.NS_XMP, "Description", XMPConst.NS_DC, "description", null);
        registerAlias(XMPConst.NS_XMP, "Format", XMPConst.NS_DC, "format", null);
        registerAlias(XMPConst.NS_XMP, "Keywords", XMPConst.NS_DC, "subject", null);
        registerAlias(XMPConst.NS_XMP, "Locale", XMPConst.NS_DC, EffectConfiguration.KEY_SYS_LANGUAGE, null);
        registerAlias(XMPConst.NS_XMP, "Title", XMPConst.NS_DC, "title", null);
        registerAlias(XMPConst.NS_XMP_RIGHTS, "Copyright", XMPConst.NS_DC, "rights", null);
        registerAlias(XMPConst.NS_PDF, "Author", XMPConst.NS_DC, "creator", arrayOrdered);
        registerAlias(XMPConst.NS_PDF, "BaseURL", XMPConst.NS_XMP, "BaseURL", null);
        registerAlias(XMPConst.NS_PDF, "CreationDate", XMPConst.NS_XMP, "CreateDate", null);
        registerAlias(XMPConst.NS_PDF, "Creator", XMPConst.NS_XMP, "CreatorTool", null);
        registerAlias(XMPConst.NS_PDF, "ModDate", XMPConst.NS_XMP, "ModifyDate", null);
        registerAlias(XMPConst.NS_PDF, "Subject", XMPConst.NS_DC, "description", arrayAltText);
        registerAlias(XMPConst.NS_PDF, "Title", XMPConst.NS_DC, "title", arrayAltText);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Author", XMPConst.NS_DC, "creator", arrayOrdered);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Caption", XMPConst.NS_DC, "description", arrayAltText);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Copyright", XMPConst.NS_DC, "rights", arrayAltText);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Keywords", XMPConst.NS_DC, "subject", null);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Marked", XMPConst.NS_XMP_RIGHTS, "Marked", null);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Title", XMPConst.NS_DC, "title", arrayAltText);
        registerAlias(XMPConst.NS_PHOTOSHOP, "WebStatement", XMPConst.NS_XMP_RIGHTS, "WebStatement", null);
        registerAlias(XMPConst.NS_TIFF, "Artist", XMPConst.NS_DC, "creator", arrayOrdered);
        registerAlias(XMPConst.NS_TIFF, "Copyright", XMPConst.NS_DC, "rights", null);
        registerAlias(XMPConst.NS_TIFF, "DateTime", XMPConst.NS_XMP, "ModifyDate", null);
        registerAlias(XMPConst.NS_TIFF, "ImageDescription", XMPConst.NS_DC, "description", null);
        registerAlias(XMPConst.NS_TIFF, "Software", XMPConst.NS_XMP, "CreatorTool", null);
        registerAlias(XMPConst.NS_PNG, "Author", XMPConst.NS_DC, "creator", arrayOrdered);
        registerAlias(XMPConst.NS_PNG, "Copyright", XMPConst.NS_DC, "rights", arrayAltText);
        registerAlias(XMPConst.NS_PNG, "CreationTime", XMPConst.NS_XMP, "CreateDate", null);
        registerAlias(XMPConst.NS_PNG, "Description", XMPConst.NS_DC, "description", arrayAltText);
        registerAlias(XMPConst.NS_PNG, "ModificationTime", XMPConst.NS_XMP, "ModifyDate", null);
        registerAlias(XMPConst.NS_PNG, "Software", XMPConst.NS_XMP, "CreatorTool", null);
        registerAlias(XMPConst.NS_PNG, "Title", XMPConst.NS_DC, "title", arrayAltText);
    }

    private void registerStandardNamespaces() throws XMPException {
        registerNamespace(XMPConst.NS_XML, "xml");
        registerNamespace(XMPConst.NS_RDF, "rdf");
        registerNamespace(XMPConst.NS_DC, "dc");
        registerNamespace(XMPConst.NS_IPTCCORE, "Iptc4xmpCore");
        registerNamespace(XMPConst.NS_X, "x");
        registerNamespace(XMPConst.NS_IX, "iX");
        registerNamespace(XMPConst.NS_XMP, "xmp");
        registerNamespace(XMPConst.NS_XMP_RIGHTS, "xmpRights");
        registerNamespace(XMPConst.NS_XMP_MM, "xmpMM");
        registerNamespace(XMPConst.NS_XMP_BJ, "xmpBJ");
        registerNamespace(XMPConst.NS_XMP_NOTE, "xmpNote");
        registerNamespace(XMPConst.NS_PDF, "pdf");
        registerNamespace(XMPConst.NS_PDFX, "pdfx");
        registerNamespace(XMPConst.NS_PDFX_ID, "pdfxid");
        registerNamespace(XMPConst.NS_PDFA_SCHEMA, "pdfaSchema");
        registerNamespace(XMPConst.NS_PDFA_PROPERTY, "pdfaProperty");
        registerNamespace(XMPConst.NS_PDFA_TYPE, "pdfaType");
        registerNamespace(XMPConst.NS_PDFA_FIELD, "pdfaField");
        registerNamespace(XMPConst.NS_PDFA_ID, "pdfaid");
        registerNamespace(XMPConst.NS_PDFA_EXTENSION, "pdfaExtension");
        registerNamespace(XMPConst.NS_PHOTOSHOP, "photoshop");
        registerNamespace(XMPConst.NS_PSALBUM, "album");
        registerNamespace(XMPConst.NS_EXIF, "exif");
        registerNamespace(XMPConst.NS_EXIF_AUX, "aux");
        registerNamespace(XMPConst.NS_TIFF, "tiff");
        registerNamespace(XMPConst.NS_PNG, "png");
        registerNamespace(XMPConst.NS_JPEG, "jpeg");
        registerNamespace(XMPConst.NS_JP2K, "jp2k");
        registerNamespace(XMPConst.NS_CAMERARAW, "crs");
        registerNamespace(XMPConst.NS_ADOBESTOCKPHOTO, "bmsp");
        registerNamespace(XMPConst.NS_CREATOR_ATOM, "creatorAtom");
        registerNamespace(XMPConst.NS_ASF, "asf");
        registerNamespace(XMPConst.NS_WAV, "wav");
        registerNamespace(XMPConst.NS_DM, "xmpDM");
        registerNamespace(XMPConst.NS_TRANSIENT, "xmpx");
        registerNamespace(XMPConst.TYPE_TEXT, "xmpT");
        registerNamespace(XMPConst.TYPE_PAGEDFILE, "xmpTPg");
        registerNamespace(XMPConst.TYPE_GRAPHICS, "xmpG");
        registerNamespace(XMPConst.TYPE_IMAGE, "xmpGImg");
        registerNamespace(XMPConst.TYPE_FONT, "stFNT");
        registerNamespace(XMPConst.TYPE_DIMENSIONS, "stDim");
        registerNamespace(XMPConst.TYPE_RESOURCEEVENT, "stEvt");
        registerNamespace(XMPConst.TYPE_RESOURCEREF, "stRef");
        registerNamespace(XMPConst.TYPE_ST_VERSION, "stVer");
        registerNamespace(XMPConst.TYPE_ST_JOB, "stJob");
        registerNamespace(XMPConst.TYPE_MANIFESTITEM, "stMfs");
        registerNamespace(XMPConst.TYPE_IDENTIFIERQUAL, "xmpidq");
    }

    @Override // com.adobe.xmp.XMPSchemaRegistry
    public synchronized void deleteNamespace(String str) {
        String namespacePrefix = getNamespacePrefix(str);
        if (namespacePrefix != null) {
            this.namespaceToPrefixMap.remove(str);
            this.prefixToNamespaceMap.remove(namespacePrefix);
        }
    }

    @Override // com.adobe.xmp.XMPSchemaRegistry
    public synchronized XMPAliasInfo findAlias(String str) {
        return (XMPAliasInfo) this.aliasMap.get(str);
    }

    @Override // com.adobe.xmp.XMPSchemaRegistry
    public synchronized XMPAliasInfo[] findAliases(String str) {
        ArrayList arrayList;
        String namespacePrefix = getNamespacePrefix(str);
        arrayList = new ArrayList();
        if (namespacePrefix != null) {
            for (String str2 : this.aliasMap.keySet()) {
                if (str2.startsWith(namespacePrefix)) {
                    arrayList.add(findAlias(str2));
                }
            }
        }
        return (XMPAliasInfo[]) arrayList.toArray(new XMPAliasInfo[arrayList.size()]);
    }

    @Override // com.adobe.xmp.XMPSchemaRegistry
    public synchronized Map getAliases() {
        return Collections.unmodifiableMap(new TreeMap(this.aliasMap));
    }

    @Override // com.adobe.xmp.XMPSchemaRegistry
    public synchronized String getNamespacePrefix(String str) {
        return (String) this.namespaceToPrefixMap.get(str);
    }

    @Override // com.adobe.xmp.XMPSchemaRegistry
    public synchronized String getNamespaceURI(String str) {
        if (str != null) {
            if (!str.endsWith(":")) {
                str = str + ":";
            }
        }
        return (String) this.prefixToNamespaceMap.get(str);
    }

    @Override // com.adobe.xmp.XMPSchemaRegistry
    public synchronized Map getNamespaces() {
        return Collections.unmodifiableMap(new TreeMap(this.namespaceToPrefixMap));
    }

    @Override // com.adobe.xmp.XMPSchemaRegistry
    public synchronized Map getPrefixes() {
        return Collections.unmodifiableMap(new TreeMap(this.prefixToNamespaceMap));
    }

    /* access modifiers changed from: package-private */
    public synchronized void registerAlias(String str, String str2, final String str3, final String str4, AliasOptions aliasOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        ParameterAsserts.assertSchemaNS(str3);
        ParameterAsserts.assertPropName(str4);
        final AliasOptions aliasOptions2 = aliasOptions != null ? new AliasOptions(XMPNodeUtils.verifySetOptions(aliasOptions.toPropertyOptions(), null).getOptions()) : new AliasOptions();
        if (this.p.matcher(str2).find() || this.p.matcher(str4).find()) {
            throw new XMPException("Alias and actual property names must be simple", 102);
        }
        String namespacePrefix = getNamespacePrefix(str);
        final String namespacePrefix2 = getNamespacePrefix(str3);
        if (namespacePrefix == null) {
            throw new XMPException("Alias namespace is not registered", 101);
        } else if (namespacePrefix2 != null) {
            String str5 = namespacePrefix + str2;
            if (!this.aliasMap.containsKey(str5)) {
                if (!this.aliasMap.containsKey(namespacePrefix2 + str4)) {
                    this.aliasMap.put(str5, new XMPAliasInfo() {
                        /* class com.adobe.xmp.impl.XMPSchemaRegistryImpl.AnonymousClass1 */

                        @Override // com.adobe.xmp.properties.XMPAliasInfo
                        public AliasOptions getAliasForm() {
                            return aliasOptions2;
                        }

                        @Override // com.adobe.xmp.properties.XMPAliasInfo
                        public String getNamespace() {
                            return str3;
                        }

                        @Override // com.adobe.xmp.properties.XMPAliasInfo
                        public String getPrefix() {
                            return namespacePrefix2;
                        }

                        @Override // com.adobe.xmp.properties.XMPAliasInfo
                        public String getPropName() {
                            return str4;
                        }

                        public String toString() {
                            return namespacePrefix2 + str4 + " NS(" + str3 + "), FORM (" + getAliasForm() + ")";
                        }
                    });
                } else {
                    throw new XMPException("Actual property is already an alias, use the base property", 4);
                }
            } else {
                throw new XMPException("Alias is already existing", 4);
            }
        } else {
            throw new XMPException("Actual namespace is not registered", 101);
        }
    }

    @Override // com.adobe.xmp.XMPSchemaRegistry
    public synchronized String registerNamespace(String str, String str2) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPrefix(str2);
        if (str2.charAt(str2.length() - 1) != ':') {
            str2 = str2 + ':';
        }
        if (Utils.isXMLNameNS(str2.substring(0, str2.length() - 1))) {
            String str3 = (String) this.namespaceToPrefixMap.get(str);
            String str4 = (String) this.prefixToNamespaceMap.get(str2);
            if (str3 != null) {
                return str3;
            }
            if (str4 != null) {
                String str5 = str2;
                int i = 1;
                while (this.prefixToNamespaceMap.containsKey(str5)) {
                    str5 = str2.substring(0, str2.length() - 1) + "_" + i + "_:";
                    i++;
                }
                str2 = str5;
            }
            this.prefixToNamespaceMap.put(str2, str);
            this.namespaceToPrefixMap.put(str, str2);
            return str2;
        }
        throw new XMPException("The prefix is a bad XML name", 201);
    }

    @Override // com.adobe.xmp.XMPSchemaRegistry
    public synchronized XMPAliasInfo resolveAlias(String str, String str2) {
        String namespacePrefix = getNamespacePrefix(str);
        if (namespacePrefix == null) {
            return null;
        }
        Map map = this.aliasMap;
        return (XMPAliasInfo) map.get(namespacePrefix + str2);
    }
}

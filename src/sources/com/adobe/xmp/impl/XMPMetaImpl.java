package com.adobe.xmp.impl;

import com.adobe.xmp.XMPConst;
import com.adobe.xmp.XMPDateTime;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPIterator;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPPathFactory;
import com.adobe.xmp.XMPUtils;
import com.adobe.xmp.impl.xpath.XMPPath;
import com.adobe.xmp.impl.xpath.XMPPathParser;
import com.adobe.xmp.options.IteratorOptions;
import com.adobe.xmp.options.ParseOptions;
import com.adobe.xmp.options.PropertyOptions;
import com.adobe.xmp.properties.XMPProperty;
import java.util.Calendar;
import java.util.Iterator;

public class XMPMetaImpl implements XMPMeta, XMPConst {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int VALUE_BASE64 = 7;
    private static final int VALUE_BOOLEAN = 1;
    private static final int VALUE_CALENDAR = 6;
    private static final int VALUE_DATE = 5;
    private static final int VALUE_DOUBLE = 4;
    private static final int VALUE_INTEGER = 2;
    private static final int VALUE_LONG = 3;
    private static final int VALUE_STRING = 0;
    private String packetHeader;
    private XMPNode tree;

    public XMPMetaImpl() {
        this.packetHeader = null;
        this.tree = new XMPNode(null, null, null);
    }

    public XMPMetaImpl(XMPNode xMPNode) {
        this.packetHeader = null;
        this.tree = xMPNode;
    }

    private void doSetArrayItem(XMPNode xMPNode, int i, String str, PropertyOptions propertyOptions, boolean z) throws XMPException {
        XMPNode xMPNode2 = new XMPNode(XMPConst.ARRAY_ITEM_NAME, null);
        PropertyOptions verifySetOptions = XMPNodeUtils.verifySetOptions(propertyOptions, str);
        int childrenLength = xMPNode.getChildrenLength();
        if (z) {
            childrenLength++;
        }
        if (i == -1) {
            i = childrenLength;
        }
        if (1 > i || i > childrenLength) {
            throw new XMPException("Array index out of bounds", 104);
        }
        if (!z) {
            xMPNode.removeChild(i);
        }
        xMPNode.addChild(i, xMPNode2);
        setNode(xMPNode2, str, verifySetOptions, false);
    }

    private Object evaluateNodeValue(int i, XMPNode xMPNode) throws XMPException {
        Object obj;
        String value = xMPNode.getValue();
        switch (i) {
            case 1:
                obj = new Boolean(XMPUtils.convertToBoolean(value));
                break;
            case 2:
                obj = new Integer(XMPUtils.convertToInteger(value));
                break;
            case 3:
                obj = new Long(XMPUtils.convertToLong(value));
                break;
            case 4:
                obj = new Double(XMPUtils.convertToDouble(value));
                break;
            case 5:
                return XMPUtils.convertToDate(value);
            case 6:
                return XMPUtils.convertToDate(value).getCalendar();
            case 7:
                return XMPUtils.decodeBase64(value);
            default:
                return (value != null || xMPNode.getOptions().isCompositeProperty()) ? value : "";
        }
        return obj;
    }

    @Override // com.adobe.xmp.XMPMeta
    public void appendArrayItem(String str, String str2, PropertyOptions propertyOptions, String str3, PropertyOptions propertyOptions2) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        if (propertyOptions == null) {
            propertyOptions = new PropertyOptions();
        }
        if (propertyOptions.isOnlyArrayOptions()) {
            PropertyOptions verifySetOptions = XMPNodeUtils.verifySetOptions(propertyOptions, null);
            XMPPath expandXPath = XMPPathParser.expandXPath(str, str2);
            XMPNode findNode = XMPNodeUtils.findNode(this.tree, expandXPath, false, null);
            if (findNode != null) {
                if (!findNode.getOptions().isArray()) {
                    throw new XMPException("The named property is not an array", 102);
                }
            } else if (verifySetOptions.isArray()) {
                findNode = XMPNodeUtils.findNode(this.tree, expandXPath, true, verifySetOptions);
                if (findNode == null) {
                    throw new XMPException("Failure creating array node", 102);
                }
            } else {
                throw new XMPException("Explicit arrayOptions required to create new array", 103);
            }
            doSetArrayItem(findNode, -1, str3, propertyOptions2, true);
            return;
        }
        throw new XMPException("Only array form flags allowed for arrayOptions", 103);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void appendArrayItem(String str, String str2, String str3) throws XMPException {
        appendArrayItem(str, str2, null, str3, null);
    }

    @Override // com.adobe.xmp.XMPMeta, java.lang.Object
    public Object clone() {
        return new XMPMetaImpl((XMPNode) this.tree.clone());
    }

    @Override // com.adobe.xmp.XMPMeta
    public int countArrayItems(String str, String str2) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode == null) {
            return 0;
        }
        if (findNode.getOptions().isArray()) {
            return findNode.getChildrenLength();
        }
        throw new XMPException("The named property is not an array", 102);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void deleteArrayItem(String str, String str2, int i) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertArrayName(str2);
            deleteProperty(str, XMPPathFactory.composeArrayItemPath(str2, i));
        } catch (XMPException unused) {
        }
    }

    @Override // com.adobe.xmp.XMPMeta
    public void deleteProperty(String str, String str2) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertPropName(str2);
            XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
            if (findNode != null) {
                XMPNodeUtils.deleteNode(findNode);
            }
        } catch (XMPException unused) {
        }
    }

    @Override // com.adobe.xmp.XMPMeta
    public void deleteQualifier(String str, String str2, String str3, String str4) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertPropName(str2);
            deleteProperty(str, str2 + XMPPathFactory.composeQualifierPath(str3, str4));
        } catch (XMPException unused) {
        }
    }

    @Override // com.adobe.xmp.XMPMeta
    public void deleteStructField(String str, String str2, String str3, String str4) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertStructName(str2);
            deleteProperty(str, str2 + XMPPathFactory.composeStructFieldPath(str3, str4));
        } catch (XMPException unused) {
        }
    }

    @Override // com.adobe.xmp.XMPMeta
    public boolean doesArrayItemExist(String str, String str2, int i) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertArrayName(str2);
            return doesPropertyExist(str, XMPPathFactory.composeArrayItemPath(str2, i));
        } catch (XMPException unused) {
            return false;
        }
    }

    @Override // com.adobe.xmp.XMPMeta
    public boolean doesPropertyExist(String str, String str2) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertPropName(str2);
            return XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null) != null;
        } catch (XMPException unused) {
            return false;
        }
    }

    @Override // com.adobe.xmp.XMPMeta
    public boolean doesQualifierExist(String str, String str2, String str3, String str4) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertPropName(str2);
            String composeQualifierPath = XMPPathFactory.composeQualifierPath(str3, str4);
            return doesPropertyExist(str, str2 + composeQualifierPath);
        } catch (XMPException unused) {
            return false;
        }
    }

    @Override // com.adobe.xmp.XMPMeta
    public boolean doesStructFieldExist(String str, String str2, String str3, String str4) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertStructName(str2);
            String composeStructFieldPath = XMPPathFactory.composeStructFieldPath(str3, str4);
            return doesPropertyExist(str, str2 + composeStructFieldPath);
        } catch (XMPException unused) {
            return false;
        }
    }

    @Override // com.adobe.xmp.XMPMeta
    public String dumpObject() {
        return getRoot().dumpNode(true);
    }

    @Override // com.adobe.xmp.XMPMeta
    public XMPProperty getArrayItem(String str, String str2, int i) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        return getProperty(str, XMPPathFactory.composeArrayItemPath(str2, i));
    }

    @Override // com.adobe.xmp.XMPMeta
    public XMPProperty getLocalizedText(String str, String str2, String str3, String str4) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        ParameterAsserts.assertSpecificLang(str4);
        String normalizeLangValue = str3 != null ? Utils.normalizeLangValue(str3) : null;
        String normalizeLangValue2 = Utils.normalizeLangValue(str4);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode == null) {
            return null;
        }
        Object[] chooseLocalizedText = XMPNodeUtils.chooseLocalizedText(findNode, normalizeLangValue, normalizeLangValue2);
        int intValue = ((Integer) chooseLocalizedText[0]).intValue();
        final XMPNode xMPNode = (XMPNode) chooseLocalizedText[1];
        if (intValue != 0) {
            return new XMPProperty() {
                /* class com.adobe.xmp.impl.XMPMetaImpl.AnonymousClass1 */

                @Override // com.adobe.xmp.properties.XMPProperty
                public String getLanguage() {
                    return xMPNode.getQualifier(1).getValue();
                }

                @Override // com.adobe.xmp.properties.XMPProperty
                public PropertyOptions getOptions() {
                    return xMPNode.getOptions();
                }

                @Override // com.adobe.xmp.properties.XMPProperty
                public Object getValue() {
                    return xMPNode.getValue();
                }

                public String toString() {
                    return xMPNode.getValue().toString();
                }
            };
        }
        return null;
    }

    @Override // com.adobe.xmp.XMPMeta
    public String getObjectName() {
        return this.tree.getName() != null ? this.tree.getName() : "";
    }

    @Override // com.adobe.xmp.XMPMeta
    public String getPacketHeader() {
        return this.packetHeader;
    }

    @Override // com.adobe.xmp.XMPMeta
    public XMPProperty getProperty(String str, String str2) throws XMPException {
        return getProperty(str, str2, 0);
    }

    /* access modifiers changed from: protected */
    public XMPProperty getProperty(String str, String str2, int i) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        final XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode == null) {
            return null;
        }
        if (i == 0 || !findNode.getOptions().isCompositeProperty()) {
            final Object evaluateNodeValue = evaluateNodeValue(i, findNode);
            return new XMPProperty() {
                /* class com.adobe.xmp.impl.XMPMetaImpl.AnonymousClass2 */

                @Override // com.adobe.xmp.properties.XMPProperty
                public String getLanguage() {
                    return null;
                }

                @Override // com.adobe.xmp.properties.XMPProperty
                public PropertyOptions getOptions() {
                    return findNode.getOptions();
                }

                @Override // com.adobe.xmp.properties.XMPProperty
                public Object getValue() {
                    return evaluateNodeValue;
                }

                public String toString() {
                    return evaluateNodeValue.toString();
                }
            };
        }
        throw new XMPException("Property must be simple when a value type is requested", 102);
    }

    @Override // com.adobe.xmp.XMPMeta
    public byte[] getPropertyBase64(String str, String str2) throws XMPException {
        return (byte[]) getPropertyObject(str, str2, 7);
    }

    @Override // com.adobe.xmp.XMPMeta
    public Boolean getPropertyBoolean(String str, String str2) throws XMPException {
        return (Boolean) getPropertyObject(str, str2, 1);
    }

    @Override // com.adobe.xmp.XMPMeta
    public Calendar getPropertyCalendar(String str, String str2) throws XMPException {
        return (Calendar) getPropertyObject(str, str2, 6);
    }

    @Override // com.adobe.xmp.XMPMeta
    public XMPDateTime getPropertyDate(String str, String str2) throws XMPException {
        return (XMPDateTime) getPropertyObject(str, str2, 5);
    }

    @Override // com.adobe.xmp.XMPMeta
    public Double getPropertyDouble(String str, String str2) throws XMPException {
        return (Double) getPropertyObject(str, str2, 4);
    }

    @Override // com.adobe.xmp.XMPMeta
    public Integer getPropertyInteger(String str, String str2) throws XMPException {
        return (Integer) getPropertyObject(str, str2, 2);
    }

    @Override // com.adobe.xmp.XMPMeta
    public Long getPropertyLong(String str, String str2) throws XMPException {
        return (Long) getPropertyObject(str, str2, 3);
    }

    /* access modifiers changed from: protected */
    public Object getPropertyObject(String str, String str2, int i) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode == null) {
            return null;
        }
        if (i == 0 || !findNode.getOptions().isCompositeProperty()) {
            return evaluateNodeValue(i, findNode);
        }
        throw new XMPException("Property must be simple when a value type is requested", 102);
    }

    @Override // com.adobe.xmp.XMPMeta
    public String getPropertyString(String str, String str2) throws XMPException {
        return (String) getPropertyObject(str, str2, 0);
    }

    @Override // com.adobe.xmp.XMPMeta
    public XMPProperty getQualifier(String str, String str2, String str3, String str4) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        return getProperty(str, str2 + XMPPathFactory.composeQualifierPath(str3, str4));
    }

    public XMPNode getRoot() {
        return this.tree;
    }

    @Override // com.adobe.xmp.XMPMeta
    public XMPProperty getStructField(String str, String str2, String str3, String str4) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertStructName(str2);
        return getProperty(str, str2 + XMPPathFactory.composeStructFieldPath(str3, str4));
    }

    @Override // com.adobe.xmp.XMPMeta
    public void insertArrayItem(String str, String str2, int i, String str3) throws XMPException {
        insertArrayItem(str, str2, i, str3, null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void insertArrayItem(String str, String str2, int i, String str3, PropertyOptions propertyOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode != null) {
            doSetArrayItem(findNode, i, str3, propertyOptions, true);
            return;
        }
        throw new XMPException("Specified array does not exist", 102);
    }

    @Override // com.adobe.xmp.XMPMeta
    public XMPIterator iterator() throws XMPException {
        return iterator(null, null, null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public XMPIterator iterator(IteratorOptions iteratorOptions) throws XMPException {
        return iterator(null, null, iteratorOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public XMPIterator iterator(String str, String str2, IteratorOptions iteratorOptions) throws XMPException {
        return new XMPIteratorImpl(this, str, str2, iteratorOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void normalize(ParseOptions parseOptions) throws XMPException {
        if (parseOptions == null) {
            parseOptions = new ParseOptions();
        }
        XMPNormalizer.process(this, parseOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setArrayItem(String str, String str2, int i, String str3) throws XMPException {
        setArrayItem(str, str2, i, str3, null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setArrayItem(String str, String str2, int i, String str3, PropertyOptions propertyOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode != null) {
            doSetArrayItem(findNode, i, str3, propertyOptions, false);
            return;
        }
        throw new XMPException("Specified array does not exist", 102);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setLocalizedText(String str, String str2, String str3, String str4, String str5) throws XMPException {
        setLocalizedText(str, str2, str3, str4, str5, null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:102:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0099, code lost:
        if (r1 == null) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009f, code lost:
        if (r6.getChildrenLength() <= 1) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a1, code lost:
        r6.removeChild(r1);
        r6.addChild(1, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a7, code lost:
        r8 = com.adobe.xmp.impl.XMPNodeUtils.chooseLocalizedText(r6, r9, r10);
        r9 = ((java.lang.Integer) r8[0]).intValue();
        r8 = (com.adobe.xmp.impl.XMPNode) r8[1];
        r2 = com.adobe.xmp.XMPConst.X_DEFAULT.equals(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00bb, code lost:
        if (r9 == 0) goto L_0x015a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bd, code lost:
        if (r9 == 1) goto L_0x010e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c0, code lost:
        if (r9 == 2) goto L_0x00f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c3, code lost:
        if (r9 == 3) goto L_0x00ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c6, code lost:
        if (r9 == 4) goto L_0x00dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c9, code lost:
        if (r9 != 5) goto L_0x00d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00cb, code lost:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r6, r10, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ce, code lost:
        if (r2 == false) goto L_0x0163;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00db, code lost:
        throw new com.adobe.xmp.XMPException("Unexpected result from ChooseLocalizedText", 9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00dc, code lost:
        if (r1 == null) goto L_0x00e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00e2, code lost:
        if (r6.getChildrenLength() != 1) goto L_0x00e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e4, code lost:
        r1.setValue(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00e7, code lost:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r6, r10, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00ec, code lost:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r6, r10, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00ef, code lost:
        if (r2 == false) goto L_0x0163;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00f3, code lost:
        if (r7 == false) goto L_0x010a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f5, code lost:
        if (r1 == r8) goto L_0x010a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00f7, code lost:
        if (r1 == null) goto L_0x010a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0105, code lost:
        if (r1.getValue().equals(r8.getValue()) == false) goto L_0x010a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0107, code lost:
        r1.setValue(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x010a, code lost:
        r8.setValue(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x010e, code lost:
        if (r2 != false) goto L_0x012b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0110, code lost:
        if (r7 == false) goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0112, code lost:
        if (r1 == r8) goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0114, code lost:
        if (r1 == null) goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0122, code lost:
        if (r1.getValue().equals(r8.getValue()) == false) goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0124, code lost:
        r1.setValue(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0127, code lost:
        r8.setValue(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x012b, code lost:
        r8 = r6.iterateChildren();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0133, code lost:
        if (r8.hasNext() == false) goto L_0x0154;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0135, code lost:
        r9 = (com.adobe.xmp.impl.XMPNode) r8.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x013b, code lost:
        if (r9 == r1) goto L_0x012f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x013d, code lost:
        r10 = r9.getValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0141, code lost:
        if (r1 == null) goto L_0x0148;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0143, code lost:
        r2 = r1.getValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0148, code lost:
        r2 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x014d, code lost:
        if (r10.equals(r2) != false) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0150, code lost:
        r9.setValue(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0154, code lost:
        if (r1 == null) goto L_0x0163;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0156, code lost:
        r1.setValue(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x015a, code lost:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r6, com.adobe.xmp.XMPConst.X_DEFAULT, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x015d, code lost:
        if (r2 != false) goto L_0x0162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x015f, code lost:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r6, r10, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0162, code lost:
        r7 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0163, code lost:
        if (r7 != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0169, code lost:
        if (r6.getChildrenLength() != 1) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x016b, code lost:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r6, com.adobe.xmp.XMPConst.X_DEFAULT, r11);
     */
    @Override // com.adobe.xmp.XMPMeta
    public void setLocalizedText(String str, String str2, String str3, String str4, String str5, PropertyOptions propertyOptions) throws XMPException {
        XMPNode xMPNode;
        boolean z;
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        ParameterAsserts.assertSpecificLang(str4);
        String normalizeLangValue = str3 != null ? Utils.normalizeLangValue(str3) : null;
        String normalizeLangValue2 = Utils.normalizeLangValue(str4);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), true, new PropertyOptions(7680));
        if (findNode != null) {
            if (!findNode.getOptions().isArrayAltText()) {
                if (findNode.hasChildren() || !findNode.getOptions().isArrayAlternate()) {
                    throw new XMPException("Specified property is no alt-text array", 102);
                }
                findNode.getOptions().setArrayAltText(true);
            }
            Iterator iterateChildren = findNode.iterateChildren();
            while (true) {
                if (!iterateChildren.hasNext()) {
                    xMPNode = null;
                    z = false;
                    break;
                }
                xMPNode = (XMPNode) iterateChildren.next();
                if (xMPNode.hasQualifier() && XMPConst.XML_LANG.equals(xMPNode.getQualifier(1).getName())) {
                    if (XMPConst.X_DEFAULT.equals(xMPNode.getQualifier(1).getValue())) {
                        z = true;
                        break;
                    }
                }
            }
            throw new XMPException("Language qualifier must be first", 102);
        }
        throw new XMPException("Failed to find or create array node", 102);
    }

    /* access modifiers changed from: package-private */
    public void setNode(XMPNode xMPNode, Object obj, PropertyOptions propertyOptions, boolean z) throws XMPException {
        if (z) {
            xMPNode.clear();
        }
        xMPNode.getOptions().mergeWith(propertyOptions);
        if (!xMPNode.getOptions().isCompositeProperty()) {
            XMPNodeUtils.setNodeValue(xMPNode, obj);
        } else if (obj == null || obj.toString().length() <= 0) {
            xMPNode.removeChildren();
        } else {
            throw new XMPException("Composite nodes can't have values", 102);
        }
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setObjectName(String str) {
        this.tree.setName(str);
    }

    public void setPacketHeader(String str) {
        this.packetHeader = str;
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setProperty(String str, String str2, Object obj) throws XMPException {
        setProperty(str, str2, obj, null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setProperty(String str, String str2, Object obj, PropertyOptions propertyOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        PropertyOptions verifySetOptions = XMPNodeUtils.verifySetOptions(propertyOptions, obj);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), true, verifySetOptions);
        if (findNode != null) {
            setNode(findNode, obj, verifySetOptions, false);
            return;
        }
        throw new XMPException("Specified property does not exist", 102);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyBase64(String str, String str2, byte[] bArr) throws XMPException {
        setProperty(str, str2, bArr, null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyBase64(String str, String str2, byte[] bArr, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, bArr, propertyOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyBoolean(String str, String str2, boolean z) throws XMPException {
        setProperty(str, str2, z ? XMPConst.TRUESTR : XMPConst.FALSESTR, null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyBoolean(String str, String str2, boolean z, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, z ? XMPConst.TRUESTR : XMPConst.FALSESTR, propertyOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyCalendar(String str, String str2, Calendar calendar) throws XMPException {
        setProperty(str, str2, calendar, null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyCalendar(String str, String str2, Calendar calendar, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, calendar, propertyOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyDate(String str, String str2, XMPDateTime xMPDateTime) throws XMPException {
        setProperty(str, str2, xMPDateTime, null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyDate(String str, String str2, XMPDateTime xMPDateTime, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, xMPDateTime, propertyOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyDouble(String str, String str2, double d2) throws XMPException {
        setProperty(str, str2, new Double(d2), null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyDouble(String str, String str2, double d2, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, new Double(d2), propertyOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyInteger(String str, String str2, int i) throws XMPException {
        setProperty(str, str2, new Integer(i), null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyInteger(String str, String str2, int i, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, new Integer(i), propertyOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyLong(String str, String str2, long j) throws XMPException {
        setProperty(str, str2, new Long(j), null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setPropertyLong(String str, String str2, long j, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, new Long(j), propertyOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setQualifier(String str, String str2, String str3, String str4, String str5) throws XMPException {
        setQualifier(str, str2, str3, str4, str5, null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setQualifier(String str, String str2, String str3, String str4, String str5, PropertyOptions propertyOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        if (doesPropertyExist(str, str2)) {
            setProperty(str, str2 + XMPPathFactory.composeQualifierPath(str3, str4), str5, propertyOptions);
            return;
        }
        throw new XMPException("Specified property does not exist!", 102);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setStructField(String str, String str2, String str3, String str4, String str5) throws XMPException {
        setStructField(str, str2, str3, str4, str5, null);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void setStructField(String str, String str2, String str3, String str4, String str5, PropertyOptions propertyOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertStructName(str2);
        setProperty(str, str2 + XMPPathFactory.composeStructFieldPath(str3, str4), str5, propertyOptions);
    }

    @Override // com.adobe.xmp.XMPMeta
    public void sort() {
        this.tree.sort();
    }
}

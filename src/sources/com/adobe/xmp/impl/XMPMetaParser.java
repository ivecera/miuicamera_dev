package com.adobe.xmp.impl;

import com.adobe.xmp.XMPConst;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.options.ParseOptions;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMPMetaParser {
    private static final Object XMP_RDF = new Object();
    private static DocumentBuilderFactory factory = createDocumentBuilderFactory();

    private XMPMetaParser() {
    }

    private static DocumentBuilderFactory createDocumentBuilderFactory() {
        DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
        newInstance.setNamespaceAware(true);
        newInstance.setIgnoringComments(true);
        try {
            newInstance.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        } catch (Exception unused) {
        }
        return newInstance;
    }

    private static Object[] findRootNode(Node node, boolean z, Object[] objArr) {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (7 == item.getNodeType()) {
                ProcessingInstruction processingInstruction = (ProcessingInstruction) item;
                if (processingInstruction.getTarget() == XMPConst.XMP_PI) {
                    if (objArr != null) {
                        objArr[2] = processingInstruction.getData();
                    }
                }
            }
            if (!(3 == item.getNodeType() || 7 == item.getNodeType())) {
                String namespaceURI = item.getNamespaceURI();
                String localName = item.getLocalName();
                if ((XMPConst.TAG_XMPMETA.equals(localName) || XMPConst.TAG_XAPMETA.equals(localName)) && XMPConst.NS_X.equals(namespaceURI)) {
                    return findRootNode(item, false, objArr);
                }
                if (z || !"RDF".equals(localName) || !XMPConst.NS_RDF.equals(namespaceURI)) {
                    Object[] findRootNode = findRootNode(item, z, objArr);
                    if (findRootNode != null) {
                        return findRootNode;
                    }
                } else {
                    if (objArr != null) {
                        objArr[0] = item;
                        objArr[1] = XMP_RDF;
                    }
                    return objArr;
                }
            }
        }
        return null;
    }

    public static XMPMeta parse(Object obj, ParseOptions parseOptions) throws XMPException {
        ParameterAsserts.assertNotNull(obj);
        if (parseOptions == null) {
            parseOptions = new ParseOptions();
        }
        Object[] findRootNode = findRootNode(parseXml(obj, parseOptions), parseOptions.getRequireXMPMeta(), new Object[3]);
        if (findRootNode == null || findRootNode[1] != XMP_RDF) {
            return new XMPMetaImpl();
        }
        XMPMetaImpl parse = ParseRDF.parse((Node) findRootNode[0]);
        parse.setPacketHeader((String) findRootNode[2]);
        if (!parseOptions.getOmitNormalization()) {
            XMPNormalizer.process(parse, parseOptions);
        }
        return parse;
    }

    private static Document parseInputSource(InputSource inputSource) throws XMPException {
        try {
            DocumentBuilder newDocumentBuilder = factory.newDocumentBuilder();
            newDocumentBuilder.setErrorHandler(null);
            return newDocumentBuilder.parse(inputSource);
        } catch (SAXException e2) {
            throw new XMPException("XML parsing failure", 201, e2);
        } catch (ParserConfigurationException e3) {
            throw new XMPException("XML Parser not correctly configured", 0, e3);
        } catch (IOException e4) {
            throw new XMPException("Error reading the XML-file", 204, e4);
        }
    }

    private static Document parseXml(Object obj, ParseOptions parseOptions) throws XMPException {
        return obj instanceof InputStream ? parseXmlFromInputStream((InputStream) obj, parseOptions) : obj instanceof byte[] ? parseXmlFromBytebuffer(new ByteBuffer((byte[]) obj), parseOptions) : parseXmlFromString((String) obj, parseOptions);
    }

    private static Document parseXmlFromBytebuffer(ByteBuffer byteBuffer, ParseOptions parseOptions) throws XMPException {
        try {
            return parseInputSource(new InputSource(byteBuffer.getByteStream()));
        } catch (XMPException e2) {
            if (e2.getErrorCode() == 201 || e2.getErrorCode() == 204) {
                if (parseOptions.getAcceptLatin1()) {
                    byteBuffer = Latin1Converter.convert(byteBuffer);
                }
                if (!parseOptions.getFixControlChars()) {
                    return parseInputSource(new InputSource(byteBuffer.getByteStream()));
                }
                try {
                    return parseInputSource(new InputSource(new FixASCIIControlsReader(new InputStreamReader(byteBuffer.getByteStream(), byteBuffer.getEncoding()))));
                } catch (UnsupportedEncodingException unused) {
                    throw new XMPException("Unsupported Encoding", 9, e2);
                }
            } else {
                throw e2;
            }
        }
    }

    private static Document parseXmlFromInputStream(InputStream inputStream, ParseOptions parseOptions) throws XMPException {
        if (!parseOptions.getAcceptLatin1() && !parseOptions.getFixControlChars()) {
            return parseInputSource(new InputSource(inputStream));
        }
        try {
            return parseXmlFromBytebuffer(new ByteBuffer(inputStream), parseOptions);
        } catch (IOException e2) {
            throw new XMPException("Error reading the XML-file", 204, e2);
        }
    }

    private static Document parseXmlFromString(String str, ParseOptions parseOptions) throws XMPException {
        try {
            return parseInputSource(new InputSource(new StringReader(str)));
        } catch (XMPException e2) {
            if (e2.getErrorCode() == 201 && parseOptions.getFixControlChars()) {
                return parseInputSource(new InputSource(new FixASCIIControlsReader(new StringReader(str))));
            }
            throw e2;
        }
    }
}

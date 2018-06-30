package pw.phylame.commons.xml;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.var;
import org.xmlpull.v1.XmlSerializer;
import pw.phylame.commons.text.StringUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class XmlRender {
    private static final String SERIALIZER_INDENTATION_KEY = "http://xmlpull.org/v1/doc/properties.html#serializer-indentation";
    private static final String SERIALIZER_LINE_SEPARATOR_KEY = "http://xmlpull.org/v1/doc/properties.html#serializer-line-separator";

    private final XmlSerializer serializer;

    public String indentation() {
        return (String) serializer.getProperty(SERIALIZER_INDENTATION_KEY);
    }

    public XmlRender indentation(String indentation) {
        serializer.setProperty(SERIALIZER_INDENTATION_KEY, indentation);
        return this;
    }

    public String lineSeparator() {
        return (String) serializer.getProperty(SERIALIZER_LINE_SEPARATOR_KEY);
    }

    public XmlRender lineSeparator(String lineSeparator) {
        serializer.setProperty(SERIALIZER_LINE_SEPARATOR_KEY, lineSeparator);
        return this;
    }

    public XmlRender startXml(String encoding) throws IOException {
        return startXml(encoding, true);
    }

    public XmlRender startXml(String encoding, Boolean standalone) throws IOException {
        serializer.startDocument(encoding, standalone);
        serializer.text(lineSeparator());
        return this;
    }

    public XmlRender doctype(String root) throws IOException {
        serializer.docdecl(" $root");
        serializer.text(lineSeparator());
        return this;
    }

    public XmlRender docdecl(String root, String id, String uri) throws IOException {
        serializer.docdecl(" " + root + " PUBLIC \"" + id + "\" \"" + uri + "\"");
        serializer.text(lineSeparator());
        return this;
    }

    public XmlRender startTag(String name) throws IOException {
        serializer.startTag(null, name);
        return this;
    }

    public XmlRender startTag(String namespace, String name) throws IOException {
        serializer.startTag(namespace, name);
        return this;
    }

    public XmlRender startTag(String prefix, String namespace, String name) throws IOException {
        serializer.setPrefix(prefix, namespace);
        serializer.startTag(null, name);
        return this;
    }

    public XmlRender attr(String name, String value) throws IOException {
        serializer.attribute(null, name, value);
        return this;
    }

    public XmlRender attr(String namespace, String name, String value) throws IOException {
        serializer.attribute(namespace, name, value);
        return this;
    }

    public XmlRender lang(String lang) throws IOException {
        return attr("xml:lang", lang);
    }

    public XmlRender xmlns(String xmlns) throws IOException {
        return attr("xmlns", xmlns);
    }

    public XmlRender comm(String text) throws IOException {
        val ln = lineSeparator();
        var indent = indentation();
        if (indent == null) {
            indent = "";
        }
        serializer.text(ln);
        serializer.text(StringUtils.repeat(indent, serializer.getDepth()));
        serializer.comment(text);
        serializer.text(ln);
        serializer.text(StringUtils.repeat(indent, serializer.getDepth()));
        return this;
    }

    public XmlRender text(String text) throws IOException {
        serializer.text(text);
        return this;
    }

    public XmlRender text(char[] buf, int start, int len) throws IOException {
        serializer.text(buf, start, len);
        return this;
    }

    public XmlRender endTag() throws IOException {
        serializer.endTag(null, serializer.getName());
        return this;
    }

    public XmlRender endTag(String namespace, String name) throws IOException {
        serializer.endTag(namespace, name);
        return this;
    }

    public XmlRender endXml() throws IOException {
        serializer.endDocument();
        return this;
    }

    public XmlRender flush() throws IOException {
        serializer.flush();
        return this;
    }
}

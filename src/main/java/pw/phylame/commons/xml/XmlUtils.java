package pw.phylame.commons.xml;

import lombok.SneakyThrows;
import lombok.val;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public final class XmlUtils {
    @SneakyThrows(XmlPullParserException.class)
    public static XmlRender newRender(OutputStream output, String encoding, String indent, String newLine) throws IOException {
        val serializer = XmlPullParserFactory.newInstance().newSerializer();
        serializer.setOutput(output, encoding);
        return new XmlRender(serializer)
                .lineSeparator(newLine)
                .indentation(indent);
    }

    @SneakyThrows(XmlPullParserException.class)
    public static XmlRender newRender(Writer writer, String indent, String newLine) throws IOException {
        val serializer = XmlPullParserFactory.newInstance().newSerializer();
        serializer.setOutput(writer);
        return new XmlRender(serializer)
                .lineSeparator(newLine)
                .indentation(indent);
    }

    public static XmlPullParser newParser() {
        return newParser(false, false);
    }

    @SneakyThrows(XmlPullParserException.class)
    public static XmlPullParser newParser(boolean namespaceAware, boolean validating) {
        val factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(namespaceAware);
        factory.setValidating(validating);
        return factory.newPullParser();
    }
}

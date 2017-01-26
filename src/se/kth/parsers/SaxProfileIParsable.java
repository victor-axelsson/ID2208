package se.kth.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import se.kth.ns.jobservicecompany.Profile;

/**
 * Created by victoraxelsson on 2017-01-26.
 */
public class SaxProfileIParsable implements IParsable {
    @Override
    public Profile parse(String username) {
        return null;
    }
}

class CompanyHandler extends DefaultHandler {

    Profile profile;

    public CompanyHandler(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }
}
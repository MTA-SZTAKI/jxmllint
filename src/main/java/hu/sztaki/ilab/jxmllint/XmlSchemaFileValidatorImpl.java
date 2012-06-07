package hu.sztaki.ilab.jxmllint;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.log4j.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Molnár Péter <molnarp@ilab.sztaki.hu>
 */
public class XmlSchemaFileValidatorImpl implements FileValidator {
    
    private static final Logger LOG = Logger.getLogger(XmlSchemaFileValidatorImpl.class);
    private boolean verbose = false;
    
    @Override
    public void validate(File f, File schemaFile) {
        try {
            // Load schema file
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaFile);

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            saxParserFactory.setSchema(schema);
            
            SAXParser parser = saxParserFactory.newSAXParser();
            
            ErrorHandlerImpl errorHandler = new ErrorHandlerImpl();
            errorHandler.setVerbose(verbose);
            
            parser.parse(f, errorHandler);            
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        
    }

    public boolean isVerbose() {
        return verbose;
    }

    @Override
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    
}

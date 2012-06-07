/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.sztaki.ilab.jxmllint;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DefaultHandler2;

/**
 *
 * @author Molnár Péter <molnarp@ilab.sztaki.hu>
 */
public class ErrorHandlerImpl extends DefaultHandler2 {
    
    private static final Logger LOG = Logger.getLogger(ErrorHandlerImpl.class);
    private boolean verbose = false;

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        if (verbose) {
            LOG.warn(exception.getMessage(), exception);
        } else {
            LOG.warn(exception.getMessage());
        }
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        if (verbose) {
            LOG.error(exception.getMessage(), exception);
        } else {
            LOG.error(exception.getMessage());
        }
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        if (verbose) {
            LOG.fatal(exception.getMessage(), exception);
        } else {
            LOG.fatal(exception.getMessage());
        }
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}

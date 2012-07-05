/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.sztaki.ilab.jxmllint;

import com.thaiopensource.validate.ValidationDriver;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author Molnár Péter <molnarp@ilab.sztaki.hu>
 */
public class JingFileValidatorImpl implements FileValidator {
    /** Logger. */
    private static final Logger LOG = Logger.getLogger(JingFileValidatorImpl.class);
    /** Enables verbose operation - unused with Jing. */
    private boolean verbose = false;

    @Override
    public void validate(File f, File schemaFile) {
        try {
            ValidationDriver validationDriver = new ValidationDriver();
            validationDriver.loadSchema(ValidationDriver.fileInputSource(schemaFile));
            validationDriver.validate(ValidationDriver.fileInputSource(f));
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);            
        }
    }

    @Override
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

}

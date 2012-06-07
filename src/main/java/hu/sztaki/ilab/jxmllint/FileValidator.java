/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.sztaki.ilab.jxmllint;

import java.io.File;

/**
 *
 * @author Molnár Péter <molnarp@ilab.sztaki.hu>
 */
public interface FileValidator {

    public void validate(File f, File schema);
    public void setVerbose(boolean verbose);
}

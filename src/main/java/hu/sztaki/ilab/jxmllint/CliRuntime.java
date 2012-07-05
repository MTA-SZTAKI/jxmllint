/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.sztaki.ilab.jxmllint;

import java.io.File;
import javax.xml.XMLConstants;
import org.apache.commons.cli.*;
import org.apache.log4j.*;

/**
 *
 * @author Molnár Péter <molnarp@ilab.sztaki.hu>
 */
public class CliRuntime {
    
    private static final Logger LOG = Logger.getLogger(CliRuntime.class);
    
    public static void main(String args[]) {
        CliRuntime runtime = new CliRuntime();
        runtime.run(args);
    }

    public void run(String args[]) {
        configureLogging();
        
        Parser parser = new GnuParser();
        Options options = getOptions();
        try {
            CommandLine cli = parser.parse(options, args);
            
            // Check help option
            if (cli.hasOption('h')) {
                printHelp(options);
                System.exit(0);
            }
            
            // Check if target file was defined.
            if (! cli.hasOption("f")) {
                LOG.error("Validated file must be defined.");
                System.exit(1);
            }
            // Assign file
            File f = new File(cli.getOptionValue("file"));
            
            // Assign schema file
            FileValidator fileValidator = null;
            File schemaFile = null;
            if (cli.hasOption("s")) {
                fileValidator = new JaxpFileValidatorImpl();
                schemaFile = new File(cli.getOptionValue("schema"));
            }
            else if (cli.hasOption("r")) {
                fileValidator = new JingFileValidatorImpl();
                schemaFile = new File(cli.getOptionValue("relaxng"));
            } else {
                LOG.error("Schema file must be defined.");
                System.exit(2);                   
            }

            // Validate against schema

            // Enable verbose mode
            if (cli.hasOption("v")) {
                fileValidator.setVerbose(true);
            }
            
            fileValidator.validate(f, schemaFile);
            
            
        } catch (ParseException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        
    }
    
    public static Options getOptions() {
        Options options = new Options();
        
        // Add main options
        options.addOption("h", "help", false, "Prints this help screen.");
        options.addOption("v", "verbose", false, "Enables verbose mode.");
        options.addOption("s", "schema", true, "The W3C XML Schema file to validate against.");
        options.addOption("r", "relaxng", true, "The Relax-NG schema file to validate against.");
        options.addOption("f", "file", true, "The XML document that is validated.");
        
        return options;
    }
    
    public static void configureLogging() {
        // Configure very basicly by hand
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.removeAllAppenders();
        rootLogger.setLevel(Level.INFO);
        
        Appender consoleAppender = new ConsoleAppender(new PatternLayout("%p: %m%n"));
        rootLogger.addAppender(consoleAppender);
    }
    
    public static void printHelp(Options options) {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp("jxmllint <OPTIONS>", "\nXML validation tool in Java.\n\n", options,
                "\nCopyright (c) 2012, MTA SZTAKI. Web: http://dms.sztaki.hu/\n\n");
    }
    
}

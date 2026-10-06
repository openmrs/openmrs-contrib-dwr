package org.openmrs.contrib.dwr;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.openmrs.contrib.dwr.impl.DTDEntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 * Validates config files against dwr20.dtd the same way DwrXmlConfigurator does.
 */
public class Dwr20DtdTest extends TestCase
{
    private static final String DOCTYPE = "<!DOCTYPE dwr PUBLIC \"-//GetAhead Limited//DTD Direct Web Remoting 2.0//EN\" \"http://directwebremoting.org/schema/dwr20.dtd\">";

    /**
     * The shape of the dwr-modules.xml that OpenMRS writes: each started module's init, allow
     * and signatures elements, tagged with the id of the module that added them.
     */
    public void testShouldAcceptTheEntriesOfSeveralModulesTaggedWithTheirModuleId() throws Exception
    {
        String xml = DOCTYPE + """
            <dwr>
              <init moduleId="first">
                <creator id="firstCreator" class="org.openmrs.contrib.dwr.create.NewCreator"/>
              </init>
              <allow moduleId="first">
                <create creator="new" javascript="FirstService">
                  <param name="class" value="java.util.Date"/>
                </create>
              </allow>
              <signatures moduleId="first">import java.util.List;</signatures>
              <allow moduleId="second">
                <create creator="new" javascript="SecondService">
                  <param name="class" value="java.util.Date"/>
                </create>
              </allow>
              <signatures moduleId="second">import java.util.Map;</signatures>
            </dwr>
            """;

        assertEquals(new ArrayList<String>(), validate(xml));
    }

    public void testShouldStillRejectAnUndeclaredAttribute() throws Exception
    {
        List<String> problems = validate(DOCTYPE + "<dwr><allow owner=\"first\"/></dwr>");

        assertEquals(problems.toString(), 1, problems.size());
        assertTrue(problems.get(0), problems.get(0).contains("\"owner\""));
    }

    private static List<String> validate(String xml) throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setEntityResolver(new DTDEntityResolver());

        final List<String> problems = new ArrayList<String>();
        db.setErrorHandler(new ErrorHandler()
        {
            public void warning(SAXParseException ex)
            {
                problems.add(ex.getMessage());
            }

            public void error(SAXParseException ex)
            {
                problems.add(ex.getMessage());
            }

            public void fatalError(SAXParseException ex)
            {
                problems.add(ex.getMessage());
            }
        });

        db.parse(new InputSource(new StringReader(xml)));
        return problems;
    }
}

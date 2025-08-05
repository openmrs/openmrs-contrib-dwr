/*
 * Copyright 2005 Joe Walker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openmrs.contrib.dwr.convert;

import java.net.MalformedURLException;
import java.net.URL;

import org.openmrs.contrib.dwr.dwrp.SimpleOutboundVariable;
import org.openmrs.contrib.dwr.extend.Converter;
import org.openmrs.contrib.dwr.extend.InboundContext;
import org.openmrs.contrib.dwr.extend.InboundVariable;
import org.openmrs.contrib.dwr.extend.MarshallException;
import org.openmrs.contrib.dwr.extend.OutboundContext;
import org.openmrs.contrib.dwr.extend.OutboundVariable;
import org.openmrs.contrib.dwr.util.JavascriptUtil;
import org.openmrs.contrib.dwr.util.LocalUtil;
import org.openmrs.contrib.dwr.util.Logger;

/**
 * An implementation of Converter for Strings.
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id: URLConverter.java 932 2006-10-08 09:22:50Z joe_walker $
 */
public class URLConverter extends BaseV20Converter implements Converter
{
    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertInbound(java.lang.Class, org.directwebremoting.InboundVariable, org.directwebremoting.InboundContext)
     */
    public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
    {
        String urlString = LocalUtil.decode(iv.getValue());
        try
        {
            return new URL(urlString);
        }
        catch (MalformedURLException ex)
        {
            log.warn("Failed to create URL from string '" + urlString + "'. Returning null");
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertOutbound(java.lang.Object, org.directwebremoting.OutboundContext)
     */
    public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException
    {
        URL url = (URL) data;
        String escaped = JavascriptUtil.escapeJavaScript(url.toExternalForm());
        return new SimpleOutboundVariable('\"' + escaped + '\"', outctx, true);
    }

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(URLConverter.class);
}

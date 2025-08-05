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

import java.io.StringReader;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;

import org.openmrs.contrib.dwr.dwrp.SimpleOutboundVariable;
import org.openmrs.contrib.dwr.extend.Converter;
import org.openmrs.contrib.dwr.extend.InboundContext;
import org.openmrs.contrib.dwr.extend.InboundVariable;
import org.openmrs.contrib.dwr.extend.MarshallException;
import org.openmrs.contrib.dwr.extend.OutboundContext;
import org.openmrs.contrib.dwr.extend.OutboundVariable;
import org.openmrs.contrib.dwr.extend.EnginePrivate;
import org.openmrs.contrib.dwr.util.LocalUtil;

/**
 * An implementation of Converter for DOM objects.
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id: StringConverter.java,v 1.2 2004/11/04 15:54:07 joe_walker Exp $
 */
public class XOMConverter extends BaseV20Converter implements Converter
{
    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertInbound(java.lang.Class, org.directwebremoting.InboundVariable, org.directwebremoting.InboundContext)
     */
    public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
    {
        String value = LocalUtil.decode(iv.getValue());

        try
        {
            Builder builder = new Builder();
            Document doc = builder.build(new StringReader(value));

            if (paramType == Document.class)
            {
                return doc;
            }
            else if (paramType == Element.class)
            {
                return doc.getRootElement();
            }

            throw new MarshallException(paramType);
        }
        catch (MarshallException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new MarshallException(paramType, ex);
        }
    }

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertOutbound(java.lang.Object, org.directwebremoting.OutboundContext)
     */
    public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException
    {
        try
        {
            // Using XSLT to convert to a stream. Setup the source
            if (!(data instanceof Node))
            {
                throw new MarshallException(data.getClass());
            }

            Node node = (Node) data;

            String script = EnginePrivate.xmlStringToJavascriptDom(node.toXML());
            OutboundVariable ov = new SimpleOutboundVariable(script, outctx, false);

            outctx.put(data, ov);

            return ov;
        }
        catch (MarshallException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new MarshallException(data.getClass(), ex);
        }
    }
}

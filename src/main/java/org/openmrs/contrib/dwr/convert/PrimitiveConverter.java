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

import org.openmrs.contrib.dwr.dwrp.SimpleOutboundVariable;
import org.openmrs.contrib.dwr.extend.Converter;
import org.openmrs.contrib.dwr.extend.InboundContext;
import org.openmrs.contrib.dwr.extend.InboundVariable;
import org.openmrs.contrib.dwr.extend.MarshallException;
import org.openmrs.contrib.dwr.extend.OutboundContext;
import org.openmrs.contrib.dwr.extend.OutboundVariable;
import org.openmrs.contrib.dwr.util.JavascriptUtil;
import org.openmrs.contrib.dwr.util.LocalUtil;
import org.openmrs.contrib.dwr.util.Messages;

/**
 * Converter for all primitive types
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class PrimitiveConverter extends BaseV20Converter implements Converter
{
    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertInbound(java.lang.Class, org.directwebremoting.InboundVariable, org.directwebremoting.InboundContext)
     */
    public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
    {
        String value = iv.getValue();
        value = LocalUtil.decode(value.trim());

        try
        {
            return LocalUtil.simpleConvert(value, paramType);
        }
        catch (NumberFormatException ex)
        {
            throw new MarshallException(paramType, Messages.getString("PrimitiveConverter.FormatError", value));
        }
        catch (IllegalArgumentException ex)
        {
            throw new MarshallException(paramType);
        }
    }

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertOutbound(java.lang.Object, org.directwebremoting.OutboundContext)
     */
    public OutboundVariable convertOutbound(Object object, OutboundContext outctx)
    {
        Class paramType = object.getClass();

        if (object.equals(Boolean.TRUE))
        {
            return new SimpleOutboundVariable("true", outctx, true);
        }
        else if (object.equals(Boolean.FALSE))
        {
            return new SimpleOutboundVariable("false", outctx, true);
        }
        else if (paramType == Character.class)
        {
            // Treat characters as strings
            return new SimpleOutboundVariable('\"' + JavascriptUtil.escapeJavaScript(object.toString()) + '\"', outctx, true);
        }
        else
        {
            // We just use the default toString for all numbers
            return new SimpleOutboundVariable(object.toString(), outctx, true);
        }
    }
}

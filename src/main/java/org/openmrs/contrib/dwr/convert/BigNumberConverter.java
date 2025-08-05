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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.openmrs.contrib.dwr.dwrp.SimpleOutboundVariable;
import org.openmrs.contrib.dwr.extend.Converter;
import org.openmrs.contrib.dwr.extend.InboundContext;
import org.openmrs.contrib.dwr.extend.InboundVariable;
import org.openmrs.contrib.dwr.extend.MarshallException;
import org.openmrs.contrib.dwr.extend.OutboundContext;
import org.openmrs.contrib.dwr.extend.OutboundVariable;
import org.openmrs.contrib.dwr.util.Messages;

/**
 * Converter for all primitive types
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class BigNumberConverter extends BaseV20Converter implements Converter
{
    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertInbound(java.lang.Class, org.directwebremoting.InboundVariable, org.directwebremoting.InboundContext)
     */
    public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
    {
        String value = iv.getValue();

        if (value == null || value.length() == 0)
        {
            return null;
        }

        try
        {
            if (paramType == BigDecimal.class)
            {
                return new BigDecimal(value.trim());
            }

            if (paramType == BigInteger.class)
            {
                return new BigInteger(value.trim());
            }

            throw new MarshallException(paramType);
        }
        catch (NumberFormatException ex)
        {
            throw new MarshallException(paramType, Messages.getString("BigNumberConverter.FormatError", value));
        }
    }

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertOutbound(java.lang.Object, org.directwebremoting.OutboundContext)
     */
    public OutboundVariable convertOutbound(Object object, OutboundContext outctx)
    {
        if (object == null)
        {
            return new SimpleOutboundVariable("null", outctx, true);
        }

        return new SimpleOutboundVariable(object.toString(), outctx, true);
    }
}

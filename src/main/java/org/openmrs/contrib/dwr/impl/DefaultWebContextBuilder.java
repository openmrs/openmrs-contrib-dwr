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
package org.openmrs.contrib.dwr.impl;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.openmrs.contrib.dwr.Container;
import org.openmrs.contrib.dwr.WebContext;
import org.openmrs.contrib.dwr.WebContextFactory.WebContextBuilder;
import org.openmrs.contrib.dwr.util.Logger;

/**
 * A WebContextBuilder that creates DefaultWebContexts.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class DefaultWebContextBuilder implements WebContextBuilder
{
    /* (non-Javadoc)
     * @see org.directwebremoting.WebContextBuilder#set(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, jakarta.servlet.ServletConfig, jakarta.servlet.ServletContext, org.directwebremoting.Container)
     */
    public void set(HttpServletRequest request, HttpServletResponse response, ServletConfig config, ServletContext context, Container container)
    {
        try
        {
            WebContext ec = new DefaultWebContext(request, response, config, context, container);
            user.set(ec);
        }
        catch (Exception ex)
        {
            log.fatal("Failed to create an ExecutionContext", ex);
        }
    }

    /* (non-Javadoc)
     * @see org.directwebremoting.WebContextBuilder#get()
     */
    public WebContext get()
    {
        return (WebContext) user.get();
    }

    /* (non-Javadoc)
     * @see org.directwebremoting.WebContextBuilder#unset()
     */
    public void unset()
    {
        user.set(null);
    }

    /**
     * The storage of thread based data
     */
    private static ThreadLocal user = new ThreadLocal();

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(DefaultWebContextBuilder.class);
}

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
package org.openmrs.contrib.dwr.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.openmrs.contrib.dwr.extend.DebugPageGenerator;
import org.openmrs.contrib.dwr.extend.Handler;
import org.openmrs.contrib.dwr.util.MimeConstants;

/**
 * A handler for requests to create a debug index page
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class IndexHandler implements Handler
{
    /* (non-Javadoc)
     * @see org.directwebremoting.Handler#handle(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse)
     */
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String page = debugPageGenerator.generateIndexPage(request.getContextPath() + request.getServletPath());

        response.setContentType(MimeConstants.MIME_HTML);
        PrintWriter out = response.getWriter();
        out.print(page);
    }

    /**
     * Setter for the debug page generator
     * @param debugPageGenerator
     */
    public void setDebugPageGenerator(DebugPageGenerator debugPageGenerator)
    {
        this.debugPageGenerator = debugPageGenerator;
    }

    /**
     * The bean to handle debug page requests
     */
    protected DebugPageGenerator debugPageGenerator = null;
}

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.openmrs.contrib.dwr.extend.Handler;
import org.openmrs.contrib.dwr.util.Logger;

/**
 * Display a 404 "not found" message
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class NotFoundHandler implements Handler
{
    /* (non-Javadoc)
     * @see org.directwebremoting.Handler#handle(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse)
     */
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        log.warn("Page not found. pathInfo='" + request.getPathInfo() + "' requestUrl='" + request.getRequestURI() + "'");
        log.warn("In debug/test mode try viewing /[WEB-APP]/dwr/");

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(NotFoundHandler.class);
}

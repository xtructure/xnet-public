/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xsim.
 *
 * xsim is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xsim is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xsim.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.xtructure.xsim.gui.impl;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * A set of GUI utilities.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class GuiUtils
{
    /** The logger for this class. */
    //private static final XLogger LOGGER = XLogger.getInstance(GuiUtils.class);

    /**
     * Loads the named image icon resource.
     * 
     * @param resourceName
     *            the name of the resource to load
     * 
     * @return the named image icon resource
     */
    public static final ImageIcon loadImageIconResource(
            final String resourceName)
    {
        if (resourceName == null)
        {
            return null;
        }
        final String fullResourceName = String.format(
            "/com/xtructure/xsim/gui/%s.png", resourceName);
        final URL url = GuiUtils.class.getResource(fullResourceName);
        if (url == null)
        {
            //LOGGER.warn("could not find icon resource %s", fullResourceName);
            return null;
        }
        return new ImageIcon(url, resourceName);
    }

    /** Creates new GUI utilities. */
    private GuiUtils()
    {
        super();
    }
}

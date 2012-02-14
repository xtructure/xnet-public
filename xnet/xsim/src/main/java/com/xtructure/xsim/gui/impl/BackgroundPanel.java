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

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A background panel.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
final class BackgroundPanel
        extends JPanel
{
    /** The serial version UID of this class (FIXME). */
    private static final long serialVersionUID = 1L;

    /** Creates a new background panel. */
    BackgroundPanel()
    {
        super();

        setLayout(new BorderLayout());
        add(new JLabel(GuiUtils.loadImageIconResource("lorenzattractor-640")),
            BorderLayout.CENTER);
    }
}

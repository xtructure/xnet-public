/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xutil.
 *
 * xutil is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xutil is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xutil.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.xtructure.xutil.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang.time.FastDateFormat;
import org.testng.ITestResult;

/**
 * Information about an entity.
 * 
 * @param <C>
 *            the type of child of this entity
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
abstract class AbstractEntityInfo<C extends EntityInfo>
        implements EntityInfo
{
    /** The date format used for timestamping reports. */
    private static final FastDateFormat DATE_FORMAT = FastDateFormat
        .getInstance("yyyy.MM.dd hh:mm:ss z");

    /** The name of the entity described by this information. */
    private final String _name;

    /** The statistics about the entity described by this information. */
    private final Stats _stats = Stats.getInstance();

    /** Information about the children of this entity. */
    private final Map<String, C> _children = new HashMap<String, C>();

    /**
     * Creates new entity information.
     * 
     * @param name
     *            the name of the entity described by this information
     */
    protected AbstractEntityInfo(
            final String name)
    {
        super();

        _name = name;
    }

    /** {@inheritDoc} */
    @Override
	public final String getName()
    {
        return _name;
    }

    /** {@inheritDoc} */
    @Override
	public String getRelativeFileName()
    {
        return String.format(_name + ".html");
    }

    /** {@inheritDoc} */
    @Override
	public final Stats getStats()
    {
        return _stats;
    }

    /** {@inheritDoc} */
    @Override
	public final void process(
            final ITestResult result)
    {
        switch (result.getStatus())
        {
            case ITestResult.SUCCESS:
                _stats.incrementNumPassed();
                break;

            case ITestResult.SKIP:
                _stats.incrementNumSkipped();
                break;

            case ITestResult.FAILURE:
            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
                _stats.incrementNumFailed();
                break;

            default:
                // don't know what to do...
                return;
        }

        final C child = getChild(result);
        if (child != null)
        {
            child.process(result);
        }
    }

    /** {@inheritDoc} */
    @Override
	public final void write(
            final File rootDir)
        throws IOException
    {
        final PrintWriter out = new PrintWriter( //
            new File(rootDir, getRelativeFileName()));

        out.printf("<html><head><title>TestNG report for %s</title>%n"
                + "<link rel=\"stylesheet\" " //
                + "href=\"xtructure-testng.css\" type=\"text/css\">%n" //
                + "</head>%n", _name);
        out.printf("<body><h1>TestNG report for %s</h1>%n", _name);
        out.printf("<p>This report was generated on %s.</p>%n", //
            DATE_FORMAT.format(new Date()));
        out.printf("<h2>Summary</h2>%n");
        out.printf("<table class=\"summary\">%n<tbody>%n"
                + "<tr><th>Failed</th>%s", //
            generateCell("failed", //
                _stats.getNumFailed(), _stats.getPercentFailed()));
        out.printf("<tr><th>Skipped</th>%s", //
            generateCell("skipped", //
                _stats.getNumSkipped(), _stats.getPercentSkipped()));
        out.printf("<tr><th>Passed</th>%s</tbody></table>", //
            generateCell("passed", //
                _stats.getNumPassed(), _stats.getPercentPassed()));

        if (!_children.keySet().isEmpty())
        {
            out.printf("<h2>Detailed report</h2>%n");
            out.printf("<table class=\"details\">%n<thead>%n");
            out.printf("%s%n", getDetailTableHeader());
            out.printf("</thead>%n<tbody>%n");
            for (final String childName : new TreeSet<String>(_children
                .keySet()))
            {
                out.printf("%s%n", getDetailTableRow(_children.get(childName)));
            }
            out.printf("</tbody>%n</table>%n");
        }
        out.printf("</body>%n</html>%n");

        out.close();

        for (final String childName : _children.keySet())
        {
            final C child = _children.get(childName);
            if (child.getRelativeFileName() != null)
            {
                child.write(rootDir);
            }
        }
    }

    /**
     * Returns the child corresponding to the given result.
     * 
     * @param result
     *            the result to be used as a basis for the returned result
     * 
     * @return the child corresponding to the given result
     */
    protected C getChild(
            final ITestResult result)
    {
        if (getChildName(result) == null)
        {
            return null;
        }

        if (!_children.containsKey(getChildName(result)))
        {
            _children.put(getChildName(result), newChildInstance(result));
        }
        return _children.get(getChildName(result));
    }

    /**
     * Returns a row of the detail table corresponding to the given child.
     * 
     * @param child
     *            the child, the detail table row for which should be returned
     * 
     * @return a row of the detail table corresponding to the given child
     */
    protected String getDetailTableRow(
            final C child)
    {
        final Stats stats = child.getStats();

        final StringBuilder tmp = new StringBuilder();

        // row and first cell start
        tmp.append("<tr><td><code>");

        // child name and end cell (with optional anchor)
        if (child.getRelativeFileName() != null)
        {
            tmp.append(String.format("<a href=\"%s\">%s</a>", child
                .getRelativeFileName(), child.getName()));
        }
        else
        {
            tmp.append(child.getName());
        }
        tmp.append("</code></td>");

        // append stats
        tmp.append(generateCell("failed", stats.getNumFailed(), stats
            .getPercentFailed()));
        tmp.append(generateCell("skipped", stats.getNumSkipped(), stats
            .getPercentSkipped()));
        tmp.append(generateCell("passed", stats.getNumPassed(), stats
            .getPercentPassed()));

        // end row
        tmp.append("</tr>");

        return tmp.toString();
    }

    /**
     * Returns the name of the child corresponding to the given result.
     * 
     * @param result
     *            the result to be used as a basis for the returned name
     * 
     * @return the name of the child corresponding to the given result
     */
    protected abstract String getChildName(
            ITestResult result);

    /**
     * Returns a new child instance based on the given result.
     * 
     * @param result
     *            the result to be used as a basis for the new result
     * 
     * @return a new child instance based on the given result
     */
    protected abstract C newChildInstance(
            ITestResult result);

    /**
     * Returns the header for the detail table.
     * 
     * @return the header for the detail table
     */
    protected abstract String getDetailTableHeader();

    /**
     * Generates a stat table cell.
     * 
     * @param type
     *            the type of cell to generate
     * 
     * @param num
     *            the number to place in the cell
     * 
     * @param pct
     *            the percentage to place in the cell
     * 
     * @return a stat table cell
     */
    private final String generateCell(
            final String type,
            final int num,
            final float pct)
    {
        return ((num == 0)
                ? "<td class=\"none\">0 (0%)</td>"
                : String.format("<td class=\"%s\">%d (%3.1f%%)</td>", type,
                    num, (pct * 100.0F)));
    }
}

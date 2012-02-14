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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.testng.ITestResult;

/**
 * Information about a test method.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
final class MethodInfo
        extends AbstractEntityInfo<InvocationInfo>
{
    /** The detail table header. */
    private static final String DETAIL_TABLE_HEADER = "<tr><th>Invocation</th>" //
            + "<th>Result</th>" //
            + "<th>Parameters</th>" //
            + "<th>Exception</th></tr>";

    /** Different parameter sets seen by this method. */
    private final List<Object[]> _instanceParameters = new ArrayList<Object[]>();

    /**
     * Creates new method information.
     * 
     * @param name
     *            the name of the method described by this information
     */
    MethodInfo(
            final String name)
    {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected final String getChildName(
            final ITestResult result)
    {
        int instanceNum;
        for (instanceNum = 0; instanceNum < _instanceParameters.size(); instanceNum++)
        {
            if (_instanceParameters.get(instanceNum).equals(
                result.getParameters()))
            {
                break;
            }
        }
        if (instanceNum == _instanceParameters.size())
        {
            _instanceParameters.add(result.getParameters());
        }
        return String.format("%s - invocation %03d", result.getName(),
            instanceNum);
    }

    /** {@inheritDoc} */
    @Override
    protected final InvocationInfo newChildInstance(
            final ITestResult result)
    {
        return new InvocationInfo(getChildName(result), result);
    }

    /** {@inheritDoc} */
    @Override
    protected final String getDetailTableHeader()
    {
        return DETAIL_TABLE_HEADER;
    }

    /** {@inheritDoc} */
    @Override
    protected final String getDetailTableRow(
            final InvocationInfo child)
    {
        return String.format("<tr>%s%s%s%s</tr>", //
            numberCell(child), resultCell(child), //
            paramCell(child), exceptionCell(child));
    }

    /**
     * Returns a cell containing the number of the given child.
     * 
     * @param child
     *            the child, the number of which should be returned
     * 
     * @return a cell containing the number of the given child
     */
    private final String numberCell(
            final InvocationInfo child)
    {
        return String.format("<td>%s</td>", child.getName().substring(
            child.getName().lastIndexOf(' ')));
    }

    /**
     * Returns a cell with the result of the given child.
     * 
     * @param child
     *            the child, the result of which should be returned
     * 
     * @return a cell with the result of the given child
     */
    private final String resultCell(
            final InvocationInfo child)
    {
        switch (child.getResult().getStatus())
        {
            case ITestResult.SUCCESS:
                return "<td class=\"passed\">passed</td>";

            case ITestResult.SKIP:
                return "<td class=\"skipped\">skipped</td>";

            case ITestResult.FAILURE:
            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
                return "<td class=\"failed\">failed</td>";

            default:
                return String.format("<td>unknown status %d</td>", child
                    .getResult().getStatus());

        }
    }

    /**
     * Returns a cell with the parameters of the given child.
     * 
     * @param child
     *            the child, the parameters of which should be returned
     * 
     * @return a cell with the parameters of the given child
     */
    private final String paramCell(
            final InvocationInfo child)
    {
        return (ArrayUtils.isEmpty(child.getResult().getParameters())
                ? "<td>&nbsp;</td>"
                : String.format("<td>%s</td>", //
                    new ToStringBuilder( //
                        child.getResult(), ToStringStyle.SIMPLE_STYLE) //
                        .append(child.getResult().getParameters()) //
                        .toString()));
    }

    /**
     * Returns a cell with the exception of the given child.
     * 
     * @param child
     *            the child, the exception of which should be returned
     * 
     * @return a cell with the exception of the given child
     */
    private final String exceptionCell(
            final InvocationInfo child)
    {
        final Throwable throwable = child.getResult().getThrowable();
        return ((throwable == null)
                ? "<td>&nbsp;</td>"
                : String.format("<td><code>%s</code><br />%s</td>", throwable
                    .getClass().getName(), throwable.getMessage()));
    }
}

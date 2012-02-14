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

package com.xtructure.xutil;

import org.testng.annotations.Test;

/**
 * Unit test for {@link XLogger}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.4
 */
@Test(groups = { "unit:xutil" })
public final class UTestXLogger
//        extends AbstractXTest
{
//    /** The logger for this class. */
//    private static final XLogger LOGGER = XLogger
//        .getInstance(UTestXLogger.class);
//
//    /** A simple layout. */
//    private static final SimpleLayout SIMPLE_LAYOUT = new SimpleLayout();
//
//    /** A string writer. */
//    private final StringWriter _writer = new StringWriter();
//
//    /** Creates a new unit test for {@link XLogger}. */
//    public UTestXLogger()
//    {
//        super(LOGGER);
//    }
//
//    /**
//     * Tests the factory method {@link XLogger#getInstance(String)}.
//     * 
//     * @throws Exception
//     *             if the test fails
//     */
//    public final void testFactoryGetInstanceString()
//        throws Exception
//    {
//        final String name = "testFactory_getInstance_String";
//
//        assertCondition("getInstance on non-null name", //
//            isNotNull(), //
//            XLogger.getInstance(name));
//
//        assertCondition("getInstance on identical names", //
//            isEqual(XLogger.getInstance(name)), //
//            XLogger.getInstance(name));
//
//        assertCondition("getInstance on null", //
//            doesThrow(NullArgumentException.class), //
//            new Worker()
//            {
//                @Override
//                public final void execute()
//                    throws Throwable
//                {
//                    XLogger.getInstance((String)null);
//                }
//            });
//    }
//
//    /**
//     * Tests the factory method {@link XLogger#getInstance(Class)}.
//     * 
//     * @throws Exception
//     *             if the test fails
//     */
//    public final void testFactoryGetInstanceClass()
//        throws Exception
//    {
//        final Class<?> type = UTestXLogger.class;
//
//        assertCondition("getInstance on non-null type", //
//            isNotNull(), //
//            XLogger.getInstance(type));
//
//        assertCondition("getInstance on identical types", //
//            isEqual(XLogger.getInstance(type)), //
//            XLogger.getInstance(type));
//
//        assertCondition("getInstance on null", //
//            doesThrow(NullArgumentException.class), //
//            new Worker()
//            {
//                @Override
//                public final void execute()
//                    throws Throwable
//                {
//                    XLogger.getInstance((Class<?>)null);
//                }
//            });
//    }
//
////    /**
////     * Tests the attribute methods for exclude patterns.
////     * 
////     * @see XLogger#getExcludePatterns()
////     * @see XLogger#addExcludePatterns(String[])
////     * @see XLogger#removeExcludePatterns(String[])
////     * @see XLogger#clearExcludePatterns()
////     * 
////     * @throws Exception
////     *             if the test fails
////     */
////    public final void testAttr_excludePatterns()
////        throws Exception
////    {
////        final XLogger parent = XLogger
////            .getInstance("testAttr_excludePatterns-parent");
////        final XLogger child = XLogger
////            .getInstance("testAttr_excludePatterns-parent.child");
////
////        assertCondition("initial patterns existence", //
////            isNotNull(), //
////            parent.getExcludePatterns());
////
////        assertCondition("initial patterns size", //
////            isTrue(), //
////            parent.getExcludePatterns().isEmpty());
////
////        parent.addExcludePatterns("one", "two", "three", null, "three");
////
////        assertCondition("patterns existence after add", //
////            isNotNull(), //
////            parent.getExcludePatterns());
////
////        assertCondition("patterns size after add", //
////            isEqual(3), //
////            parent.getExcludePatterns().size());
////
////        parent.addExcludePatterns((String[])null);
////
////        assertCondition("patterns existence after null array add", //
////            isNotNull(), //
////            parent.getExcludePatterns());
////
////        assertCondition("patterns size after null array add", //
////            isEqual(3), //
////            parent.getExcludePatterns().size());
////
////        parent.removeExcludePatterns("one", null);
////
////        assertCondition("patterns existence after remove", //
////            isNotNull(), //
////            parent.getExcludePatterns());
////
////        assertCondition("patterns size after remove", //
////            isEqual(2), //
////            parent.getExcludePatterns().size());
////
////        parent.removeExcludePatterns((String[])null);
////
////        assertCondition("patterns existence after null array remove", //
////            isNotNull(), //
////            parent.getExcludePatterns());
////
////        assertCondition("patterns size after null array remove", //
////            isEqual(2), //
////            parent.getExcludePatterns().size());
////
////        child.addExcludePatterns("four");
////
////        assertCondition("child effective patterns existence", //
////            isNotNull(), //
////            child.getEffectiveExcludePatterns());
////
////        assertCondition("child effective patterns size", //
////            isEqual(3), //
////            child.getEffectiveExcludePatterns().size());
////    }
//
////    /**
////     * Tests the attribute methods for filter.
////     * 
////     * @see XLogger#isFilterActive()
////     * @see XLogger#setFilterActive(boolean)
////     * @see XLogger#setFilterActive()
////     * @see XLogger#clearFilterActive()
////     * 
////     * @throws Exception
////     *             if the test fails
////     */
////    public final void testAttr_filterActive()
////        throws Exception
////    {
////        final XLogger logger = XLogger
////            .getInstance("testAttr_filterActive-parent");
////
////        assertCondition("initial state", //
////            isTrue(), //
////            logger.isFilterActive());
////
////        logger.setFilterActive(false);
////        assertCondition("state after explicit clear", //
////            isFalse(), //
////            logger.isFilterActive());
////
////        logger.setFilterActive(true);
////        assertCondition("state after explicit set", //
////            isTrue(), //
////            logger.isFilterActive());
////
////        logger.clearFilterActive();
////        assertCondition("state after implicit clear", //
////            isFalse(), //
////            logger.isFilterActive());
////
////        logger.setFilterActive();
////        assertCondition("state after implicit set", //
////            isTrue(), //
////            logger.isFilterActive());
////    }
//
//    /**
//     * Tests the method {@link XLogger#fatal(String, Object...)}.
//     * 
//     * @throws Exception
//     *             if the test fails
//     */
//    public final void testMethodFatalStringObjects()
//        throws Exception
//    {
//        final XLogger logger = XLogger
//            .getInstance("testMethod_fatal_String_Objects-parent");
//        startLoggingCapture();
//
//        // log with logger level fatal
//        logger.setLevel(Level.FATAL);
//
//        clearLoggingCapture();
//        logger.fatal("a message %s", "arg");
//        assertCondition("log with string arg with logger level fatal", //
//            isTrue(), //
//            loggingCaptureMatches("FATAL - a message arg"));
//
//        clearLoggingCapture();
//        logger.fatal("a message");
//        assertCondition("log with no args with logger level fatal", //
//            isTrue(), //
//            loggingCaptureMatches("FATAL - a message"));
//
//        clearLoggingCapture();
//        logger.fatal("a message %s");
//        assertCondition("log with missing arg with logger level fatal", //
//            isTrue(), //
//            loggingCaptureMatches("FATAL - a message %s"));
//
//        // log with logger level off
//        logger.setLevel(Level.OFF);
//
//        clearLoggingCapture();
//        logger.fatal("a message %s", "arg");
//        assertCondition("log with string arg with logger level off", //
//            isTrue(), //
//            getLoggingCapture().trim().isEmpty());
//
//        clearLoggingCapture();
//        logger.fatal("a message");
//        assertCondition("log with no args with logger level off", //
//            isTrue(), //
//            getLoggingCapture().trim().isEmpty());
//
//        clearLoggingCapture();
//        logger.fatal("a message %s");
//        assertCondition("log with missing arg with logger level fatal", //
//            isTrue(), //
//            getLoggingCapture().trim().isEmpty());
//
//        endLoggingCapture();
//    }
//
////    /**
////     * Tests the method
////     * {@link XLogger#fatal(Throwable, boolean, String, Object...)}.
////     * 
////     * @throws Exception
////     *             if the test fails
////     */
////    public final void testMethod_fatal_Throwable_boolean_String_Objects()
////        throws Exception
////    {
////        final String methodName = "testMethod_fatal_Throwable_boolean_String_Objects";
////        final XLogger logger = XLogger.getInstance(methodName);
////        final Throwable thr = new Throwable("throwable message");
////        startLoggingCapture();
////
////        // log with logger level fatal
////        logger.setLevel(Level.FATAL);
////
////        clearLoggingCapture();
////        logger.fatal(thr, true, "a message %s", "arg");
////        assertCondition("log trace with string arg; logger level fatal", //
////            isTrue(), //
////            loggingCaptureMatches("(?s:FATAL - a message arg: "
////                    + "java\\.lang\\.Throwable: throwable message"
////                    + ".*at com\\.xtructure\\.xutil\\.UTestXLogger\\."
////                    + methodName + ")"));
////
////        clearLoggingCapture();
////        logger.fatal(thr, false, "a message %s", "arg");
////        assertCondition("log no trace; logger level fatal - find message", //
////            isTrue(), //
////            loggingCaptureMatches("FATAL - a message arg: "
////                    + "java\\.lang\\.Throwable: throwable message"));
////
////        clearLoggingCapture();
////        logger.fatal(thr, false, "a message %s", "arg");
////        assertCondition("log no trace; logger level fatal - do not find trace", //
////            isFalse(), //
////            loggingCaptureMatches("(?s:FATAL - a message arg: "
////                    + "java\\.lang\\.Throwable: throwable message"
////                    + ".*at com\\.xtructure\\.xutil\\.UTestXLogger\\."
////                    + methodName + ")"));
////
////        // log with logger level fatal and filtered stack trace
////        logger.addExcludePatterns(".*com\\.xtructure.*");
////
////        clearLoggingCapture();
////        logger.fatal(thr, true, "a message %s", "arg");
////        assertCondition(
////            "log filtered trace; logger level fatal - find unfiltered frame", //
////            isTrue(), //
////            loggingCaptureMatches("(?s:FATAL - a message arg: "
////                    + "java\\.lang\\.Throwable: throwable message"
////                    + ".*at java\\.lang\\.reflect\\.Method\\.invoke)"));
////
////        clearLoggingCapture();
////        logger.fatal(thr, true, "a message %s", "arg");
////        assertCondition(
////            "log trace; logger level fatal - do not find filtered frame", //
////            isFalse(), //
////            loggingCaptureMatches("(?s:FATAL - a message arg: "
////                    + "java\\.lang\\.Throwable: throwable message"
////                    + ".*at com\\.xtructure\\.xutil\\.UTestXLogger\\."
////                    + methodName + ")"));
////
////        endLoggingCapture();
////    }
//
////    /**
////     * Tests the method {@link XLogger#fatal(Throwable, String, Object...)}.
////     * 
////     * @throws Exception
////     *             if the test fails
////     */
////    public final void testMethod_fatal_Throwable_String_Objects()
////        throws Exception
////    {
////        final String methodName = "testMethod_fatal_Throwable_String_Objects";
////        final XLogger logger = XLogger.getInstance(methodName);
////        final Throwable thr = new Throwable("throwable message");
////        startLoggingCapture();
////
////        // log with logger level fatal
////        logger.setLevel(Level.FATAL);
////
////        clearLoggingCapture();
////        logger.fatal(thr, "a message %s", "arg");
////        assertCondition("log trace with string arg; logger level fatal", //
////            isTrue(), //
////            loggingCaptureMatches("(?s:FATAL - a message arg: "
////                    + "java\\.lang\\.Throwable: throwable message"
////                    + ".*at com\\.xtructure\\.xutil\\.UTestXLogger\\."
////                    + methodName + ")"));
////
////        clearLoggingCapture();
////        logger.fatal(thr, "a message %s", "arg");
////        assertCondition("log no trace; logger level fatal - find message", //
////            isTrue(), //
////            loggingCaptureMatches("FATAL - a message arg: "
////                    + "java\\.lang\\.Throwable: throwable message"));
////
////        // log with logger level fatal and filtered stack trace
////        logger.addExcludePatterns(".*com\\.xtructure.*");
////
////        clearLoggingCapture();
////        logger.fatal(thr, "a message %s", "arg");
////        assertCondition(
////            "log filtered trace; logger level fatal - find unfiltered frame", //
////            isTrue(), //
////            loggingCaptureMatches("(?s:FATAL - a message arg: "
////                    + "java\\.lang\\.Throwable: throwable message"
////                    + ".*at java\\.lang\\.reflect\\.Method\\.invoke)"));
////
////        clearLoggingCapture();
////        logger.fatal(thr, "a message %s", "arg");
////        assertCondition(
////            "log trace; logger level fatal - do not find filtered frame", //
////            isFalse(), //
////            loggingCaptureMatches("(?s:FATAL - a message arg: "
////                    + "java\\.lang\\.Throwable: throwable message"
////                    + ".*at com\\.xtructure\\.xutil\\.UTestXLogger\\."
////                    + methodName + ")"));
////
////        endLoggingCapture();
////    }
//
////    /**
////     * Tests the method {@link XLogger#fatal(Throwable)}.
////     * 
////     * @throws Exception
////     *             if the test fails
////     */
////    public final void testMethod_fatal_Throwable()
////        throws Exception
////    {
////        final String methodName = "testMethod_fatal_Throwable";
////        final XLogger logger = XLogger.getInstance(methodName);
////        final Throwable thr = new Throwable("throwable message");
////        startLoggingCapture();
////
////        // log with logger level fatal
////        logger.setLevel(Level.FATAL);
////
////        clearLoggingCapture();
////        logger.fatal(thr);
////        assertCondition("log trace with string arg; logger level fatal", //
////            isTrue(), //
////            loggingCaptureMatches("(?s:FATAL - "
////                    + "java\\.lang\\.Throwable: throwable message"
////                    + ".*at com\\.xtructure\\.xutil\\.UTestXLogger\\."
////                    + methodName + ")"));
////
////        clearLoggingCapture();
////        logger.fatal(thr);
////        assertCondition("log no trace; logger level fatal - find message", //
////            isTrue(), //
////            loggingCaptureMatches("FATAL - "
////                    + "java\\.lang\\.Throwable: throwable message"));
////
////        // log with logger level fatal and filtered stack trace
////        logger.addExcludePatterns(".*com\\.xtructure.*");
////
////        clearLoggingCapture();
////        logger.fatal(thr);
////        assertCondition(
////            "log filtered trace; logger level fatal - find unfiltered frame", //
////            isTrue(), //
////            loggingCaptureMatches("(?s:FATAL - "
////                    + "java\\.lang\\.Throwable: throwable message"
////                    + ".*at java\\.lang\\.reflect\\.Method\\.invoke)"));
////
////        clearLoggingCapture();
////        logger.fatal(thr);
////        assertCondition(
////            "log trace; logger level fatal - do not find filtered frame", //
////            isFalse(), //
////            loggingCaptureMatches("(?s:FATAL - "
////                    + "java\\.lang\\.Throwable: throwable message"
////                    + ".*at com\\.xtructure\\.xutil\\.UTestXLogger\\."
////                    + methodName + ")"));
////
////        endLoggingCapture();
////    }
//
//    // /**
//    // * Tests the method
//    // * {@link XLogger#verify(Level, boolean, Class, String, Object[])}.
//    // *
//    // * @throws Exception
//    // * if the test fails
//    // */
//    // public final void testMethod_verify_boolean()
//    // throws Exception
//    // {
//    // final XLogger logger = XLogger.getInstance("testMethod_verify_boolean");
//    // final String messageRegex = "(?s:INFO - java\\.lang\\.Exception: "
//    // + "a message arg)";
//    // startLoggingCapture();
//    //
//    // // log with logger level info
//    // logger.setLevel(Level.INFO);
//    //
//    // clearLoggingCapture();
//    // assertCondition("expected failure class check", //
//    // doesThrow(Exception.class), //
//    // new Worker()
//    // {
//    // @Override
//    // public final void execute()
//    // throws Throwable
//    // {
//    // logger.verify(Level.INFO, false, Exception.class,
//    // "a message %s", "arg");
//    // }
//    // });
//    // assertCondition("expected failure log check", //
//    // isTrue(), //
//    // loggingCaptureMatches(messageRegex));
//    //
//    // clearLoggingCapture();
//    // logger.verify(Level.INFO, true, Exception.class, "a message %s", "arg");
//    // assertCondition("expected success log check", //
//    // isFalse(), //
//    // loggingCaptureMatches(messageRegex));
//    //
//    // endLoggingCapture();
//    // }
//
//    // /**
//    // * Tests the method
//    // * {@link XLogger#verify(Level, Worker, Class, String, Object[])}.
//    // *
//    // * @throws Exception
//    // * if the test fails
//    // */
//    // public final void testMethod_verify_Worker()
//    // throws Exception
//    // {
//    // final XLogger logger = XLogger.getInstance("testMethod_verify_Worker");
//    // final String messageRegex = "(?s:INFO - java\\.lang\\.Exception: "
//    // + "a message arg)";
//    // startLoggingCapture();
//    //
//    // // log with logger level info
//    // logger.setLevel(Level.INFO);
//    //
//    // clearLoggingCapture();
//    // assertCondition("expected failure class check", //
//    // doesThrow(Exception.class), //
//    // new Worker()
//    // {
//    // @Override
//    // public final void execute()
//    // throws Throwable
//    // {
//    // logger.verify(Level.INFO, new Worker()
//    // {
//    // @Override
//    // public void execute()
//    // throws Throwable
//    // {
//    // throw new RuntimeException();
//    // }
//    // }, Exception.class, "a message %s", "arg");
//    // }
//    // });
//    // assertCondition("expected failure log check", //
//    // isTrue(), //
//    // loggingCaptureMatches(messageRegex));
//    //
//    // clearLoggingCapture();
//    // logger.verify(Level.INFO, new Worker()
//    // {
//    // @Override
//    // public void execute()
//    // throws Throwable
//    // {
//    // // do nothing
//    // }
//    // }, Exception.class, "a message %s", "arg");
//    // assertCondition("expected success log check", //
//    // isFalse(), //
//    // loggingCaptureMatches(messageRegex));
//    //
//    // endLoggingCapture();
//    // }
//
////    /**
////     * Tests the method
////     * {@link XLogger#verifyArg(String, String, boolean, String, Object[])}.
////     * 
////     * @throws Exception
////     *             if the test fails
////     */
////    public final void testMethod_verifyArg_impliedLevel()
////        throws Exception
////    {
////        final XLogger logger = XLogger
////            .getInstance("testMethod_verifyArg_impliedLevel");
////        final String messageRegex = "(?s:ERROR - "
////                + "java\\.lang\\.IllegalArgumentException: "
////                + ".*argname.*bob.*: a message arg)";
////        startLoggingCapture();
////
////        // log with logger level error
////        logger.setLevel(Level.ERROR);
////
////        clearLoggingCapture();
////        assertCondition("expected failure class check", //
////            doesThrow(IllegalArgumentException.class), //
////            new Worker()
////            {
////                @Override
////                public final void execute()
////                    throws Throwable
////                {
////                    logger.verifyArg("argname", "bob", false, "a message %s",
////                        "arg");
////                }
////            });
////        System.err.println("'" + getLoggingCapture() + "'");
////        assertCondition("expected failure log check", //
////            isTrue(), //
////            loggingCaptureMatches(messageRegex));
////
////        clearLoggingCapture();
////        logger.verifyArg("argname", "bob", true, "a message %s", "arg");
////        System.err.println("'" + getLoggingCapture() + "'");
////        assertCondition("expected failure log check", //
////            isFalse(), //
////            loggingCaptureMatches(messageRegex));
////
////        endLoggingCapture();
////    }
//
//    // /**
//    // * Tests the method {@link XLogger#verifyArgNonNull(String, Object)}.
//    // *
//    // * @throws Exception
//    // * if the test fails
//    // */
//    // public final void testMethod_verifyArgNonNull_impliedLevel()
//    // throws Exception
//    // {
//    // final XLogger logger = XLogger
//    // .getInstance("testMethod_verifyArgNonNull_impliedLevel");
//    // final String messageRegex = "(?s:ERROR - "
//    // + "org\\.apache\\.commons\\.lang\\.NullArgumentException: "
//    // + "argname must not be null)";
//    // startLoggingCapture();
//    //
//    // // log with logger level error
//    // logger.setLevel(Level.ERROR);
//    //
//    // clearLoggingCapture();
//    // assertCondition("expected failure class check", //
//    // doesThrow(IllegalArgumentException.class), //
//    // new Worker()
//    // {
//    // @Override
//    // public final void execute()
//    // throws Throwable
//    // {
//    // logger.verifyArgNonNull("argname", null);
//    // }
//    // });
//    // System.err.println("'" + getLoggingCapture() + "'");
//    // assertCondition("expected failure log check", //
//    // isTrue(), //
//    // loggingCaptureMatches(messageRegex));
//    //
//    // clearLoggingCapture();
//    // logger.verifyArgNonNull("argname", "bob");
//    // System.err.println("'" + getLoggingCapture() + "'");
//    // assertCondition("expected failure log check", //
//    // isFalse(), //
//    // loggingCaptureMatches(messageRegex));
//    //
//    // endLoggingCapture();
//    // }
//
//    // /**
//    // * Tests the method {@link XLogger#verifyState(boolean, String,
//    // Object[])}.
//    // *
//    // * @throws Exception
//    // * if the test fails
//    // */
//    // public final void testMethod_verifyState_impliedLevel()
//    // throws Exception
//    // {
//    // final XLogger logger = XLogger
//    // .getInstance("testMethod_verifyState_impliedLevel");
//    // final String messageRegex = "(?s:ERROR - "
//    // + "java\\.lang\\.IllegalStateException: " + "a message arg)";
//    // startLoggingCapture();
//    //
//    // // log with logger level error
//    // logger.setLevel(Level.ERROR);
//    //
//    // clearLoggingCapture();
//    // assertCondition("expected failure class check", //
//    // doesThrow(IllegalStateException.class), //
//    // new Worker()
//    // {
//    // @Override
//    // public final void execute()
//    // throws Throwable
//    // {
//    // logger.verifyState(false, "a message %s", "arg");
//    // }
//    // });
//    // System.err.println("'" + getLoggingCapture() + "'");
//    // assertCondition("expected failure log check", //
//    // isTrue(), //
//    // loggingCaptureMatches(messageRegex));
//    //
//    // clearLoggingCapture();
//    // logger.verifyState(true, "a message %s", "arg");
//    // System.err.println("'" + getLoggingCapture() + "'");
//    // assertCondition("expected failure log check", //
//    // isFalse(), //
//    // loggingCaptureMatches(messageRegex));
//    //
//    // endLoggingCapture();
//    // }
//
//    /** Starts logging capture. */
//    private final void startLoggingCapture()
//    {
//        BasicConfigurator.resetConfiguration();
//        BasicConfigurator.configure(new WriterAppender(SIMPLE_LAYOUT, _writer));
//    }
//
//    /** Ends logging capture. */
//    private final void endLoggingCapture()
//    {
//        initLogging();
//    }
//
//    /**
//     * Returns the captured log.
//     * 
//     * @return the logging captured so far
//     */
//    private final String getLoggingCapture()
//    {
//        return _writer.getBuffer().toString();
//    }
//
//    /** Clears the captured log. */
//    private final void clearLoggingCapture()
//    {
//        _writer.getBuffer().delete(0, _writer.getBuffer().length());
//    }
//
//    /**
//     * Attempts to match the logging capture against the given regular
//     * expression.
//     * 
//     * @param regex
//     *            the expression to match
//     * 
//     * @return <code>true</code> if the match succeeds; <code>false</code>
//     *         otherwise
//     */
//    private final boolean loggingCaptureMatches(
//            final String regex)
//    {
//        final String logCapture = getLoggingCapture();
//        return Pattern.compile(regex).matcher(logCapture).find();
//    }
}

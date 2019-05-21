package edu.ictt.blockchain.config;



import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogFilterConfig extends AbstractMatcherFilter<ILoggingEvent>{

	private Class<?> exceptionClass;

    public LogFilterConfig() {
    }

    @Override
    public FilterReply decide(final ILoggingEvent event) {
    	final IThrowableProxy throwableProxy = event.getThrowableProxy();
        if (throwableProxy == null) {
            return FilterReply.NEUTRAL;
        }

        if (!(throwableProxy instanceof ThrowableProxy)) {
            return FilterReply.NEUTRAL;
        }

        final ThrowableProxy throwableProxyImpl = 
            (ThrowableProxy) throwableProxy;
        final Throwable throwable = throwableProxyImpl.getThrowable();
        if (exceptionClass.isInstance(throwable)) {
            return onMatch;
        }

        return FilterReply.NEUTRAL;
    }

    public void setExceptionClassName(final String exceptionClassName) {
        try {
            exceptionClass = Class.forName(exceptionClassName);
        } catch (final ClassNotFoundException e) {
            throw new IllegalArgumentException("Class is unavailable: "
                    + exceptionClassName, e);
        }
    }
}

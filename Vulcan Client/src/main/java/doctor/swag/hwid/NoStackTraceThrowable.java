package doctor.swag.hwid;

import me.ka1.vulcan.Vulcan;

public class NoStackTraceThrowable extends RuntimeException {

    public NoStackTraceThrowable(final String msg) {
        super(msg);
        this.setStackTrace(new StackTraceElement[0]);
    }

    @Override
    public String toString() {
        return "" + Vulcan.getVersion();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

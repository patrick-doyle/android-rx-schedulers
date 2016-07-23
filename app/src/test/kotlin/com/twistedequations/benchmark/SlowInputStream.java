package com.twistedequations.benchmark;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SlowInputStream extends FilterInputStream {

    /**
     * Wraps the input stream to emulate a slow device
     * @param in input stream
     */
    public SlowInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
        return in.read();
    }

    // Also handles read(byte[])
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
        return in.read(b, off, len);
    }

}
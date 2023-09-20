package com.simple.rpc.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

@Data
public class SimpleRpcFuture<T> {
    private Promise<T> promise;
    private long timeout;

    public SimpleRpcFuture(Promise<T> promise, long timeout) {
        this.promise = promise;
        this.timeout = timeout;
    }
}
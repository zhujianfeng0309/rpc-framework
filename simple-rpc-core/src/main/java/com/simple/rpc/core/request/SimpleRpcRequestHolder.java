package com.simple.rpc.core.request;

import com.simple.rpc.common.SimpleRpcResponse;
import com.simple.rpc.core.SimpleRpcFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleRpcRequestHolder {

    public final static AtomicLong REQUEST_ID_GEN = new AtomicLong(0);

    public static final Map<Long, SimpleRpcFuture<SimpleRpcResponse>> REQUEST_MAP = new ConcurrentHashMap<>();
}
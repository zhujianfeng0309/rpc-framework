package com.simple.rpc.common;


import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleRpcRequest implements Serializable {
    private String serviceVersion;
    private String appId;
    private String className;
    private String methodName;
    private Object[] params;
    private Class<?>[] parameterTypes;
    private String requestId;
}
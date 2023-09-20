package com.simple.rpc.common;

public class RpcServiceHelper {
    public static String buildServiceKey(String serviceName, String serviceVersion) {
        return String.join("#", serviceName, serviceVersion);
    }

    public static String buildServiceName(String appId, String serviceName) {
        return String.join("#", appId, serviceName);
    }
}
package com.simple.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceMeta {
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 版本号
     */
    private String serviceVersion;
    /**
     * 地址
     */
    private String serviceAddr;

    /**
     * 端口
     */
    private int servicePort;

    /**
     * 是否健康
     */
    private boolean healthy;

    /**
     * 扩展类型
     */
    private Map<String, String> metadata = new HashMap<>();

}
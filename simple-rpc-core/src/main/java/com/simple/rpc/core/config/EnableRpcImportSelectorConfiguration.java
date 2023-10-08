package com.simple.rpc.core.config;


import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

public class EnableRpcImportSelectorConfiguration implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
       /*
       Set<String> rpcProvideService = importingClassMetadata.getMetaAnnotationTypes("RpcProvideService");
        Set<String> rpcReference = importingClassMetadata.getMetaAnnotationTypes("RpcReference");
        */
        return new String[0];
    }
}
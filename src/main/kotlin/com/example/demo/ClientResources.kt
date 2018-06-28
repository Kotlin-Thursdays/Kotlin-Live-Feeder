package com.example.demo

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.boot.context.properties.NestedConfigurationProperty


class ClientResources {
    @NestedConfigurationProperty lateinit var client: AuthorizationCodeResourceDetails
    @NestedConfigurationProperty lateinit var resource: ResourceServerProperties
}
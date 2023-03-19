package com.br.authentication.config;

import com.br.common.amazon.AmazonSecretManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

/**
 * @author hungdt
 */
@Configuration
public class AmazonConfig {

    @Bean
    public AmazonSecretManager amazonSecretManager() {
        // todo change config here
        var client = SecretsManagerClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        return new AmazonSecretManager(client);
    }
}

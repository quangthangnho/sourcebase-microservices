package com.br.common.amazon;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

import java.nio.ByteBuffer;

/**
 * @author an.cantuong
 */
@Slf4j
public class AmazonSecretManager {

    private final SecretsManagerClient client;

    public AmazonSecretManager(SecretsManagerClient client) {
        this.client = client;
    }

    public String getStringValue(String secretName) {
        try {
            var valueRequest = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            var valueResponse = client.getSecretValue(valueRequest);
            return valueResponse.secretString();
        } catch (SecretsManagerException e) {
            log.error(e.awsErrorDetails().errorMessage(), e);
            return null;
        }
    }

    public ByteBuffer getValueAsByteBuffer(String secretName) {
        try {
            var valueRequest = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            var valueResponse = client.getSecretValue(valueRequest);
            return valueResponse.secretBinary().asByteBuffer();
        } catch (SecretsManagerException e) {
            log.error(e.awsErrorDetails().errorMessage(), e);
            return null;
        }
    }

    public double getDoubleValue(String secretName) {
        return getValueAsByteBuffer(secretName).asDoubleBuffer().get();
    }

    public int getIntValue(String secretName) {
        return getValueAsByteBuffer(secretName).asIntBuffer().get();
    }

    public long getLongValue(String secretName) {
        return getValueAsByteBuffer(secretName).asLongBuffer().get();
    }

    public float getFloatValue(String secretName) {
        return getValueAsByteBuffer(secretName).asFloatBuffer().get();
    }
}

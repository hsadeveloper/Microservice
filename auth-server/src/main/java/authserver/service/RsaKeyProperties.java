package authserver.service;


import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;


@ConfigurationProperties(prefix = "rsa")
public class RsaKeyProperties {

    private Resource publicKeyLocation;
    private Resource privateKeyLocation;
    private String secretKey;
    private long expirationTime;

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public Resource getPublicKeyLocation() {
        return publicKeyLocation;
    }

    public void setPublicKeyLocation(Resource publicKeyLocation) throws Exception {
        this.publicKeyLocation = publicKeyLocation;
        this.publicKey = PemUtils.readPublicKey(publicKeyLocation.getInputStream());
    }

    public Resource getPrivateKeyLocation() {
        return privateKeyLocation;
    }

    public void setPrivateKeyLocation(Resource privateKeyLocation) throws Exception {
        this.privateKeyLocation = privateKeyLocation;
        this.privateKey = PemUtils.readPrivateKey(privateKeyLocation.getInputStream());
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}

package com.commonlib.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class JwtGenerator {

    public static void main(String[] args) throws Exception {
        // Load private key from PEM file
    	String key = "-----BEGIN PRIVATE KEY-----\r\n"
    			+ "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCzOMKvuNn2o5QS\r\n"
    			+ "9BKs65Opn/4KZjW+UH9p7lAgUJ+SZPkRfAgzzPNOC6yC8ZlMdugErIPT6femxP/u\r\n"
    			+ "QVWp1yCw7NraoL+3E84LefXESG/vYpAPHKgrJP5Vbrgq6VHVnArcAjR6VLN58RmW\r\n"
    			+ "2tCMnJ3387jP5RooYXARMCNCxlU1t/tM0htFn+YEe1WV+70wqVids6YrLYfKTari\r\n"
    			+ "qyB+uT8J4zd+0GJBRkKlcDiXXoksZU71czuJcpOIoGVsOwnxideW5L6kXhdrbR/a\r\n"
    			+ "ZjEHKf0Z3jCGG5kJg3DCpiy6dwuEpnwM/Wgn5U0nU7THuMObg5rBFIg7Ffv/jnzk\r\n"
    			+ "dBSA1EGrAgMBAAECggEAV0tq3K+DjxQvECpNHGfkdScmVIdjXw8TeJPo+Ex85RAE\r\n"
    			+ "lyUcU6rcyaN7BA7MtgdmzLwAvAwGofbrl++/22mbFUJQ+VN5/iEJ5+IgONp1lxqh\r\n"
    			+ "g5LYQKeUsCUtVE1wHEUUrwPt7rXp85fheG6dTRgOA5N1beGwI4IOVXjme+Rh5bcm\r\n"
    			+ "SUR+6m76qlZk+f7ekNpBmudFhf3tCEx8p5G9lOL2jI3HGfhtknR+I43eEAANYQvK\r\n"
    			+ "r6ya+vbfEgK9ZHpZz1fDdTkfChiR1G+qXYCI/obvL4elbdgtZCTU5FFv3uPjEbYA\r\n"
    			+ "tk86LO5s+jzIbR11aMiWBPqd1Z7CNCGUcqsmS4GPjQKBgQDdq6rjRuBi9+75dCtJ\r\n"
    			+ "OubheR/V7jG8r8CVBY5CKB1ElBpvY1NvyPMgbuxrjInuz+NLWpRSRILHzaHPw8Tv\r\n"
    			+ "AA0QheAAqIr58b30t/IDc80743C08w+BTjWR4AYSDfz5AMKiY3rRLLs7nhSG5GDg\r\n"
    			+ "Nezt+cU9YcBhgUKH/oVLHOsDFwKBgQDO+itX5+zJQ0INFDvzwn3E659nBqihERmK\r\n"
    			+ "1pXpiGMVU2vgY5Kam3+eNnd4Qmbh/gbXkbfMSyxWB9k4wqrts+lp5/L6/uEZGLbj\r\n"
    			+ "lvjyoGQPF4mIJ0EDPBxBs9T9CM8ta6A9snHL9Cuc7sow4lygsPYljedhA3DhO1SF\r\n"
    			+ "YOz6gWmijQKBgFUEFOtL8J0kd9vmaP+R/qC1IreaviqewV6mHdRTrD30C1tzHbn+\r\n"
    			+ "uPhUcoohAXm883CgEcini/zzab7UV4BOZbRTlADMo1iPFYp4zfHzBOCWL1DG88hk\r\n"
    			+ "5XjIEav0/3PEjeFYA++bl0ebLkY56smzBOaUZ8hN6Em9GHYt8JNJSP0LAoGBAJFK\r\n"
    			+ "seRqbSC3MwWlX5R0wf7a/DHBTdyK+WgpzGB8GjndLov8uewIHiS8zU7BJiGcRcdL\r\n"
    			+ "hj9FweC+b7DVjlGAiuiKJ1okU3VfkPrgfu8qwjKJ7kSCcbPInXlNuMTLS0vyEjFd\r\n"
    			+ "M17dj0QBqDN6o1U2BARGwdarKLEjCej7Kj+LWtD9AoGBALfm0j6+k3WkJPlR9da2\r\n"
    			+ "msMo3zX2wf+m8Rv9t5cjUfMWMXgM/K9v4SedlZJFezXtEKFHOEXFHBiybj/G/Ugt\r\n"
    			+ "45PjwrY+HqhNeOssMgtOmLk+D/zB08doHP67uaLOrbonJ2vzz6PCysMI6bQPyW76\r\n"
    			+ "pgG8ByC6ni2kBLF5RIZHlTHp\r\n"
    			+ "-----END PRIVATE KEY-----";
        String privateKeyPem = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        // Generate JWT
        String jwt = Jwts.builder()
                .setSubject("anish1")                         // username
                .claim("roles", List.of("ADMIN"))             // roles claim
                //.setIssuer("https://sso.example.com")       // issuer 
                .setIssuer("self")  //self
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour expiration
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();

        System.out.println("Generated JWT:\n" + jwt);
    }
}
package ma.enset.security;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class CertificateDocumentSigner {

    public static void main(String[] args) throws Exception {

        Path documentPath = Path.of("documents/document.txt");
        Path signaturePath = Path.of("documents/document.sig");

        // Lire le document
        String data = Files.readString(
                documentPath,
                StandardCharsets.UTF_8
        );

        // Charger le keystore PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        try (FileInputStream fis =
                     new FileInputStream("secure-app.p12")) {

            keyStore.load(
                    fis,
                    "changeit".toCharArray()
            );
        }

        // Récupérer la clé privée
        PrivateKey privateKey =
                (PrivateKey) keyStore.getKey(
                        "secureapp",
                        "changeit".toCharArray()
                );

        // Signer avec SHA256withRSA
        Signature signature =
                Signature.getInstance("SHA256withRSA");

        signature.initSign(privateKey);

        signature.update(
                data.getBytes(StandardCharsets.UTF_8)
        );

        byte[] signatureBytes = signature.sign();

        // Encoder la signature en Base64
        String signatureBase64 =
                Base64.getEncoder().encodeToString(signatureBytes);

        System.out.println("===== DOCUMENT =====");
        System.out.println(data);

        System.out.println("\n===== SIGNATURE =====");
        System.out.println(signatureBase64);

        // Enregistrer la signature
        Files.writeString(
                signaturePath,
                signatureBase64,
                StandardCharsets.UTF_8
        );

        System.out.println(
                "\nSignature enregistrée dans : "
                        + signaturePath
        );
    }
}
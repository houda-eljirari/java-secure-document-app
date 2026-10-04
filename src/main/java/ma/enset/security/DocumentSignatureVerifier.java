package ma.enset.security;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DocumentSignatureVerifier {

    public static void main(String[] args) throws Exception {

        // Chemin du document
        Path documentPath =
                Path.of("documents/document.txt");

        // Chemin de la signature
        Path signaturePath =
                Path.of("documents/document.sig");

        // Chemin de la clé publique
        Path publicKeyPath =
                Path.of("documents/public.key");

        // Lire le document
        String data =
                Files.readString(
                        documentPath,
                        StandardCharsets.UTF_8
                );

        // Lire la signature
        String signatureBase64 =
                Files.readString(
                        signaturePath,
                        StandardCharsets.UTF_8
                ).trim();

        // Lire la clé publique
        String publicKeyBase64 =
                Files.readString(
                        publicKeyPath,
                        StandardCharsets.UTF_8
                ).trim();

        // Décoder la clé publique
        byte[] publicKeyBytes =
                Base64.getDecoder()
                        .decode(publicKeyBase64);

        // Reconstruire la clé publique
        X509EncodedKeySpec keySpec =
                new X509EncodedKeySpec(publicKeyBytes);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        PublicKey publicKey =
                keyFactory.generatePublic(keySpec);

        // Créer le vérificateur RSA
        Signature verifySign =
                Signature.getInstance("SHA256withRSA");

        // Initialiser avec la clé publique
        verifySign.initVerify(publicKey);

        // Donner le document au vérificateur
        verifySign.update(
                data.getBytes(StandardCharsets.UTF_8)
        );

        // Décoder la signature
        byte[] signatureBytes =
                Base64.getDecoder()
                        .decode(signatureBase64);

        // Vérifier
        boolean result =
                verifySign.verify(signatureBytes);

        // Afficher le résultat
        if (result) {
            System.out.println("SIGNATUREVALIDE");
        } else {
            System.out.println("SIGNATUREINVALIDE");
        }
    }
}
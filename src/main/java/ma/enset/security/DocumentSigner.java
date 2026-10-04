package ma.enset.security;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class DocumentSigner {

    public static void main(String[] args) throws Exception {

        // Fichier du document
        Path documentPath =
                Path.of("documents/document.txt");

        // Fichier contenant la clé privée
        Path privateKeyPath =
                Path.of("documents/private.key");

        // Fichier où enregistrer la signature
        Path signaturePath =
                Path.of("documents/document.sig");

        // Lire le document
        String data =
                Files.readString(
                        documentPath,
                        StandardCharsets.UTF_8
                );

        // Lire la clé privée en Base64
        String privateKeyBase64 =
                Files.readString(
                        privateKeyPath,
                        StandardCharsets.UTF_8
                ).trim();

        // Décoder Base64
        byte[] privateKeyBytes =
                Base64.getDecoder()
                        .decode(privateKeyBase64);

        // Reconstruire la clé privée
        PKCS8EncodedKeySpec keySpec =
                new PKCS8EncodedKeySpec(privateKeyBytes);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        PrivateKey privateKey =
                keyFactory.generatePrivate(keySpec);

        // Créer la signature RSA
        Signature signature =
                Signature.getInstance("SHA256withRSA");

        // Initialiser avec la clé privée
        signature.initSign(privateKey);

        // Donner le document à signer
        signature.update(
                data.getBytes(StandardCharsets.UTF_8)
        );

        // Générer la signature
        byte[] dataSignBytes =
                signature.sign();

        // Convertir la signature en Base64
        String dataSign =
                Base64.getEncoder()
                        .encodeToString(dataSignBytes);

        // Afficher
        System.out.println("===== DOCUMENT =====");
        System.out.println(data);

        System.out.println("\n===== SIGNATURE RSA =====");
        System.out.println(dataSign);

        // Enregistrer la signature
        Files.writeString(
                signaturePath,
                dataSign,
                StandardCharsets.UTF_8
        );

        System.out.println(
                "\nSignature enregistrée dans : "
                        + signaturePath
        );
    }
}
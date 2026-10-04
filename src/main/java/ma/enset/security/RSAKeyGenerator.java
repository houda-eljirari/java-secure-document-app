package ma.enset.security;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RSAKeyGenerator {

    public static void main(String[] args) throws Exception {

        // Création du générateur RSA
        KeyPairGenerator pairGenerator =
                KeyPairGenerator.getInstance("RSA");

        // Le TP demande une clé de 2048 bits
        pairGenerator.initialize(2048);

        // Génération de la paire de clés
        KeyPair keyPair =
                pairGenerator.generateKeyPair();

        // Récupération de la clé privée
        byte[] privateKeyBytes =
                keyPair.getPrivate().getEncoded();

        // Récupération de la clé publique
        byte[] publicKeyBytes =
                keyPair.getPublic().getEncoded();

        // Conversion en Base64
        String privateKey =
                Base64.getEncoder()
                        .encodeToString(privateKeyBytes);

        String publicKey =
                Base64.getEncoder()
                        .encodeToString(publicKeyBytes);

        // Enregistrement de la clé privée
        Files.writeString(
                Path.of("documents/private.key"),
                privateKey,
                StandardCharsets.UTF_8
        );

        // Enregistrement de la clé publique
        Files.writeString(
                Path.of("documents/public.key"),
                publicKey,
                StandardCharsets.UTF_8
        );

        System.out.println("=== RSA ===");
        System.out.println("Paire de clés RSA générée.");
        System.out.println("Taille : 2048 bits");

        System.out.println("\nClé privée enregistrée dans :");
        System.out.println("documents/private.key");

        System.out.println("\nClé publique enregistrée dans :");
        System.out.println("documents/public.key");
    }
}
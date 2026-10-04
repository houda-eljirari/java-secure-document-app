package ma.enset.security;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;

public class CertificateSignatureVerifier {

    public static void main(String[] args) throws Exception {

        Path documentPath =
                Path.of("documents/document.txt");

        Path signaturePath =
                Path.of("documents/document.sig");

        // 1. Lire le document
        String data = Files.readString(
                documentPath,
                StandardCharsets.UTF_8
        );

        // 2. Lire la signature
        String signatureBase64 = Files.readString(
                signaturePath,
                StandardCharsets.UTF_8
        ).trim();

        byte[] signatureBytes =
                Base64.getDecoder().decode(signatureBase64);

        // 3. Lire le certificat X.509
        CertificateFactory factory =
                CertificateFactory.getInstance("X.509");

        Certificate certificate;

        try (InputStream inputStream =
                     new FileInputStream("certificate.cer")) {

            certificate =
                    factory.generateCertificate(inputStream);
        }

        // 4. Récupérer la clé publique du certificat
        PublicKey publicKey =
                certificate.getPublicKey();

        System.out.println("===== CERTIFICAT =====");
        System.out.println(
                "Type : " + certificate.getType()
        );

        System.out.println(
                "Algorithme de la clé : "
                        + publicKey.getAlgorithm()
        );

        System.out.println(
                "Format de la clé : "
                        + publicKey.getFormat()
        );

        // 5. Vérifier la signature
        Signature verifySignature =
                Signature.getInstance("SHA256withRSA");

        verifySignature.initVerify(publicKey);

        verifySignature.update(
                data.getBytes(StandardCharsets.UTF_8)
        );

        boolean result =
                verifySignature.verify(signatureBytes);

        System.out.println("\n===== VERIFICATION =====");

        if (result) {
            System.out.println("SIGNATURE VALIDE");
            System.out.println(
                    "Le document est authentique et n'a pas été modifié."
            );
        } else {
            System.out.println("SIGNATURE INVALIDE");
            System.out.println(
                    "Le document a été modifié ou la signature ne correspond pas."
            );
        }
    }
}
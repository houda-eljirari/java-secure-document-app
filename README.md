# SecureDocumentApp 🔐

Application Java dédiée à la sécurisation et à l'authentification de documents numériques.

## Présentation

Ce projet a été réalisé dans le cadre du module **Sécurité des systèmes distribués** du Master SDIA à l'ENSET Mohammedia.

L'objectif est de mettre en œuvre plusieurs mécanismes cryptographiques permettant de protéger l'intégrité et l'authenticité d'un document numérique.

## Objectifs

Le projet couvre :

- HMAC avec SHA-256
- Génération de clés RSA 2048 bits
- Signature numérique avec RSA
- Vérification de signatures
- Simulation d'attaques par modification du document
- Certificats numériques X.509
- Utilisation de `keytool`
- Keystore PKCS#12
- Extraction d'une clé publique depuis un certificat
- Vérification d'une signature à partir d'un certificat X.509

## Technologies utilisées

- Java 17
- Maven
- IntelliJ IDEA
- Java Cryptography Architecture (JCA)
- HMAC-SHA256
- RSA 2048
- SHA256withRSA
- X.509
- PKCS#12
- keytool

## Structure du projet

```text
SecureDocumentApp/
│
├── src/
│   └── main/
│       └── java/
│           └── ma/
│               └── enset/
│                   └── security/
│                       ├── DocumentHMacGenerator.java
│                       ├── DocumentHMacVerifier.java
│                       ├── RSAKeyGenerator.java
│                       ├── DocumentSigner.java
│                       ├── DocumentSignatureVerifier.java
│                       ├── CertificateReader.java
│                       ├── CertificateDocumentSigner.java
│                       └── CertificateSignatureVerifier.java
│
├── documents/
│   └── document.txt
│
├── screenshots/
│
├── .gitignore
├── pom.xml
└── README.md
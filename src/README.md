# 🛒 E-Commerce Spring Boot Projekt

Ein webbasiertes E-Commerce-System mit Benutzerverwaltung, Produktdarstellung, Warenkorb, Bestellfunktion und optionalem Admin-Panel. Technisch umgesetzt mit Spring Boot, JWT-Sicherheit und einem modularen Domain-Design.

---

## 🎯 Ziel des Projekts

Dieses Projekt demonstriert den Aufbau eines modularen, wartbaren E-Commerce-Backends mit Fokus auf:

- **Kundenbereich**: Produkte anzeigen, Warenkorb, Bestellung
- **Adminbereich**: Produkt- und Benutzerverwaltung
- **Rollenbasiertem Zugriff**: Kunde, Admin, ggf. Lagerist


## 🧱 Projektstruktur

```plaintext
com.shop.ecommerce
├── admin/                  // Admin-spezifische Features
├── cart/
├── config/                 // Spring-Konfigurationen (z. B. CORS, Security)
├── security/               // Authentifizierung mit JWT, Filter, SecurityConfig
├── user/                   // Benutzerverwaltung, Authentifizierung
├── product/                // Produkte, Kategorien
├── order/                 // Bestellung & Warenkorb
│   ├── controller/         // Endpunkte wie /api/orders
│   ├── dto/                // OrderRequestDTO, OrderResponseDTO
│   ├── entity/             // Order.java, OrderItem.java
│   ├── repository/         // OrderRepository
│   ├── service/            // OrderService + Impl
│   └── exception/          // Eigene Fehlerklassen


📦 Module im Überblick
1. 🔐 Authentifizierung & Benutzerverwaltung
Registrierung & Login mit JWT

Rollen: USER, ADMIN

Eigene Profildaten bearbeiten

2. 📦 Produktverwaltung
CRUD-Funktionalität für Produkte (Admin)

Kategorien, Lagerbestand, Bilder

3. 🛍️ Warenkorb & Bestellung
Produkt in Warenkorb legen

Menge ändern, löschen, Warenkorb leeren

Bestellung aus Warenkorb aufgeben

4. 🧑‍💼 Adminbereich 
Alle Nutzer einsehen und löschen

Produkte und Bestellungen verwalten

5. 🖥️ Frontend 
React (getestet mit Vite + Axios)

Login, Produktliste, Warenkorb, Checkout

🗃️ Datenmodell (vereinfachte Übersicht)
Entity	Beziehungen
User	1:N Order, Rollen
Product	N:1 Category, Lagerbestand
Order	N:1 User, N:M OrderItem
OrderItem	1 Product, Menge, Preis pro Artikel
Category	1:N Product

🛠 Verwendete Technologien
Spring Boot 3.x

Spring Security (JWT)

Spring Data JPA

Lombok

ModelMapper / MapStruct

PostgreSQL / MySQL / H2

React (Frontend, optional)

Swagger / OpenAPI (optional)

🔐 Authentifizierung (JWT)
Endpunkt	Methode	Auth erforderlich	Beschreibung
/api/auth/login	POST	❌ Nein	Login, gibt JWT zurück
/api/auth/register	POST	❌ Nein	Registrierung neuer Nutzer
/api/users/me	GET	✅ Ja	Eigene Daten anzeigen
/api/users/me	PUT	✅ Ja	Eigene Daten aktualisieren

🛒 Warenkorb & Bestellung
Endpunkt	Methode	Beschreibung
/api/cart	GET	Aktuellen Warenkorb anzeigen
/api/cart/items	POST	Produkt zum Warenkorb hinzufügen
/api/cart/items/{itemId}	PUT	Menge eines Artikels ändern
/api/cart/items/{itemId}	DELETE	Artikel aus dem Warenkorb entfernen
/api/cart	DELETE	Warenkorb vollständig leeren
/api/orders	POST	Bestellung aufgeben
/api/orders	GET	Alle eigenen Bestellungen anzeigen
/api/orders/{orderId}	GET	Details einer Bestellung anzeigen

👨‍💼 Admin-Endpunkte
Endpunkt	Methode	Beschreibung
/api/admin/users	GET	Liste aller Nutzer
/api/admin/users/{userId}	DELETE	Nutzer löschen
/api/products	POST	Neues Produkt anlegen
/api/products/{id}	PUT/DELETE	Produkt bearbeiten/löschen

🛡️ Diese Endpunkte sind nur mit ROLE_ADMIN zugänglich.

🔍 Teststrategie
🔐 Authentifizierung
1. GET /api/cart ohne Token → 401 Unauthorized

2. Mit gültigem JWT-Token → 200 OK

POST /api/auth/register
Content-Type: application/json

{
  "email": "sahar1@mis.com",
  "password": "test123",
  "firstName": "Sahar",
  "lastName": "Test"
}


📘 Beispielablauf: Bestellung aufgeben
Produkt anzeigen: GET /api/products

Produkt in Warenkorb legen: POST /api/cart/items

Warenkorb einsehen: GET /api/cart

Bestellung aufgeben: POST /api/orders

🚀 Projekt starten
Backend läuft z. B. auf http://localhost:8081, Frontend auf http://localhost:5173

✅ Vorteile der Architektur
Modular & Domain-orientiert (DDD-ähnlich)

Skalierbar: Neue Module können leicht ergänzt werden

Testbar: Klare Trennung von Controller, Service, DTO, Entity

Sicher: JWT + Rollenbasierte Autorisierung

Frontend-unabhängig: Kann mit React, Angular oder mobilen Apps verwendet werden

📌 Nächste Schritte (optional)
Produktsuche + Filter

Echte Zahlungsintegration (z. B. Stripe)

Email-Versand nach Bestellung

Admin-Dashboard mit Statistiken

Swagger/OpenAPI einbauen


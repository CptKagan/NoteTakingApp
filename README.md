# NoteTakingApp

Spring Boot tabanlı not alma uygulaması. JWT tabanlı kullanıcı kimlik doğrulaması ve rol bazlı erişim kontrolü ile not ekleme, güncelleme, silme ve listeleme işlemlerini sağlamaktadır.

## Özellikler

- **Kullanıcı Kimlik Doğrulama:** JWT kullanarak kullanıcı giriş işlemleri.
- **Not Yönetimi:** Kullanıcılar not oluşturabilir, düzenleyebilir, görüntüleyebilir ve silebilir.
- **Rol Bazlı Erişim:** Normal kullanıcılar kendi notlarına erişirken, admin tüm notları görebilir.
- **H2 Veritabanı:** Geliştirme ortamı için H2 veritabanı kullanımı.
- **RESTful API:** Not işlemleri ve kimlik doğrulama için API endpointleri.

## Teknolojiler

- **Java** ve **Spring Boot**
- **Spring Security** ile kimlik doğrulama / izin kontrolleri
- **JWT** ile token bazlı oturum yöntemi
- **H2 Database** (geliştirme / test)

## Kurulum

### Gereksinimler

- Java 11 veya üstü
- Maven

### Adımlar

1. **Depoyu Klonlayın:**
```bash
   git clone https://github.com/kullaniciadi/NoteTakingApp.git
   cd NoteTakingApp
```

2. **Projeyi Derleyin**
```bash
mvn clean install
```

3. **Uygulamayı Başlatın**
```bash
mvn spring-boot:run
```

4. Uygulama varsayılan olarak 8080 portunda çalışacaktır. Geliştirme sırasında H2 konsoluna http://localhost:8080/db-console üzerinden erişebilirsiniz.
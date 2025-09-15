# IPTVPlayer2 Mimari Dokümantasyonu

## Genel Bakış

IPTVPlayer2, Android platformu için geliştirilmiş modern bir IPTV oynatıcı uygulamasıdır. Uygulama, Jetpack Compose UI framework'ü kullanılarak geliştirilmiş ve MVVM (Model-View-ViewModel) mimari desenini takip etmektedir.

## Mimari Yapı

Uygulama aşağıdaki ana bileşenlerden oluşmaktadır:

### 1. Sunum Katmanı (Presentation Layer)

#### 1.1. UI Bileşenleri
- **Ekranlar (Screens)**: Kullanıcı arayüzünün ana bileşenleridir.
  - `MainScreen`: Ana ekran
  - `ChannelListScreen`: Kanal listesi ekranı
  - `PlayerScreen`: Video oynatıcı ekranı
  - `EpgScreen`: Elektronik Program Rehberi ekranı
  - `ProfileSelectionScreen`: Profil seçim ekranı
  - `ProviderLoginScreen`: Sağlayıcı giriş ekranı
  - `UrlInputScreen`: URL giriş ekranı

#### 1.2. Navigasyon
- **AppNavigation**: Uygulama içi ekranlar arası geçişleri yönetir.

#### 1.3. Tema
- **IPTVPlayer2Theme**: Uygulama genelinde tutarlı bir görünüm sağlar.
  - `Color.kt`: Renk tanımlamaları
  - `Theme.kt`: Tema yapılandırması
  - `Type.kt`: Tipografi tanımlamaları

### 2. İş Mantığı Katmanı (Business Logic Layer)

#### 2.1. ViewModel'ler
- **MainViewModel**: Ana uygulama mantığını ve durumunu yönetir.
- **ProfileViewModel**: Profil yönetimi ile ilgili iş mantığını içerir.

### 3. Veri Katmanı (Data Layer)

#### 3.1. Model
- **Channel**: Kanal bilgilerini temsil eder.
- **EpgProgram**: Program bilgilerini temsil eder.
- **Profile**: Kullanıcı profillerini temsil eder.

#### 3.2. Veri İşleme
- **M3uParser**: M3U formatındaki IPTV listelerini işler.
- **EpgParser**: EPG (Elektronik Program Rehberi) verilerini işler.

#### 3.3. Ağ İşlemleri
- **M3uApi**: M3U dosyalarını indirmek için API çağrıları.
- **RetrofitInstance**: Ağ istekleri için Retrofit yapılandırması.

## Veri Akışı

1. Kullanıcı bir eylem gerçekleştirir (örn. URL giriş ekranında bir M3U URL'si girer).
2. ViewModel, bu eylemi alır ve gerekli veri işlemlerini başlatır.
3. Veri katmanı (M3uApi, M3uParser vb.) verileri alır ve işler.
4. İşlenen veriler ViewModel'e döner.
5. ViewModel, UI durumunu günceller.
6. Compose UI bileşenleri, güncellenmiş durumu gözlemler ve kullanıcı arayüzünü yeniden oluşturur.

## Teknoloji Yığını

- **Dil**: Kotlin
- **UI Framework**: Jetpack Compose
- **Mimari Desen**: MVVM (Model-View-ViewModel)
- **Ağ İşlemleri**: Retrofit
- **Asenkron İşlemler**: Kotlin Coroutines
- **Bağımlılık Enjeksiyonu**: Hilt (varsayılan)
- **Navigasyon**: Jetpack Navigation Compose

## Gelecek Geliştirmeler

- Yerel veritabanı entegrasyonu (Room)
- Çoklu dil desteği
- Tema özelleştirme
- Gelişmiş EPG özellikleri
- Uzaktan kumanda desteği
- Chromecast entegrasyonu

## Diyagram

```
+----------------------------------+
|            UI Layer               |
|  +----------------------------+   |
|  |        Screens             |   |
|  |  - MainScreen              |   |
|  |  - ChannelListScreen       |   |
|  |  - PlayerScreen            |   |
|  |  - EpgScreen               |   |
|  |  - ProfileSelectionScreen  |   |
|  |  - ProviderLoginScreen     |   |
|  |  - UrlInputScreen          |   |
|  +----------------------------+   |
+----------------------------------+
               |
               v
+----------------------------------+
|         ViewModel Layer          |
|  +----------------------------+   |
|  |  - MainViewModel           |   |
|  |  - ProfileViewModel        |   |
|  +----------------------------+   |
+----------------------------------+
               |
               v
+----------------------------------+
|           Data Layer             |
|  +----------------------------+   |
|  |         Models             |   |
|  |  - Channel                 |   |
|  |  - EpgProgram              |   |
|  |  - Profile                 |   |
|  +----------------------------+   |
|                                  |
|  +----------------------------+   |
|  |       Data Processing      |   |
|  |  - M3uParser               |   |
|  |  - EpgParser               |   |
|  +----------------------------+   |
|                                  |
|  +----------------------------+   |
|  |       Network              |   |
|  |  - M3uApi                  |   |
|  |  - RetrofitInstance        |   |
|  +----------------------------+   |
+----------------------------------+
```
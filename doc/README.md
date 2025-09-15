# IPTVPlayer2

## Proje Hakkında

IPTVPlayer2, Android platformu için geliştirilmiş modern bir IPTV oynatıcı uygulamasıdır. Bu uygulama, kullanıcıların M3U formatındaki IPTV listelerini yükleyerek canlı TV kanallarını izlemelerini sağlar. Ayrıca EPG (Elektronik Program Rehberi) desteği ile program bilgilerini görüntüleme imkanı sunar.

## Özellikler

- M3U formatındaki IPTV listelerini yükleme ve işleme
- Canlı TV kanallarını izleme
- EPG (Elektronik Program Rehberi) desteği
- Çoklu profil yönetimi
- Modern ve kullanıcı dostu arayüz (Jetpack Compose)
- Kanal listesi filtreleme ve arama
- Tam ekran video oynatma

## Kurulum

### Gereksinimler

- Android Studio Arctic Fox veya daha yeni bir sürüm
- JDK 11 veya daha yeni bir sürüm
- Android SDK 31 (Android 12) veya daha yeni bir sürüm
- Kotlin 1.6.0 veya daha yeni bir sürüm

### Adımlar

1. Projeyi klonlayın:
   ```
   git clone https://github.com/xl9t/IPTVPlayer2.git
   ```

2. Android Studio'da projeyi açın.

3. Gradle senkronizasyonunu tamamlayın.

4. Uygulamayı bir emülatörde veya fiziksel cihazda çalıştırın.

## Kullanım

1. Uygulamayı başlatın.

2. İlk kullanımda, bir profil oluşturun.

3. IPTV listesi eklemek için:
   - URL ile eklemek için: "URL Ekle" seçeneğini kullanın ve M3U listesinin URL'sini girin.
   - Yerel dosyadan eklemek için: "Dosya Seç" seçeneğini kullanın ve cihazınızdaki M3U dosyasını seçin.

4. Kanal listesi yüklendikten sonra, izlemek istediğiniz kanalı seçin.

5. EPG bilgilerini görüntülemek için kanal izlerken "EPG" düğmesine tıklayın.

## Proje Yapısı

Proje, MVVM (Model-View-ViewModel) mimari desenini takip etmektedir. Daha detaylı bilgi için [mimari.md](mimari.md) dosyasını inceleyebilirsiniz.

## Katkıda Bulunma

1. Bu repository'yi fork edin.
2. Yeni bir branch oluşturun (`git checkout -b feature/amazing-feature`).
3. Değişikliklerinizi commit edin (`git commit -m 'Add some amazing feature'`).
4. Branch'inizi push edin (`git push origin feature/amazing-feature`).
5. Pull Request oluşturun.

## Lisans

Bu proje [MIT Lisansı](LICENSE) altında lisanslanmıştır.

## İletişim

Proje Sahibi: [xl9t](https://github.com/xl9t)

---

**Not:** Bu README dosyası, projenin genel yapısını ve kullanımını açıklamak için oluşturulmuştur. Projenin geliştirilmesi sırasında güncellenebilir.
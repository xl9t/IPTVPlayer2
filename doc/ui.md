# IPTV Oynatıcı Kullanıcı Arayüzü Gereksinimleri

## 1. Zorunlu Özellikler

### 1.1. Canlı Yayın Özellikleri
- **Çoklu Çözünürlük Desteği** (SD, HD, Full HD, 4K)
- **Akıcı Kanallar Arası Geçiş** (Zapping)
- **Hızlı Kanal Değiştirme** (Sayısal tuş takımı ile)
- **Ses Seviyesi Kontrolü**
- **Alt Yazı Desteği** (Dahili ve harici)
- **Ses Kanalı Seçimi** (Mono/Stereo/5.1)
- **Görüntü Oranı Ayarları** (16:9, 4:3, Orjinal, Uygun)

### 1.2. EPG (Elektronik Program Rehberi)
- **7 Günlük Program Bilgisi**
- **Program Hatırlatıcıları**
- **Program Kategorilerine Göre Filtreleme**
- **Favori Kanalları İşaretleme**
- **Program Arama** (İsim, tür, saat aralığına göre)

## 2. Kullanıcı Deneyimi

### 2.1. Ana Ekran
- **Son İzlenenler**
- **Sık Kullanılan Kanallar**
- **Kategori Bazlı Kanal Grupları**
- **Canlı Önizleme** (Hover/Seçili kanal önizlemesi)
- **Hızlı Erişim Menüsü**

### 2.2. Arama ve Keşif
- **Sesli Arama**
- **Gelişmiş Filtreleme** (Dil, ülke, kalite, içerik türü)
- **Önerilen İçerikler**
- **Popüler Yayınlar**
- **Yakında Başlayacaklar**

## 3. Oynatıcı Kontrolleri

### 3.1. Temel Kontroller
- **Play/Pause**
- **İleri/Sar Geri** (10s, 30s, 1dk, 5dk)
- **Durdur**
- **Sessiz**
- **Tam Ekran**

### 3.2. Gelişmiş Kontroller
- **Yavaş Çalma Hızı** (0.5x - 2x)
- **Altyazı Ayarı** (Renk, boyut, font)
- **Ses Gecikmesi Ayarlama**
- **Görüntü Ayarları** (Parlaklık, kontrast, doygunluk)
- **Çocuk Kilidi**

## 4. Çoklu Ekran Desteği
- **PIP (Picture-in-Picture) Modu**
- **Çoklu Pencere Desteği**
- **TV Uzaktan Kumandası Uyumluluğu**
- **Akıllı TV ve Set Üstü Kutu Desteği**

## 5. Kişiselleştirme

### 5.1. Profil Yönetimi
- **Çoklu Kullanıcı Desteği**
- **Özelleştirilebilir Kanal Listeleri**
- **Tema Seçenekleri** (Açık/Koyu/Sistem)
- **Ana Ekran Düzeni Özelleştirme**

### 5.2. Favori Yönetimi
- **Kanal Favorilere Ekleme**
- **Özel Klasörler**
- **Hızlı Erişim Menüsü**

## 6. Performans ve Optimizasyon

### 6.1. Önbellek Yönetimi
- **Otomatik Önbellek Temizleme**
- **Önbellek Boyutu Ayarlama**
- **Çevrimdışı İzleme İçin İndirme**

### 6.2. Ağ Optimizasyonu
- **Otomatik Kalite Ayarlama** (Bant genişliğine göre)
- **Buffer Ayarları**
- **Proxy ve VPN Desteği**

## 7. Erişilebilirlik

### 7.1. Görsel Erişilebilirlik
- **Yüksek Kontrast Modu**
- **Renk Körlüğü Desteği**
- **Yazı Tipi Boyutu Ayarlama**

### 7.2. İşitsel Erişilebilirlik
- **Ekran Okuyucu Desteği**
- **Sesli Betikler**
- **Görsel İşitsel Uyarılar**

## 8. Güvenlik ve Gizlilik

### 8.1. Hesap Yönetimi
- **Çift Doğrulama**
- **Oturum Yönetimi**
- **Gizlilik Ayarları**

### 8.2. İçerik Koruması
- **DRM Desteği**
- **Ekran Görüntüsü Engelleme**
- **Güvenli İzleme Modu**

## 9. Çapraz Platform Desteği
- **Masaüstü Uyumluluğu**
- **Mobil Uyumluluk**
- **TV Uyumluluğı**
- **Tarayıcı Tabanlı Sürüm**

## 10. Analitik ve Geri Bildirim
- **İzleme Geçmişi**
- **Kullanım İstatistikleri**
- **Hata Raporlama**
- **Kullanıcı Geri Bildirim Sistemi**

## 1. Giriş Ekranları

### 1.1. Sağlayıcı Girişi (ProviderLoginScreen)
- **Amaç**: Kullanıcı kimlik doğrulaması
- **Bileşenler**:
  - Sunucu URL giriş alanı
  - Kullanıcı adı alanı
  - Şifre alanı
  - Giriş butonu

### 1.2. Profil Seçimi (ProfileSelectionScreen)
- **Amaç**: Kullanıcı profili seçimi
- **Bileşenler**:
  - Profil listesi
  - Yeni profil ekleme butonu

## 2. Ana Ekranlar

### 2.1. Kanal Listesi (ChannelListScreen)
- **Amaç**: Mevcut TV kanallarını gösterme
- **Bileşenler**:
  - Kanal listesi
  - Arama çubuğu
  - Kategori filtreleme
  - Canlı yayın göstergesi

### 2.2. Program Rehberi (EpgScreen)
- **Amaç**: Yayın akışını gösterme
- **Bileşenler**:
  - Zaman çizelgesi
  - Kanal listesi
  - Program detayları

## 3. Oynatıcı Ekranı (PlayerScreen)

### 3.1. Video Oynatıcı
- **Özellikler**:
  - Tam ekran modu
  - Ses kontrolü
  - Parlaklık kontrolü
  - İleri/Sar geri özelliği

### 3.2. Kontroller
- Oynat/Duraklat
- Ses seviyesi
- Ekran boyutlandırma
- Kanal listesine dönüş

## 4. Tema ve Görünüm

### 4.1. Renk Şeması
- Ana renk: `#6200EE` (Mor)
- İkincil renk: `#03DAC6` (Turkuaz)
- Arkaplan: `#121212` (Koyu gri)
- Ön plan: `#FFFFFF` (Beyaz)

### 4.2. Tipografi
- Başlık: Roboto Bold 24sp
- Alt başlık: Roboto Medium 18sp
- Normal metin: Roboto Regular 14sp
- Küçük metin: Roboto Light 12sp

## 5. Kullanıcı Etkileşimleri

### 5.1. Dokunma Geri Bildirimleri
- Butonlara basıldığında hafif ölçeklendirme
- Liste öğelerine dokunulduğunda vurgu efekti

### 5.2. Kaydırma Davranışları
- Yatay kaydırma: Program rehberinde zaman atlama
- Dikey kaydırma: Kanal listesinde gezinme

## 6. Hata Durumları

### 6.1. Bağlantı Hataları
- Çevrimdışı durum bildirimi
- Yeniden deneme butonu

### 6.2. İçerik Bulunamadı
- Boş durum görseli
- Açıklayıcı mesaj

## 7. Erişilebilirlik

### 7.1. Ekran Okuyucu Desteği
- Tüm etkileşimli öğeler için içerik açıklamaları
- Mantıksal okuma sırası

### 7.2. Renk Kontrastı
- Minimum 4.5:1 oranı
- Renk körlüğüne duyarlı renk paleti

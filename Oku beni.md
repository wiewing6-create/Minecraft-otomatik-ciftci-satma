# Mod sürümü
1.21.x
# Mod nasıl çalışıyor
Öncelikle modun slot indexi 0-53 olarak çalışıyor eğer 24. slotta bir öge varsa onu 23 olarak yazmanız gerekli. Modun menüsünü açmak için "J" tuşuna basmanız gerekli
Çalıştırdıktan sonra kapatmak için tekrar "J" tuşuna basmanız gerekli
# Mod nasıl kullanılıyor
"J" butonuna basarak menüyü açmanız gerek.
"Komut" yazan kısmın altındaki kutucuğa sunucudaki çiftçiyi açmak için kullandığınız komutu yazın (örnek olarak "/çiftçi" şeklinde yazın)
Config kısmı için ise satılacak ögeler hangi slottaysa o slotun numarası yazılması gerek (örnek olarak 24. slotta demir var ve satmanız gerekiyor bunun için "Page:1 slot:24" olarak yazmanız gerek
Eğer satmanız gereken birden fazla öge varsa hepsini "slot:" yazan kısma (örnek olarak) "slot:24,25,26" olarak yazmanız gerekir.
Satmanız gereken öge diğer sayfadaysa, o sayfaya geçip çalıştırmanız gerekli
birden fazla sayfada satmanız gereken öge varsa işiniz biraz daha zor. Öncelikle diğer sayfaya geçmek ve önceki sayfaya dönmek için bastığınız slotların numarasını belirlemeniz gerekli
slotların numarasını belirledikten sonra hangi sayfada neelr satılacak diye belirlemeniz gerekli. 
Belirledikten sonra (diyelimki ilk sayfada sattıktan sonra diğer sayfaya geçip orada belirlediklerinizi satmasını istiyorsunuz)
(sonraki sayfaya geçme slotu 53, önceki sayfaya dönme slotu 44)
"page:1 slot:24,25,26,53,14,15,16,44" şeklinde yazarak sat->diğersayfa->sat->öncekisayfa döngüsünü elde etmiş olursunuz.

# Not
Karşılaştığınız problemleri ve önerilerinizi yazarsanız ileriki sürümleri ona göre çıkaracağım.

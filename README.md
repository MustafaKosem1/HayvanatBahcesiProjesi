# Hayvanat Bahçesi Projesi

Bir hayvan class'ı ve bir main class bulunmaktadır. İki class için gerekli yerlerde yorum satırları ile açıklamalar yapılmıştır. 

Hayvan class'ı mevcut turda hayvan objesinin ürediği hayvanları tutan bir liste içermektedir. Bu listenin amacı tüm hayvanların bulunduğu listeyi gezerken üreme işleminin aynı turda iki taraflı çifter şekilde olmasını önlemektir. Ek olarak hayvanın türüne göre hareketini sağlayan metot da bu class içinde bulunmaktadır.

Main class'ta iki hayvan arasında mesafeyi hesaplayan, bir hayvanın 8 birim yakınındaki hayvanları bulan, algoritma başlarken oluşturulması gereken hayvanları oluşturan ve algoritma sonunda hangi hayvandan kaç adet olduğunu konsola yazdıran metotlar bulunmaktadır. 

Ana algoritma bir adet hayvan listesi içermektedir. Bu hayvan listesi tüm hayvanların tutulduğu listedir. 1000 tur boyunca bu listedeki hayvanların hepsi for ile dönülmektedir. Her hayvana yakın olan hayvanları bulmak için 2 boyutlu 500x500'lük alan isimli bir liste kullanılmaktadır. Bu alanda o hayvana yakın bölge tarandığı için algoritma daha verimli olmaktadır. Çünkü diğer türlü tüm alanı gezmek veya tüm hayvanları içerideki bir for döngüsünde tekrar gezmek çok daha masraflı olacaktır. Özellikle hayvan sayısı arttığında iç içe iki for döngüsü ile hayvan listesinde gezmek algoritmayı aşırı yavaşlatmaktadır. 

İçerideki for döngüsünde mevcut hayvana yakın olan hayvanların listesi dönülmektedir. Burada eğer hayvan türleri aynı ve cinsiyetleri farklı(horoz ve tavuk için farklı bir else if vardır) ise yeni oluşacak hayvan yeni hayvan listesine atılmaktadır. Devamında eğer hayvanlar aynı değilse avlama durumu incelenmektedir. Avlanabilen hayvanlar çevrelerindeki avlayabilecekleri hayvanları avlamaktadırlar. Bu avlanan hayvanlar silinecek hayvanlar listesine eklenmektedir.

Her tur sonunda yeni hayvanlar hayvan listesine eklenip, silinecekler silinip, tüm hayvanlar rastgele hareket ettirilmektedir. Genel algoritmanın sonunda ise tüm hayvanlar yazdırılmaktadır.  

Not: Hayvan sayısı için bir üst sınır olmadığı için nadiren hayvanlar aşırı sayıda(100 bin) ürediklerinde algoritma aşırı yavaşlayıp durabilmektedir.

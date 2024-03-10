import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        //tüm hayvanların bulunduğu bir liste
        ArrayList<Hayvan> hayvanlar= new ArrayList<>();
        //hayvanların bulunduğu alanı temsil eden iki boyutlu array
        List<Hayvan>[][] alan= new List[501][501];
        //array oluşturuluyor
        for (int i = 0; i < 501; i++) {
            for (int j = 0; j < 501; j++) {
                alan[i][j] = new ArrayList<>();
            }
        }
        //başlangıçta bulunan hayvanlar oluşturulup alan ve hayvanlar listesine ekleniyor.
        baslangicHayvanlariniOlustur(hayvanlar, alan);
        //1000 tur dönecek ana for döngüsü.
        for(int turSayisi=0; turSayisi<=1000; turSayisi++){
            //tur sonunda üreme sonucu oluşacak hayvanlar geçici olarak bu listeye ekleniyor.
            ArrayList<Hayvan> yeniHayvanlar= new ArrayList<>();
            //tur sonunda avlanıp silinecek hayvanlar geçici olarak bu listeye ekleniyor.
            ArrayList<Hayvan> silinecekHayvanlar= new ArrayList<>();
            //tüm hayvanların gezildiği ana for döngüsü.
            for(Hayvan hayvan: hayvanlar){
                //algoritmanın daha verimli olması amacıyla alan'a bakılarak yakındaki hayvanların bulunup
                //yazılacağı liste
                ArrayList<Hayvan> yakinHayvanlar= new ArrayList<>();
                //yakınlardaki(8 birim mesafede olan) hayvanlar bulunuyor.
                yakindakiHayvanlariBul(hayvan, alan,  yakinHayvanlar);
                //sadece en başta seçilen hayvan ile bu yakın hayvanları etkileşime sokmak için for döngüsü.
                for(Hayvan hayvan2: yakinHayvanlar){
                    //hayvanın kendine mesafesi 0 olacağı için o durumu kontrol ediyoruz.
                    if(hayvan!=hayvan2){
                        //eğer türleri aynı ise(tavuk ve horoz hariç) buraya giriyor ve yeteri kadar yakınsa ürüyor.
                        if(hayvan.getTur()==hayvan2.getTur() && !"tavuk".equals(hayvan.getTur()) && !"horoz".equals(hayvan.getTur())){
                            if(!hayvan2.getCinsiyet().equals(hayvan.getCinsiyet()) && mesafeHesapla(hayvan, hayvan2) <=3 && !hayvan.getTurIcindeUrenenler().contains(hayvan2)){
                                int cinsiyet=random.nextInt(2);
                                hayvan.getTurIcindeUrenenler().add(hayvan2);
                                hayvan2.getTurIcindeUrenenler().add(hayvan);
                                if(cinsiyet==1){
                                    yeniHayvanlar.add(new Hayvan(hayvan.getTur(),"dişi", random.nextInt(501),random.nextInt(501)));
                                }
                                else{
                                    yeniHayvanlar.add(new Hayvan(hayvan.getTur(),"erkek", random.nextInt(501),random.nextInt(501)));
                                }
                            }
                        }
                        //eğer horoz ve tavuk ikilisi ise burada ürüyor.
                        else if(!hayvan.getTurIcindeUrenenler().contains(hayvan2) &&(("tavuk".equals(hayvan.getTur()) && "horoz".equals(hayvan2.getTur()))||("horoz".equals(hayvan.getTur()) && "tavuk".equals(hayvan2.getTur())))){
                            if(mesafeHesapla(hayvan, hayvan2) <=3 ) {
                                int cinsiyet = random.nextInt(2);
                                hayvan.getTurIcindeUrenenler().add(hayvan2);
                                hayvan2.getTurIcindeUrenenler().add(hayvan);
                                if (cinsiyet == 1) {
                                    yeniHayvanlar.add(new Hayvan(hayvan.getTur(), hayvan.getCinsiyet(), random.nextInt(501), random.nextInt(501)));
                                } else {
                                    yeniHayvanlar.add(new Hayvan(hayvan2.getTur(), hayvan2.getCinsiyet(), random.nextInt(501), random.nextInt(501)));
                                }
                            }
                        }
                        //eğer üreyecek türler(aynı) değillerse avcı türlerden olup olmadıklarına göre çevresindeki
                        //hayvanları avlıyorlar.
                        else {
                            if ("avcı".equals(hayvan.getTur()) && mesafeHesapla(hayvan, hayvan2) <= 8) {
                                silinecekHayvanlar.add(hayvan2);
                            }
                            else if ("aslan".equals(hayvan.getTur()) && mesafeHesapla(hayvan, hayvan2) <= 5) {
                                if("inek".equals(hayvan2.getTur()) || "koyun".equals(hayvan2.getTur())){
                                    silinecekHayvanlar.add(hayvan2);
                                }
                            }
                            else if ("kurt".equals(hayvan.getTur()) && mesafeHesapla(hayvan, hayvan2) <= 4) {
                                if("koyun".equals(hayvan2.getTur()) || "tavuk".equals(hayvan2.getTur()) || "horoz".equals(hayvan2.getTur())){
                                    silinecekHayvanlar.add(hayvan2);
                                }
                            }
                            else{
                            }
                        }
                    }
                }
            }
            //!TUR SONU İŞLEMLERİ!
            //üreme sonucu oluşan yeni hayvanlar ekleniyor.
            for(Hayvan yeniHayvan: yeniHayvanlar){
                hayvanlar.add(yeniHayvan);
                alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            }
            //avlanma sonucu silinecek hayvanlar siliniyor.
            for(Hayvan silinecekHayvan: silinecekHayvanlar){
                hayvanlar.remove(silinecekHayvan);
                alan[silinecekHayvan.getX()][silinecekHayvan.getY()].remove(silinecekHayvan);
            }
            //hayvanlar hareket ettiriliyor.
            for(Hayvan hayvan: hayvanlar){
                alan[hayvan.getX()][hayvan.getY()].remove(hayvan);
                hayvan.hareketEt();
                hayvan.getTurIcindeUrenenler().clear();
                alan[hayvan.getX()][hayvan.getY()].add(hayvan);
            }
        }
        //konsol çıktıları yazdırılıyor.
        hayvanSayisiniYazdir(hayvanlar);
    }
    public static double mesafeHesapla(Hayvan hayvan1, Hayvan hayvan2){
        int x=hayvan1.getX()-hayvan2.getX();
        int y=hayvan1.getY()-hayvan2.getY();
        return Math.sqrt((x*x)+(y*y));
    }

    public static void yakindakiHayvanlariBul(Hayvan hayvan,  List<Hayvan>[][] alan, ArrayList<Hayvan> yakinHayvanlar){
        int x_bas;
        int x_son;
        int y_bas;
        int y_son;
        if(hayvan.getX()<8){
            x_bas=0;
        }
        else{
            x_bas=hayvan.getX()-8;
        }
        if(hayvan.getX()>492){
            x_son=500;
        }
        else {
            x_son=hayvan.getX();
        }
        if(hayvan.getY()<8){
            y_bas=0;
        }
        else{
            y_bas=hayvan.getY()-8;
        }
        if(hayvan.getY()>492){
            y_son=500;
        }
        else {
            y_son=hayvan.getY();
        }
        for(int x=x_bas; x<=x_son; x++){
            for(int y=y_bas; y<=y_son; y++){
                alan[x][y].stream().forEach(yakinhayvan-> yakinHayvanlar.add(yakinhayvan));
            }
        }
    }

    public static void baslangicHayvanlariniOlustur(List<Hayvan> hayvanlar, List<Hayvan>[][] alan){
        Random random = new Random();
        for(int a=0; a<15; a++){
            Hayvan yeniHayvan=new Hayvan("koyun", "erkek", random.nextInt(501), random.nextInt(501) );
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
        for(int a=0; a<15; a++){
            Hayvan yeniHayvan=new Hayvan("koyun", "dişi", random.nextInt(501), random.nextInt(501) );
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
        for(int a=0; a<5; a++){
            Hayvan yeniHayvan=new Hayvan("inek", "dişi", random.nextInt(501), random.nextInt(501) );
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
        for(int a=0; a<5; a++){
            Hayvan yeniHayvan=new Hayvan("inek", "erkek", random.nextInt(501), random.nextInt(501) );
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
        for(int a=0; a<10; a++){
            Hayvan yeniHayvan=new Hayvan("horoz", "erkek", random.nextInt(501), random.nextInt(501) );
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
        for(int a=0; a<10; a++){
            Hayvan yeniHayvan=new Hayvan("tavuk", "dişi", random.nextInt(501), random.nextInt(501) );
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
        for(int a=0; a<5; a++){
            Hayvan yeniHayvan=new Hayvan("kurt", "erkek", random.nextInt(501), random.nextInt(501) );
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
        for(int a=0; a<5; a++){
            Hayvan yeniHayvan=new Hayvan("kurt", "dişi", random.nextInt(501), random.nextInt(501) );
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
        for(int a=0; a<4; a++){
            Hayvan yeniHayvan=new Hayvan("aslan", "dişi", random.nextInt(501), random.nextInt(501) );
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
        for(int a=0; a<4; a++){
            Hayvan yeniHayvan=new Hayvan("aslan", "erkek", random.nextInt(501), random.nextInt(501) );
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
        for(int a=0; a<1; a++){
            Hayvan yeniHayvan=new Hayvan("avcı", "dişi", random.nextInt(501),random.nextInt(501));
            alan[yeniHayvan.getX()][yeniHayvan.getY()].add(yeniHayvan);
            hayvanlar.add(yeniHayvan);
        }
    }

    public static void hayvanSayisiniYazdir(List<Hayvan> hayvanlar){
        int inekSayisi=0;
        int tavukSayisi=0;
        int horozSayisi=0;
        int aslanSayisi=0;
        int koyunSayisi=0;
        int kurtSayisi=0;
        int avciSayisi=0;
        for(Hayvan hayvan: hayvanlar){
            if("horoz".equals(hayvan.getTur())){
                horozSayisi++;
            }
            else if("tavuk".equals(hayvan.getTur())){
                tavukSayisi++;
            }
            else if("inek".equals(hayvan.getTur())){
                inekSayisi++;
            }
            else if("aslan".equals(hayvan.getTur())){
                aslanSayisi++;
            }
            else if("koyun".equals(hayvan.getTur())){
                koyunSayisi++;
            }
            else if("kurt".equals(hayvan.getTur())){
                kurtSayisi++;
            }
            else{
                avciSayisi++;
            }
        }
        System.out.println("Toplam Hayvan Sayısı: "+hayvanlar.size());
        System.out.println("Avcı Sayısı: "+avciSayisi);
        System.out.println("Aslan Sayısı: "+aslanSayisi);
        System.out.println("Kurt Sayısı: "+kurtSayisi);
        System.out.println("Koyun Sayısı: "+koyunSayisi);
        System.out.println("Horoz Sayısı: "+horozSayisi);
        System.out.println("Tavuk Sayısı: "+tavukSayisi);
        System.out.println("İnek Sayısı: "+inekSayisi);
    }
}
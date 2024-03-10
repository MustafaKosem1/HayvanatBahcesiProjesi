import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hayvan {
    private String tur;

    private String cinsiyet;

    private int x;

    private int y;

    //birbirine yakın olan iki hayvan aynı tur içinde iki kez ürememesi için, hayvanın o turda üreme
    //gerçekleştirdiği hayvanlar burada geçici olarak tutuluyor.
    private List<Hayvan> turIcindeUrenenler= new ArrayList<>();

    public Hayvan(String tur, String cinsiyet, int x, int y) {
        if(x<0 || x>500 || y<0 || y>500){
            throw new IllegalArgumentException();
        }
        this.tur = tur;
        this.cinsiyet = cinsiyet;
        this.x = x;
        this.y = y;
    }

    //hayvan için hareket etme metodu.
    public void hareketEt(){
        Random random = new Random();
        int x;
        int y;
        if(this.tur=="avcı"||this.tur=="horoz"||this.tur=="tavuk"){
            x=random.nextInt(3)-1;
            y= random.nextInt(3)-1;
            setX(getX()+x);
            setY(getY()+y);
        }
        else if(this.tur=="koyun"||this.tur=="inek"){
            x=random.nextInt(5)-2;
            y= random.nextInt(5)-2;
            setX(getX()+x);
            setY(getY()+y);
        }
        else if(this.tur=="kurt"){
            x=random.nextInt(7)-3;
            y= random.nextInt(7)-3;
            setX(getX()+x);
            setY(getY()+y);
        }
        else{
            x=random.nextInt(9)-4;
            y= random.nextInt(9)-4;
            setX(getX()+x);
            setY(getY()+y);
        }
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if(x>500){
            this.x=500;
        }
        else if(x<0){
            this.x=0;
        }
        else{
            this.x = x;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if(y>500){
            this.y=500;
        }
        else if(y<0){
            this.y=0;
        }
        else{
            this.y = y;
        }
    }

    public List<Hayvan> getTurIcindeUrenenler() {
        return turIcindeUrenenler;
    }

    public void setTurIcindeUrenenler(List<Hayvan> turIcindeUrenenler) {
        this.turIcindeUrenenler = turIcindeUrenenler;
    }

    @Override
    public String toString() {
        return "Hayvan{" +
                "tur='" + tur + '\'' +
                ", cinsiyet='" + cinsiyet + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

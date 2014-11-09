package pl.zeromskiego.androidapp;

public class Baza {

	
	private long id;
	private String Nazwa;
	private String Opis;
	private String Miejsce;
	private String Adres;
	private String Data;
	private String Typ;
	private String Alarm;
	
	public Baza(long id, String Nazwa, String Opis, String Miejsce, String Adres, String Data, String Typ, String Alarm){
		this.id=id;
		this.Nazwa=Nazwa;
		this.Opis=Opis;
		this.Miejsce=Miejsce;
		this.Adres=Adres;
		this.Data=Data;
		this.Typ=Typ;
		this.Alarm=Alarm;
	}
	
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id=id;
	}
	public String getNazwa(){
		return Nazwa;
	}
	public void setNazwa(String Nazwa){
		this.Nazwa=Nazwa;
	}
	public String getOpis(){
		return Opis;
	}
	public void setOpis(String Opis){
		this.Opis=Opis;
	}
	public String getMiejsce(){
		return Miejsce;
	}
	public void setMiejsce(String Miejsce){
		this.Miejsce=Miejsce;
	}
	public String getAdres(){
		return Adres;
	}
	public void setAdres(String Adres){
		this.Adres=Adres;
	}
	public String getData(){
		return Data;
	}
	public void setData(String Data){
		this.Data=Data;
	}
	public String getTyp(){
		return Typ;
	}
	public void setTyp(String Typ){
		this.Typ=Typ;
	}
	public String getAlarm(){
		return Alarm;
	}
	public void setAlarm(String Alarm){
		this.Alarm=Alarm;
	}
}

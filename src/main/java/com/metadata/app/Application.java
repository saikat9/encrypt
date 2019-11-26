package com.metadata.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.metadata.app.model.Column;
import com.metadata.app.model.TabCol;


@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application implements CommandLineRunner{
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
       Column col1=new Column();
       Column col2=new Column();
       col1.setColNam("empname");
       col1.setDataTyp("varchar");
       col2.setColNam("empage");
       col2.setDataTyp("numeric");
       Map<TabCol,Column> tabCol=new HashMap<>();
       tabCol.put(new TabCol("tabcol1"), col1);
       tabCol.put(new TabCol("tabcol2"), col2);
       Encrypt encrypt=new Encrypt();
       CryptoParm tabCol2=encrypt.encr(tabCol);
       Cipher cipher2=tabCol2.getCipherLst().get(1);
       Map<SealedObject,SealedObject> tabCol9=tabCol2.getTabCol1();
       List<HashMapSeal> documents = new ArrayList<>();
       for(Map.Entry<SealedObject, SealedObject> kv :tabCol9.entrySet()) {
    	     HashMapSeal hashMapObj=new HashMapSeal();
    	     hashMapObj.setTabColNam(kv.getKey());
    	     hashMapObj.setSeal(kv.getValue());
    	     documents.add(hashMapObj);
    	  }
       mongoTemplate.dropCollection("person");
       mongoTemplate.insert(documents, "person");
   
       List<HashMapSeal>hMapLst=mongoTemplate.findAll(HashMapSeal.class, "person");
      
      Map<String,Column> tabCol4=new HashMap<>();
       for(int elemCnt=0;elemCnt<hMapLst.size();elemCnt++)
       {
    	   String key=(String) hMapLst.get(elemCnt).getTabColNam().getObject(cipher2);
    	   Column value=(Column) hMapLst.get(elemCnt).getSeal().getObject(cipher2);
    	   tabCol4.put(key, value);
       }
       
       System.out.println("hashmap after:"+tabCol4);
       
	}
	
}
class Encrypt
{
public CryptoParm encr(Map<TabCol,Column> tabCol)
{
	Map<SealedObject,SealedObject> tabCol1=new HashMap<>();
	CryptoParm cryptoParm=new CryptoParm();
	try
	{
    SecretKey key=KeyGenerator.getInstance("DES").generateKey();
    Cipher ecipher=Cipher.getInstance("DES");
    Cipher dcipher=Cipher.getInstance("DES");
    ecipher.init(Cipher.ENCRYPT_MODE, key);
    dcipher.init(Cipher.DECRYPT_MODE, key);
    //Add data to the cipher
    for(Map.Entry<TabCol, Column> kv :tabCol.entrySet())
    {
    	
    	SealedObject sealedObject2 = new SealedObject( kv.getValue(), ecipher);
    	SealedObject sealedObject1 = new SealedObject( kv.getKey(), ecipher);
    	tabCol1.put(sealedObject1, sealedObject2);
    }
 
    
    List<Cipher> cipherLst=new ArrayList<>();
    cipherLst.add(ecipher);
    cipherLst.add(dcipher);
    
    cryptoParm.setCipherLst(cipherLst);
    cryptoParm.setTabCol1(tabCol1);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
		return cryptoParm;
	}
 }
}

@Document
class HashMapSeal
{
	@Id
	private SealedObject tabColNam;
	private SealedObject seal;
	public SealedObject getTabColNam() {
		return tabColNam;
	}
	public void setTabColNam(SealedObject tabColNam) {
		this.tabColNam = tabColNam;
	}
	public SealedObject getSeal() {
		return seal;
	}
	public void setSeal(SealedObject seal) {
		this.seal = seal;
	}
	
	
}

class CryptoParm
{
	Map<SealedObject,SealedObject> tabCol1;
	List<Cipher> cipherLst;
	public Map<SealedObject, SealedObject> getTabCol1() {
		return tabCol1;
	}
	public void setTabCol1(Map<SealedObject, SealedObject> tabCol1) {
		this.tabCol1 = tabCol1;
	}
	public List<Cipher> getCipherLst() {
		return cipherLst;
	}
	public void setCipherLst(List<Cipher> cipherLst) {
		this.cipherLst = cipherLst;
	}
	
	

	
}


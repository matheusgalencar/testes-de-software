package br.edu.ifpr.pedidos;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;
class DescontoCompletoTest {
 @ParameterizedTest @CsvSource(value={
 "false|1|49999|NULL|0","false|1|50000|NULL|2500","false|1|50001|NULL|2500",
 "true|1|101|NULL|10","false|0|0|NULL|0","true|1|10000|BRANCO|1000",
 "false|0|9999|BEMVINDO|0","false|0|10000|BEMVINDO|2000","false|0|10001|BEMVINDO|2000",
 "false|1|10000|BEMVINDO|0","true|0|10000|BEMVINDO|2000",
 "false|1|19999|EXTRA10|0","false|1|20000|EXTRA10|2000","false|1|20001|EXTRA10|2000",
 "true|1|20000|EXTRA10|4000","false|1|50000|EXTRA10|7500","false|0|100000|BEMVINDO|7000"
 },delimiter='|')
 void regras(boolean vip,int compras,long subtotal,String cupom,long esperado){
  String c=cupom.equals("NULL")?null:cupom.equals("BRANCO")?"  ":cupom;
  assertEquals(esperado,new PoliticaDesconto().calcular(new Cliente(vip,false,compras),subtotal,c));
 }
 @Test void normalizacao(){assertEquals(2000,new PoliticaDesconto().calcular(new Cliente(false,false,0),10000,"  bemvindo  "));}
 @Test void invalido(){PoliticaDesconto p=new PoliticaDesconto();Cliente c=new Cliente(false,false,0);assertThrows(IllegalArgumentException.class,()->p.calcular(c,-1,null));assertThrows(IllegalArgumentException.class,()->p.calcular(c,10000,"NENHUM"));}
}

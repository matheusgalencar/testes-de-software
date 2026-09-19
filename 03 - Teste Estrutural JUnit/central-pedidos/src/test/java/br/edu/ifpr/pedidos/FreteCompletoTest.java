package br.edu.ifpr.pedidos;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;
class FreteCompletoTest {
 @ParameterizedTest @CsvSource({
 "PR,2000,29999,false,false,false,1200","SP,2000,29999,false,false,false,2000","RJ,2000,29999,false,false,false,2000","ZZ,2000,29999,false,false,false,3000",
 "PR,1999,100,false,false,false,1200","PR,2001,100,false,false,false,1500","PR,3000,100,false,false,false,1500","PR,3001,100,false,false,false,1800",
 "PR,2000,30000,false,false,false,0","PR,3001,30001,false,false,true,500",
 "PR,2000,100,false,true,false,600","PR,2000,30000,true,false,false,2700",
 "PR,2000,100,true,true,true,2600","PR,2000,30000,false,true,false,0"
 })
 void tarifas(String uf,int peso,long liquido,boolean expresso,boolean vip,boolean fragil,long esperado){
  Pedido p=new Pedido(List.of(new ItemPedido("A",100,1,1,peso,fragil)),uf,expresso,null);
  assertEquals(esperado,new CalculadoraFrete().calcular(p,new Cliente(vip,false,1),liquido));
 }
 @Test void fragilidadeUmaVez(){Pedido p=new Pedido(List.of(new ItemPedido("A",100,1,1,1,true),new ItemPedido("B",100,1,1,1,true)),"PR",false,null);assertEquals(1700,new CalculadoraFrete().calcular(p,new Cliente(false,false,1),100));}
 @Test void negativo(){assertThrows(IllegalArgumentException.class,()->new CalculadoraFrete().calcular(new Pedido(List.of(),"PR",false,null),new Cliente(false,false,1),-1));}
}

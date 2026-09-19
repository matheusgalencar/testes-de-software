package br.edu.ifpr.pedidos;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;
class DominioCompletoTest {
 ItemPedido item(int q,int estoque,boolean fragil){return new ItemPedido("A",100,q,estoque,1000,fragil);}
 @Test void cliente(){
  assertThrows(IllegalArgumentException.class,()->new Cliente(false,false,-1));
  Cliente c=new Cliente(true,true,0); assertTrue(c.vip());assertTrue(c.bloqueado());assertEquals(0,c.comprasAnteriores());
 }
 @Test void limitesItem(){
  ItemPedido i=new ItemPedido("A",1,0,0,1,false); assertEquals(0,i.totalCentavos());assertTrue(i.disponivel());
  i=new ItemPedido("B",1000000,100,100,100000,true); assertEquals(100000000L,i.totalCentavos());assertTrue(i.disponivel());
  assertFalse(item(2,1,false).disponivel());
 }
 @Test void itensInvalidos(){
  assertThrows(IllegalArgumentException.class,()->new ItemPedido(null,1,1,1,1,false));
  assertThrows(IllegalArgumentException.class,()->new ItemPedido(" ",1,1,1,1,false));
  for(long p:new long[]{0,-1,1000001}) assertThrows(IllegalArgumentException.class,()->new ItemPedido("A",p,1,1,1,false));
  for(int q:new int[]{-1,101}) assertThrows(IllegalArgumentException.class,()->new ItemPedido("A",1,q,1,1,false));
  assertThrows(IllegalArgumentException.class,()->new ItemPedido("A",1,1,-1,1,false));
  for(int p:new int[]{0,-1,100001}) assertThrows(IllegalArgumentException.class,()->new ItemPedido("A",1,1,1,p,false));
 }
 @Test void pedidoInvalido(){
  assertThrows(IllegalArgumentException.class,()->new Pedido(null,"PR",false,null));
  assertThrows(IllegalArgumentException.class,()->new Pedido(Collections.nCopies(101,item(1,1,false)),"PR",false,null));
  assertThrows(NullPointerException.class,()->new Pedido(Arrays.asList((ItemPedido)null),"PR",false,null));
  for(String uf:Arrays.asList(null,"","pr","P","PRR","1R","ÁB")) assertThrows(IllegalArgumentException.class,()->new Pedido(List.of(),uf,false,null));
  assertEquals("ZZ",new Pedido(List.of(),"ZZ",false,null).uf());
 }
 @Test void vazio(){Pedido p=new Pedido(List.of(),"PR",false,null);assertEquals(0,p.subtotalCentavos());assertEquals(0,p.pesoGramas());assertFalse(p.temFragil());assertTrue(p.estoqueSuficiente());}
 @Test void copiaDefensiva(){
  List<ItemPedido> l=new ArrayList<>();l.add(item(1,1,false));Pedido p=new Pedido(l,"PR",false,null);l.clear();
  assertEquals(1,p.itens().size());assertThrows(UnsupportedOperationException.class,()->p.itens().clear());
 }
 @Test void ativosInativosERepetidos(){
  Pedido p=new Pedido(List.of(item(0,0,true),item(1,1,false),item(2,2,true)),"PR",true,"EXTRA10");
  assertEquals(300,p.subtotalCentavos());assertEquals(3000,p.pesoGramas());assertTrue(p.temFragil());assertTrue(p.estoqueSuficiente());assertTrue(p.expresso());assertEquals("EXTRA10",p.cupom());
  assertFalse(new Pedido(List.of(item(0,0,true),item(1,1,false)),"PR",false,null).temFragil());
 }
 @Test void estoqueInicioEFim(){
  assertFalse(new Pedido(List.of(item(2,1,false),item(1,1,false)),"PR",false,null).estoqueSuficiente());
  assertFalse(new Pedido(List.of(item(1,1,false),item(2,1,false)),"PR",false,null).estoqueSuficiente());
 }
 @Test void dominioMaximo(){Pedido p=new Pedido(Collections.nCopies(100,new ItemPedido("A",1000000,100,100,100000,false)),"PR",false,null);assertEquals(10000000000L,p.subtotalCentavos());assertEquals(1000000000,p.pesoGramas());}
 @Test void resultado(){ResultadoPedido r=new ResultadoPedido("PAGO",100,10,20,110);assertEquals("PAGO",r.status());assertEquals(100,r.subtotalCentavos());assertEquals(10,r.descontoCentavos());assertEquals(20,r.freteCentavos());assertEquals(110,r.totalCentavos());}
}

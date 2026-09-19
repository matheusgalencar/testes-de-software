package br.edu.ifpr.pedidos;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CaminhosIndependentesTest {
 // subtotal=-1; VIP=False; compras=0; cupom=None; caminho A B C Z
 @Test void descontoCaminho1(){assertThrows(IllegalArgumentException.class,()->new PoliticaDesconto().calcular(new Cliente(false,false,0),-1L,null));}
 // subtotal=0; VIP=False; compras=0; cupom=None; caminho A B D F H I K Z
 @Test void descontoCaminho2(){assertEquals(0L,new PoliticaDesconto().calcular(new Cliente(false,false,0),0L,null));}
 // subtotal=0; VIP=False; compras=0; cupom=' '; caminho A B D F H I J K Z
 @Test void descontoCaminho3(){assertEquals(0L,new PoliticaDesconto().calcular(new Cliente(false,false,0),0L," "));}
 // subtotal=0; VIP=False; compras=0; cupom='BEMVINDO'; caminho A B D F H I J L M N R S U Z
 @Test void descontoCaminho4(){assertEquals(0L,new PoliticaDesconto().calcular(new Cliente(false,false,0),0L,"BEMVINDO"));}
 // subtotal=0; VIP=False; compras=0; cupom='EXTRA10'; caminho A B D F H I J L P R S U Z
 @Test void descontoCaminho5(){assertEquals(0L,new PoliticaDesconto().calcular(new Cliente(false,false,0),0L,"EXTRA10"));}
 // subtotal=0; VIP=False; compras=0; cupom='X'; caminho A B D F H I J L C Z
 @Test void descontoCaminho6(){assertThrows(IllegalArgumentException.class,()->new PoliticaDesconto().calcular(new Cliente(false,false,0),0L,"X"));}
 // subtotal=0; VIP=False; compras=1; cupom='BEMVINDO'; caminho A B D F H I J L M R S U Z
 @Test void descontoCaminho7(){assertEquals(0L,new PoliticaDesconto().calcular(new Cliente(false,false,1),0L,"BEMVINDO"));}
 // subtotal=0; VIP=True; compras=0; cupom=None; caminho A B D E I K Z
 @Test void descontoCaminho8(){assertEquals(0L,new PoliticaDesconto().calcular(new Cliente(true,false,0),0L,null));}
 // subtotal=10000; VIP=False; compras=0; cupom='BEMVINDO'; caminho A B D F H I J L M N O R S U Z
 @Test void descontoCaminho9(){assertEquals(2000L,new PoliticaDesconto().calcular(new Cliente(false,false,0),10000L,"BEMVINDO"));}
 // subtotal=10000; VIP=True; compras=0; cupom='BEMVINDO'; caminho A B D E I J L M N O R S T Z
 @Test void descontoCaminho10(){assertEquals(2000L,new PoliticaDesconto().calcular(new Cliente(true,false,0),10000L,"BEMVINDO"));}
 // subtotal=20000; VIP=False; compras=0; cupom='EXTRA10'; caminho A B D F H I J L P Q R S U Z
 @Test void descontoCaminho11(){assertEquals(2000L,new PoliticaDesconto().calcular(new Cliente(false,false,0),20000L,"EXTRA10"));}
 // subtotal=50000; VIP=False; compras=0; cupom=None; caminho A B D F G I K Z
 @Test void descontoCaminho12(){assertEquals(2500L,new PoliticaDesconto().calcular(new Cliente(false,false,0),50000L,null));}
 // líquido=-1; UF=PR; peso=2000; VIP=False; expresso=False; frágil=False; caminho A B C Z
 @Test void freteCaminho1(){assertThrows(IllegalArgumentException.class,()->new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2000,false)),"PR",false,null),new Cliente(false,false,1),-1L));}
 // líquido=100; UF=PR; peso=2000; VIP=False; expresso=False; frágil=False; caminho A B D E I J L O Q S U Z
 @Test void freteCaminho2(){assertEquals(1200L,new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2000,false)),"PR",false,null),new Cliente(false,false,1),100L));}
 // líquido=100; UF=PR; peso=2000; VIP=False; expresso=False; frágil=True; caminho A B D E I J L O Q S T U Z
 @Test void freteCaminho3(){assertEquals(1700L,new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2000,true)),"PR",false,null),new Cliente(false,false,1),100L));}
 // líquido=100; UF=PR; peso=2000; VIP=False; expresso=True; frágil=False; caminho A B D E I J L O Q R S U Z
 @Test void freteCaminho4(){assertEquals(2700L,new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2000,false)),"PR",true,null),new Cliente(false,false,1),100L));}
 // líquido=100; UF=PR; peso=2000; VIP=True; expresso=False; frágil=False; caminho A B D E I J L O P Q S U Z
 @Test void freteCaminho5(){assertEquals(600L,new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2000,false)),"PR",false,null),new Cliente(true,false,1),100L));}
 // líquido=100; UF=PR; peso=2001; VIP=False; expresso=False; frágil=False; caminho A B D E I J K J L O Q S U Z
 @Test void freteCaminho6(){assertEquals(1500L,new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2001,false)),"PR",false,null),new Cliente(false,false,1),100L));}
 // líquido=100; UF=SP; peso=2000; VIP=False; expresso=False; frágil=False; caminho A B D F I J L O Q S U Z
 @Test void freteCaminho7(){assertEquals(2000L,new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2000,false)),"SP",false,null),new Cliente(false,false,1),100L));}
 // líquido=100; UF=RJ; peso=2000; VIP=False; expresso=False; frágil=False; caminho A B D G I J L O Q S U Z
 @Test void freteCaminho8(){assertEquals(2000L,new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2000,false)),"RJ",false,null),new Cliente(false,false,1),100L));}
 // líquido=100; UF=ZZ; peso=2000; VIP=False; expresso=False; frágil=False; caminho A B D H I J L O Q S U Z
 @Test void freteCaminho9(){assertEquals(3000L,new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2000,false)),"ZZ",false,null),new Cliente(false,false,1),100L));}
 // líquido=30000; UF=PR; peso=2000; VIP=False; expresso=False; frágil=False; caminho A B D E I J L M N O Q S U Z
 @Test void freteCaminho10(){assertEquals(0L,new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2000,false)),"PR",false,null),new Cliente(false,false,1),30000L));}
 // líquido=30000; UF=PR; peso=2000; VIP=False; expresso=True; frágil=False; caminho A B D E I J L M O Q R S U Z
 @Test void freteCaminho11(){assertEquals(2700L,new CalculadoraFrete().calcular(new Pedido(List.of(new ItemPedido("A",100,1,1,2000,false)),"PR",true,null),new Cliente(false,false,1),30000L));}
 // total=-1; bloqueado=False; compras=0; expresso=False; VIP=False; caminho A B C Z
 @Test void riscoCaminho1(){assertThrows(IllegalArgumentException.class,()->new AnaliseRisco().avaliar(new Cliente(false,false,0),-1L,false));}
 // total=0; bloqueado=False; compras=0; expresso=False; VIP=False; caminho A B D F G H L Z
 @Test void riscoCaminho2(){assertEquals("APROVADO",new AnaliseRisco().avaliar(new Cliente(false,false,0),0L,false));}
 // total=0; bloqueado=False; compras=0; expresso=True; VIP=False; caminho A B D F G H I Z
 @Test void riscoCaminho3(){assertEquals("REVISAO",new AnaliseRisco().avaliar(new Cliente(false,false,0),0L,true));}
 // total=0; bloqueado=False; compras=1; expresso=False; VIP=False; caminho A B D F J L Z
 @Test void riscoCaminho4(){assertEquals("APROVADO",new AnaliseRisco().avaliar(new Cliente(false,false,1),0L,false));}
 // total=0; bloqueado=True; compras=0; expresso=False; VIP=False; caminho A B D E Z
 @Test void riscoCaminho5(){assertEquals("RECUSADO",new AnaliseRisco().avaliar(new Cliente(false,true,0),0L,false));}
 // total=100001; bloqueado=False; compras=0; expresso=False; VIP=False; caminho A B D F G I Z
 @Test void riscoCaminho6(){assertEquals("REVISAO",new AnaliseRisco().avaliar(new Cliente(false,false,0),100001L,false));}
 // total=500001; bloqueado=False; compras=1; expresso=False; VIP=False; caminho A B D F J K I Z
 @Test void riscoCaminho7(){assertEquals("REVISAO",new AnaliseRisco().avaliar(new Cliente(false,false,1),500001L,false));}
 // total=500001; bloqueado=False; compras=1; expresso=False; VIP=True; caminho A B D F J K L Z
 @Test void riscoCaminho8(){assertEquals("APROVADO",new AnaliseRisco().avaliar(new Cliente(true,false,1),500001L,false));}
}
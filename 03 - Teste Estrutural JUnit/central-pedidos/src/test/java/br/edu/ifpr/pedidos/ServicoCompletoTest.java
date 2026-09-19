package br.edu.ifpr.pedidos;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;
class ServicoCompletoTest {
 Cliente comum=new Cliente(false,false,1);
 Pedido pedido(long preco,int estoque,boolean expresso,String cupom){return new Pedido(List.of(new ItemPedido("A",preco,1,estoque,1000,false)),"PR",expresso,cupom);}
 void resultado(ResultadoPedido r,String s,long subtotal,long desconto,long frete,long total){assertEquals(new ResultadoPedido(s,subtotal,desconto,frete,total),r);}
 @Test void nulos(){PedidoService s=new PedidoService(t->true);assertThrows(NullPointerException.class,()->new PedidoService(null));assertThrows(NullPointerException.class,()->s.fechar(null,comum));assertThrows(NullPointerException.class,()->s.fechar(pedido(10000,1,false,null),null));}
 @Test void bloqueioAntesDoSubtotalECupom(){AtomicInteger n=new AtomicInteger();PedidoService s=new PedidoService(t->{n.incrementAndGet();return true;});resultado(s.fechar(new Pedido(List.of(),"PR",false,"INVALIDO"),new Cliente(false,true,0)),"BLOQUEADO",0,0,0,0);assertEquals(0,n.get());}
 @Test void semAtivos(){PedidoService s=new PedidoService(t->{fail("Não deveria cobrar");return true;});assertThrows(IllegalArgumentException.class,()->s.fechar(new Pedido(List.of(),"PR",false,null),comum));assertThrows(IllegalArgumentException.class,()->s.fechar(new Pedido(List.of(new ItemPedido("A",1,0,0,1,false)),"PR",false,null),comum));}
 @Test void estoqueAntesDoCupom(){PedidoService s=new PedidoService(t->{fail("Não deveria cobrar");return true;});resultado(s.fechar(pedido(10000,0,false,"INVALIDO"),comum),"SEM_ESTOQUE",0,0,0,0);}
 @Test void cupomInvalidoNaoCobra(){PedidoService s=new PedidoService(t->{fail("Não deveria cobrar");return true;});assertThrows(IllegalArgumentException.class,()->s.fechar(pedido(10000,1,false,"INVALIDO"),comum));}
 @Test void revisaoNaoCobra(){PedidoService s=new PedidoService(t->{fail("Não deveria cobrar");return true;});resultado(s.fechar(pedido(10000,1,true,null),new Cliente(false,false,0)),"REVISAO",10000,0,2700,12700);}
 @Test void pagoComDescontoEFrete(){AtomicInteger n=new AtomicInteger();PedidoService s=new PedidoService(t->{assertEquals(30100,t);n.incrementAndGet();return true;});resultado(s.fechar(pedido(35000,1,true,"EXTRA10"),new Cliente(true,false,1)),"PAGO",35000,7000,2100,30100);assertEquals(1,n.get());}
 @Test void recusado(){AtomicInteger n=new AtomicInteger();PedidoService s=new PedidoService(t->{assertEquals(11200,t);n.incrementAndGet();return false;});resultado(s.fechar(pedido(10000,1,false,null),comum),"PAGAMENTO_RECUSADO",10000,0,1200,11200);assertEquals(1,n.get());}
 @Test void tresTentativas(){AtomicInteger n=new AtomicInteger();PedidoService s=new PedidoService(t->{assertEquals(11200,t);if(n.incrementAndGet()<3)throw new IllegalStateException();return true;});resultado(s.fechar(pedido(10000,1,false,null),comum),"PAGO",10000,0,1200,11200);assertEquals(3,n.get());}
 @Test void indisponivel(){AtomicInteger n=new AtomicInteger();PedidoService s=new PedidoService(t->{n.incrementAndGet();throw new IllegalStateException();});resultado(s.fechar(pedido(10000,1,false,null),comum),"PAGAMENTO_RECUSADO",10000,0,1200,11200);assertEquals(3,n.get());}
 @Test void propagaExcecao(){PedidoService s=new PedidoService(t->{throw new UnsupportedOperationException();});assertThrows(UnsupportedOperationException.class,()->s.fechar(pedido(10000,1,false,null),comum));}
}


package br.edu.ifpr.pedidos;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;
class PagamentoCompletoTest {
 @Test void aprovaUmaVez(){AtomicInteger n=new AtomicInteger();assertTrue(new PagamentoService(t->{assertEquals(123,t);n.incrementAndGet();return true;}).pagar(123,3));assertEquals(1,n.get());}
 @Test void recusaSemRepetir(){AtomicInteger n=new AtomicInteger();assertFalse(new PagamentoService(t->{n.incrementAndGet();return false;}).pagar(123,3));assertEquals(1,n.get());}
 @ParameterizedTest @ValueSource(ints={1,2,3})
 void sucessoAposFalhas(int sucesso){AtomicInteger n=new AtomicInteger();assertTrue(new PagamentoService(t->{assertEquals(100,t);if(n.incrementAndGet()<sucesso)throw new IllegalStateException();return true;}).pagar(100,3));assertEquals(sucesso,n.get());}
 @ParameterizedTest @ValueSource(ints={1,2,3})
 void esgota(int max){AtomicInteger n=new AtomicInteger();assertFalse(new PagamentoService(t->{n.incrementAndGet();throw new IllegalStateException();}).pagar(100,max));assertEquals(max,n.get());}
 @Test void falhaDepoisRecusa(){AtomicInteger n=new AtomicInteger();assertFalse(new PagamentoService(t->{if(n.incrementAndGet()==1)throw new IllegalStateException();return false;}).pagar(100,3));assertEquals(2,n.get());}
 @Test void propaga(){AtomicInteger n=new AtomicInteger();RuntimeException erro=new UnsupportedOperationException("x");assertSame(erro,assertThrows(UnsupportedOperationException.class,()->new PagamentoService(t->{n.incrementAndGet();throw erro;}).pagar(100,3)));assertEquals(1,n.get());}
 @Test void validacoes(){
  assertThrows(NullPointerException.class,()->new PagamentoService(null));
  AtomicInteger n=new AtomicInteger();PagamentoService p=new PagamentoService(t->{n.incrementAndGet();return true;});
  for(long t:new long[]{-1,0}) assertThrows(IllegalArgumentException.class,()->p.pagar(t,3));
  for(int max:new int[]{0,4})assertThrows(IllegalArgumentException.class,()->p.pagar(1,max));assertEquals(0,n.get());
 }
}

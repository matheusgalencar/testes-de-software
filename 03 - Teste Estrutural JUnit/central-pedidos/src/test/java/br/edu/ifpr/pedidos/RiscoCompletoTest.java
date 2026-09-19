package br.edu.ifpr.pedidos;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;
class RiscoCompletoTest {
 @ParameterizedTest @CsvSource({
 "false,true,0,0,false,RECUSADO",
 "false,false,0,99999,false,APROVADO","false,false,0,100000,false,APROVADO","false,false,0,100001,false,REVISAO","false,false,0,0,true,REVISAO",
 "false,false,1,499999,false,APROVADO","false,false,1,500000,false,APROVADO","false,false,1,500001,false,REVISAO","true,false,1,500001,true,APROVADO"
 })
 void regras(boolean vip,boolean bloqueado,int compras,long total,boolean expresso,String esperado){assertEquals(esperado,new AnaliseRisco().avaliar(new Cliente(vip,bloqueado,compras),total,expresso));}
 @Test void negativo(){assertThrows(IllegalArgumentException.class,()->new AnaliseRisco().avaliar(new Cliente(false,false,0),-1,false));}
}

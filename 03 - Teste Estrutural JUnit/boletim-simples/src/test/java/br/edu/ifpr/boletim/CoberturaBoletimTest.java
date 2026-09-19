package br.edu.ifpr.boletim;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
class CoberturaBoletimTest {
 private final Boletim b = new Boletim();
 @ParameterizedTest @CsvSource({"0,0,0","10,10,10","5,8,6.5","8,5,6.5","0,10,5","3.1,4.2,3.65"})
 void media(double a,double c,double esperado) { assertEquals(esperado,b.calcularMedia(a,c),0.0001); }
 @ParameterizedTest @CsvSource({"0,REPROVADO","3.99,REPROVADO","4,RECUPERACAO","4.01,RECUPERACAO","6.99,RECUPERACAO","7,APROVADO","7.01,APROVADO","10,APROVADO"})
 void situacao(double m,String esperado) { assertEquals(esperado,b.verificarSituacao(m)); }
 @Test void arrayVazio(){assertEquals(0,b.contarAprovados(new double[]{}));}
 @Test void umAprovado(){assertEquals(1,b.contarAprovados(new double[]{7}));}
 @Test void umReprovado(){assertEquals(0,b.contarAprovados(new double[]{6.99}));}
 @Test void varios(){assertEquals(3,b.contarAprovados(new double[]{8,5,7,0,10}));}
 @ParameterizedTest @CsvSource({"false,false,0","true,false,2","false,true,1","true,true,3"})
 void participacao(boolean atividade,boolean aula,int esperado){assertEquals(esperado,new Participacao().calcularPontos(atividade,aula));}
}

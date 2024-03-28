package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class VelozesFuriosos extends Thread{
    private Semaphore escuderia;
    private Semaphore pista;
    private int corredor;
    private int escud;
    private int voltaMaisRapida;

    public VelozesFuriosos(Semaphore escuderia, Semaphore pista, int corredor, int escud){
        this.escuderia = escuderia;
        this.pista = pista;
        this.corredor = corredor;
        this.escud = escud;
    }

    public void run() {
        try {
            escuderia.acquire();
            entrarNaPista();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Terminou suas voltas: | Corredor: "+corredor+" | Escuderia: "+escud);
            escuderia.release();
        }
    }

    private void entrarNaPista() {
        try {
            pista.acquire();
            System.out.println("Entrou na pista: | Corredor: "+corredor+" | Escuderia: "+escud);
            primeiraVolta();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("Saiu da pista : | Corredor: "+corredor+" | Escuderia: "+escud);
            pista.release();
        }
    }

    private void primeiraVolta() {
        int volta = geraTempo();
        try {
            sleep((long) volta);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("| Corredor: "+corredor+" | Escuderia: "+escud+" | Tempo 1º: "+volta+"secs.");
        voltaMaisRapida = volta;
        segundaVolta();
    }

    private void segundaVolta() {
        int volta = geraTempo();
        try {
            sleep((long) volta);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("| Corredor: "+corredor+" | Escuderia: "+escud+" | Tempo 2º: "+volta+"secs.");
        if(voltaMaisRapida>volta){
            voltaMaisRapida = volta;
        }
        terceiraVolta();
    }

    private void terceiraVolta() {
        int volta = geraTempo();
        try {
            sleep((long) volta);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("| Corredor: "+corredor+" | Escuderia: "+escud+" | Tempo 3º: "+volta+"secs.");
        if(voltaMaisRapida>volta){
            voltaMaisRapida = volta;
        }
    }

    public int getVoltaMaisRapida() {
        return voltaMaisRapida;
    }

    public int getCorredor() {
        return corredor;
    }

    private int geraTempo(){
        Random random = new Random();
        return random.nextInt(1000, 5000);

    }
}

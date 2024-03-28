package view;

import controller.VelozesFuriosos;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore [] escuderias = new Semaphore[7];
        Semaphore pista = new Semaphore(5);
        VelozesFuriosos [] vf = new VelozesFuriosos[14];
        int [][] matriz = new int[2][14];
        for(int h = 0; h< escuderias.length; h++){
            escuderias[h] = new Semaphore(1);
        }
        for(int x = 0; x< 14; x++) {
            int y = x %7;
            vf[x] = new VelozesFuriosos(escuderias[y], pista,x+1,y+1);
            vf[x].start();
            }
        for(Thread thread: vf){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int x = 0; x<vf.length; x++){
            matriz[0][x] = vf[x].getCorredor();
            matriz[1][x] = vf[x].getVoltaMaisRapida();
        }
        for(int x = 0; x<matriz[0].length-1; x++){
            for(int y = 0; y<matriz[0].length-1-x; y++){
                if(matriz[1][y]> matriz[1][y+1]){
                    int[] help =  {matriz[0][y],matriz[1][y]};
                    matriz[0][y] = matriz[0][y+1];
                    matriz[1][y] = matriz[1][y+1];
                    matriz[0][y+1] = help[0];
                    matriz[1][y+1] = help[1];
                }
            }
        }
        for(int x = 0; x<14; x++){
            System.out.println("Largada "+(1+x)+"º : Corredor "+matriz[0][x]+" com tempo de: "+matriz[1][x]+"secs");
        }
    }
}
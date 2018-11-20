/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Transmissor {
    
    private OutputStream   osSaida;
    private PrintWriter    pwSaida;
    private Socket         dispositivoCliente;
    private Gson           gson;
    private GeradorGrafico geradorGrafico;
    
    private String       json;
    
    public void enviar(Modelo modelo, String ip, int porta, boolean teste){
        geradorGrafico = new GeradorGrafico();
        try{
            gson = new Gson();
            json = gson.toJson(modelo);
            System.out.println("Preparando para enviar mensagem...");
            dispositivoCliente = new Socket(ip, porta);
            osSaida = dispositivoCliente.getOutputStream();
            pwSaida = new PrintWriter(osSaida);
            pwSaida.print(json);
            System.out.println("Parametros enviados" + json);
            System.out.println("Mensagem enviada!!!\n");
            osSaida.flush();
            pwSaida.flush();
            
            osSaida.close();
            pwSaida.close();
        }catch(IOException erro){
            System.out.println("Erro no envio da mensagem: " + erro.getMessage());
        }
        /*if(teste){
            for(int i = 0; i < modelo.getPalavras().size(); i++){
                geradorGrafico.addValor(Double.parseDouble(modelo.getVezesPalavras().get(i)), modelo.getPalavras().get(i), modelo.getPalavras().get(i), i);
            }
            geradorGrafico.exibeGrafico();
        }*/
    }
    
}
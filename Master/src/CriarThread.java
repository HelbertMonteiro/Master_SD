/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.gson.Gson;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Helbert Monteiro
 */
public class CriarThread {
    
    private ServerSocket socketServidor;
    private Socket       dispositivoCliente;
    
    private Scanner  scanner;
    private String   json, subTexto;
    private int      index;
    private String[] palavras;
    
    private Gson                   gson;
    private Modelo                 modelo;
    private NodePalavra            nodePalavra;
    private ArrayList<NodePalavra> listaNodePalavra;
    
    public void run(int porta, ArrayList<Node> listaNodes){
        try {
            socketServidor = new ServerSocket(porta);
            System.out.println("Ouvindo na porta " + porta + "...");

            while(true) {
                dispositivoCliente = socketServidor.accept();
                System.out.println("\n\nCliente conectado: " + dispositivoCliente.getInetAddress().getHostAddress());

                scanner = new Scanner(dispositivoCliente.getInputStream());
                json    = scanner.nextLine();
                
                gson   = new Gson();
                modelo = new Modelo();
                modelo = gson.fromJson(json, Modelo.class);
                
                switch(modelo.getCodigo()){
                    case 1:
                        distribuir(modelo, listaNodes);
                }
                
                
                dispositivoCliente.close();
            }
            
        }catch(IOException a) {
            System.out.println("Erro na Thread: " + a.getMessage());
        }
    }
    
    private void distribuir(Modelo modelo, ArrayList<Node> listaNodes){
        palavras         = modelo.getTexto().split(" ");
        listaNodePalavra = new ArrayList<>();
        index            = 0;
        
        for(int i = 0; i < listaNodes.size(); i++){
            nodePalavra = new NodePalavra(i);
            listaNodePalavra.add(nodePalavra);
        }
        
        for(int i = 0; i < palavras.length; i++){
            if(index < listaNodes.size()){
                listaNodePalavra.get(index).addPalavra(palavras[i]);
                index++;
            }else{
                index = 0;
                listaNodePalavra.get(index).addPalavra(palavras[i]);
                index++;
            }
        }
        
        System.out.println("Texto Inteiro: " + modelo.getTexto());
        subTexto = "";
        
        for(int i = 0; i < listaNodePalavra.size(); i++){
            for(int j = 0; j < listaNodePalavra.get(i).getListaPalavras().size(); j++){
                subTexto = subTexto + listaNodePalavra.get(i).getListaPalavras().get(j) + " ";
            }
            System.out.println("Palavras separadas para Node " + i + ": " + subTexto);
            
            modelo = new Modelo(modelo.getCodigo(), modelo.getPalavras(), subTexto);
            
            new Transmissor().enviar(modelo, listaNodes.get(i).getIp(), Integer.parseInt(listaNodes.get(i).getPorta()));
            
            subTexto = "";
        }
    }

}
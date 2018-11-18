
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Helbert Monteiro
 */
public class Modelo {
    
    private int               codigo;
    private ArrayList<String> palavras;
    private String            texto;
    
    public Modelo(){}
    
    public Modelo(int codigo, ArrayList<String> palavras, String texto){
        this.codigo   = codigo;
        this.palavras = palavras;
        this.texto    = texto;
    }
    
    public int getCodigo(){
        return codigo;
    }
    
    public ArrayList<String> getPalavras(){
        return palavras;
    }
    
    public String getTexto(){
        return texto;
    }
    
}

package Snake.src;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaInicial extends JPanel implements ActionListener { 

    private static final int Largura= 900;
    private static final int Altura= 900;
    private static final int TamanhoBloco= 50;
    private static final int Unidades= Largura * Altura / (TamanhoBloco*TamanhoBloco);
    private int CorpoCobra= 6;
    private final int[] eixoX= new int[Unidades];
    private final int[] eixoY= new int[Unidades];
    private static final String NomeFonte = "Futura";
    public static char direcao= 'B'; // D = Direita, E = Esquerda, B = Baixo, C = Cima
    private int blocoX;
    private int blocoY;
    private int blocosComidos;
    private boolean rodando = false;
    private int Intervalo = 75;
    Random random;
    Timer timer;
    TelaInicial() {
        random= new Random();
        setPreferredSize(new Dimension(Largura, Altura));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new LeitorDeTecla());
        iniciarJogo();
    }
    public void iniciarJogo(){
        // A cobra começa no centro
        eixoX[0] = (Largura / 2 / TamanhoBloco) * TamanhoBloco;
        eixoY[0] = (Altura / 2 / TamanhoBloco) * TamanhoBloco;
        // Corpo começe atrás da cabeça
        for (int i = 1; i < CorpoCobra; i++) {
            eixoX[i] = eixoX[0] - i * TamanhoBloco;
            eixoY[i] = eixoY[0];
        }
        criarBloco();
        rodando = true;
        timer = new Timer(Intervalo, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        fazerTela(g);
    }

    public void fazerTela(Graphics g){ // Criar a tela do jogo
        if (rodando){
            ImageIcon imagemback = new ImageIcon("Snake/src/resources/background.png");
            Image back = imagemback.getImage();
            g.drawImage(back, 0,0, Largura, Altura, this);
            ImageIcon imagemMaca = new ImageIcon("Snake/src/resources/maca.png");
            Image maca = imagemMaca.getImage();
            ImageIcon imagemCorpo = new ImageIcon("Snake/src/resources/corpoCobra.png");
            Image corpo = imagemCorpo.getImage();
            ImageIcon imagemCabecaC = new ImageIcon("Snake/src/resources/cabecaC.png");
            Image cabecaC = imagemCabecaC.getImage();
            ImageIcon imagemCabecaB = new ImageIcon("Snake/src/resources/cabecaB.png");
            Image cabecaB = imagemCabecaB.getImage();
            ImageIcon imagemCabecaD = new ImageIcon("Snake/src/resources/cabecaD.png");
            Image cabecaD = imagemCabecaD.getImage();
            ImageIcon imagemCabecaE = new ImageIcon("Snake/src/resources/cabecaE.png");
            Image cabecaE = imagemCabecaE.getImage();
            Image cabecaAtual;
            switch (direcao) {
                case 'C': cabecaAtual = cabecaC; break;
                case 'B': cabecaAtual = cabecaB; break;
                case 'E': cabecaAtual = cabecaE; break;
                case 'D': cabecaAtual = cabecaD; break;
                default: cabecaAtual = cabecaD; break;
            }
            g.drawImage(maca, blocoX, blocoY, TamanhoBloco, TamanhoBloco, this);
            for(int i =0; i< CorpoCobra; i++){ //Cria a cobra
                if (i==0) {
                    //g.setColor(new Color(0,180,0));
                    g.drawImage(cabecaAtual, eixoX[i], eixoY[i], TamanhoBloco, TamanhoBloco, this);
                    
                } else {
                    g.drawImage(corpo, eixoX[i], eixoY[i], TamanhoBloco, TamanhoBloco, this);
                }
                g.setColor(Color.CYAN);
                g.setFont(new Font(NomeFonte, Font.BOLD, 36));
                g.drawString("Pontos: "+ blocosComidos, 50, 35);
            }
        } else {
            fimJogo(g);
        }
    }

    public void criarBloco(){ // Criar um bloco em uma posição aleatória do mapa
        int margem= 50; // Para nao criar nas bordas da janela

        blocoX=margem + random.nextInt((Largura-2*margem)/TamanhoBloco)* TamanhoBloco;
        blocoY=margem + random.nextInt((Altura-2*margem)/TamanhoBloco)* TamanhoBloco;
    }

    public void fimJogo(Graphics g){ // A cobra morreu
        ImageIcon imagemBack = new ImageIcon("Snake/src/resources/background1.png");
        Image background = imagemBack.getImage();
        g.drawImage(background, 0, 0, Largura, Altura, this);
        g.setColor(Color.CYAN);
        g.setFont(new Font(NomeFonte, Font.BOLD, 36));
        FontMetrics fontePontos = getFontMetrics(g.getFont());
        g.drawString("Pontos: "+ blocosComidos, (Largura - fontePontos.stringWidth("Pontos: "+ blocosComidos))/2, Altura/2);
        g.setColor(Color.red);
        g.setFont(new Font(NomeFonte, Font.BOLD, 75));
        FontMetrics fonteFim = getFontMetrics(g.getFont());
        g.drawString("Fim de Jogo", (Largura - fonteFim.stringWidth("Fim de Jogo"))/2, Altura/2-100);
    }
    public void actionPerformed(ActionEvent e ){
        if (rodando) {
            andar();
            alcancarBloco();
            validarLimites();
            
        } 
        repaint(); 
    }
    private void andar(){ // Fazer a cobra se mexer
        for(int i = CorpoCobra ; i>0; i--){
            eixoX[i]= eixoX[i-1];
            eixoY[i]= eixoY[i-1];
        }
        switch (direcao) {
            case 'C':
                eixoY[0] = eixoY[0] - TamanhoBloco;
                break;
            case 'B':
                eixoY[0] = eixoY[0] + TamanhoBloco;
                break;
            case 'D':
                eixoX[0] = eixoX[0] + TamanhoBloco;
                break;
            case 'E':
                eixoX[0] = eixoX[0] - TamanhoBloco;
                break;
            default:
                break;
        }
    }
    private void alcancarBloco(){ // A cobra comeu um ponto
        if (eixoX[0]== blocoX && eixoY[0]==blocoY) {
            CorpoCobra++;
            blocosComidos++;
            criarBloco();
        }
    }
    private void validarLimites(){
        // Verificar se a cabeça bateu no corpo
        for(int i = CorpoCobra ; i>0; i--){
            if (eixoX[0]==eixoX[i]&&eixoY[0]==eixoY[i]){
                rodando=false;
                break;
            }
        }
        // Verificar se a cabeça bateu nas paredes
        if (eixoX[0]<50||eixoX[0]> (Largura-100)) {
            rodando=false;
        }
        // Verificar se a cabeça bateu no chão ou no teto
        if (eixoY[0]<50||eixoY[0]> (Altura-100)){
            rodando=false;
        }
        // Para o timer quando morrer
        if (!rodando) {
            timer.stop();
        }
    }
    public class LeitorDeTecla extends KeyAdapter { //Verifica qual tecla foi pressionada

        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
                    if (TelaInicial.direcao != 'D') {
                        TelaInicial.direcao = 'E'; 
                    }
                    break;
                case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
                    if (TelaInicial.direcao != 'E') {
                        TelaInicial.direcao = 'D'; 
                    }
                    break;
                case KeyEvent.VK_UP: case KeyEvent.VK_W:
                    if (TelaInicial.direcao != 'B') {
                        TelaInicial.direcao = 'C'; 
                    }
                    break;
                case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
                    if (TelaInicial.direcao != 'C') {
                        TelaInicial.direcao = 'B'; 
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
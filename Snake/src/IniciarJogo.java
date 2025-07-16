package Snake.src;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.*;

public class IniciarJogo {
    public static void main(String[] args){
        JFrame frame= new JFrame("Snake Game");
        frame.add(new TelaInicial());
        frame.setTitle("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        ImageIcon imagemIcon = new ImageIcon("Snake/src/resources/jackfrost.jpg");
        Image icon = imagemIcon.getImage();
        frame.setIconImage(icon);
    }
}

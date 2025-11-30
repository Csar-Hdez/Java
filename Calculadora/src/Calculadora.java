// Importación de las librerías necesarias
import java.awt.*;           // Para componentes gráficos básicos
import java.awt.event.*;     // Para manejar eventos (clic en botones)
import java.util.Arrays;     // Para usar Arrays.asList()
import javax.swing.*;        // Para componentes de interfaz gráfica
import javax.swing.border.LineBorder;  // Para bordes de botones

public class Calculadora {
    // Ancho y alto de la ventana
    int boardwidth = 360;    // Ancho de la ventana en píxeles
    int boardheigh = 540;    // Alto de la ventana en píxeles

    // Colores de la calculadora - definiendo la paleta de colores
    Color grizClaro = new Color(212,212,210);    // Gris claro para botones superiores
    Color grizOscuroo = new Color(80,80,80);     // Gris oscuro para números
    Color negro = new Color(28,28,28);           // Negro para fondo
    Color naranja = new Color(255,19,0);         // Naranja para operadores

    // Array con los textos de todos los botones en orden
    String[] botones = {
            "AC","+/-","%","÷",     // Fila 1: Limpiar, signo, porcentaje, división
            "7","8","9","x",        // Fila 2: Números 7-9 y multiplicación
            "4","5","6","-",        // Fila 3: Números 4-6 y resta
            "1","2","3","+",        // Fila 4: Números 1-3 y suma
            "0",".","√","="         // Fila 5: Cero, punto decimal, raíz, igual
    };
    
    // Botones de la columna derecha (operadores)
    String[] simbolosDerecha = {"÷","x","-","+","="};
    // Botones de la fila superior (funciones especiales)
    String[] simbolosArriba = {"AC","+/-","%"};

    // Componentes de la interfaz gráfica
    JFrame frame = new JFrame("Calculadora");        // Ventana principal
    JLabel displaylabel = new JLabel();              // Etiqueta para mostrar números
    JPanel displayPanel = new JPanel();              // Panel para el display
    JPanel botonesPanel = new JPanel();              // Panel para los botones

    // Variables para almacenar los números y operaciones
    String A = "0";          // Primer número de la operación
    String operador = null;  // Operador actual (+, -, x, ÷)
    String B = null;         // Segundo número de la operación

    public Calculadora(){
        // Configuración de la ventana principal
        frame.setSize(boardwidth, boardheigh);           // Tamaño de la ventana
        frame.setLocationRelativeTo(null);               // Centrar en pantalla
        frame.setResizable(false);                       // No redimensionable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar aplicación al salir
        frame.setLayout(new BorderLayout());             // Layout para organizar componentes

        // Configuración del display (donde se ven los números)
        displaylabel.setBackground(negro);               // Fondo negro
        displaylabel.setForeground(Color.white);         // Texto blanco
        displaylabel.setFont(new Font("Arial", Font.PLAIN, 80)); // Fuente grande
        displaylabel.setHorizontalAlignment(JLabel.RIGHT); // Alinear texto a la derecha
        displaylabel.setText("0");                       // Texto inicial
        displaylabel.setOpaque(true);                    // Hacer fondo visible

        // Configuración del panel del display
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displaylabel);                  // Agregar el label al panel
        frame.add(displayPanel, BorderLayout.NORTH);     // Panel en la parte superior

        // Configuración del panel de botones
        botonesPanel.setLayout(new GridLayout(5, 4));   // Grid de 5 filas x 4 columnas
        botonesPanel.setBackground(negro);               // Fondo negro
        frame.add(botonesPanel);                         // Agregar panel a la ventana

        // Crear todos los botones dinámicamente
        for (int i = 0; i < botones.length; i++){
            JButton boton = new JButton();               // Crear nuevo botón
            String botonValor = botones[i];              // Texto del botón actual
            boton.setFont(new Font("Arial", Font.PLAIN, 30)); // Fuente del botón
            boton.setText(botonValor);                   // Establecer texto
            boton.setFocusable(false);                   // Evitar que se enfoque con Tab
            botonesPanel.add(boton);                     // Agregar botón al panel
            boton.setBorder(new LineBorder(negro));      // Borde negro

            // Asignar colores según el tipo de botón
            if(Arrays.asList(simbolosArriba).contains(botonValor)){
                // Botones superiores (AC, +/-, %) - Gris claro
                boton.setBackground(grizClaro);
                boton.setForeground(negro);
            } else if(Arrays.asList(simbolosDerecha).contains(botonValor)){
                // Botones derechos (operadores) - Naranja
                boton.setBackground(naranja);
                boton.setForeground(Color.WHITE);
            } else {
                // Botones numéricos - Gris oscuro
                boton.setBackground(grizOscuroo);
                boton.setForeground(Color.WHITE);
            }

            // Agregar ActionListener para detectar clics
            boton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    JButton boton = (JButton) e.getSource(); // Botón que se presionó
                    String botonValor = boton.getText();     // Texto del botón
                    
                    // BOTONES DE OPERADORES (derecha)
                    if(Arrays.asList(simbolosDerecha).contains(botonValor)){
                        if(botonValor == "="){
                            // Botón IGUAL - realizar cálculo
                            if(A != null){
                                B = displaylabel.getText();  // Obtener segundo número
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                // Realizar operación según el operador guardado
                                if(operador == "+"){
                                    displaylabel.setText(quitarCeroAlFinal(numA + numB));
                                } else if(operador == "-"){
                                    displaylabel.setText(quitarCeroAlFinal(numA - numB));
                                } else if (operador == "x"){
                                    displaylabel.setText(quitarCeroAlFinal(numA * numB));
                                } else if (operador == "÷"){
                                    displaylabel.setText(quitarCeroAlFinal(numA / numB));
                                }
                                limpiarTodo();  // Reiniciar variables después del cálculo
                            }
                        } else if("+-x÷".contains(botonValor)){
                            // Botones de operación (+, -, x, ÷)
                            if(operador == null){
                                A = displaylabel.getText();  // Guardar primer número
                                displaylabel.setText("0");   // Resetear display
                                B = "0";
                            }
                            operador = botonValor;  // Guardar operador seleccionado
                        }
                    
                    // BOTONES SUPERIORES (AC, +/-, %)
                    } else if (Arrays.asList(simbolosArriba).contains(botonValor)) {
                        if(botonValor == "AC"){
                            // Limpiar TODO - reset completo
                            limpiarTodo();
                            displaylabel.setText("0");
                        } else if (botonValor == "+/-") {
                            // Cambiar signo (+/-)
                            double numDisplay = Double.parseDouble(displaylabel.getText());
                            numDisplay *= -1;  // Invertir signo
                            displaylabel.setText(quitarCeroAlFinal(numDisplay));
                        } else if(botonValor == "%"){
                            // Porcentaje (dividir entre 100)
                            double numDisplay = Double.parseDouble(displaylabel.getText());
                            numDisplay /= 100;
                            displaylabel.setText(quitarCeroAlFinal(numDisplay));
                        }
                    
                    // BOTONES NUMÉRICOS Y PUNTO DECIMAL
                    } else {
                        if(botonValor == ".") {
                            // Punto decimal - solo si no existe ya
                            if(!displaylabel.getText().contains(botonValor)){
                                displaylabel.setText(displaylabel.getText() + botonValor);
                            }
                        } else if ("0123456789".contains(botonValor)){
                            // Números del 0-9
                            if (displaylabel.getText().equals("0")){  // CORRECCIÓN: usar .equals()
                                displaylabel.setText(botonValor);     // Reemplazar el 0
                            } else {
                                displaylabel.setText(displaylabel.getText() + botonValor); // Concatenar
                            }
                        }
                    }
                }
            });
            frame.setVisible(true);  // Hacer visible la ventana
        }
    }

    // Método para limpiar todas las variables de operación
    void limpiarTodo(){
        A = "0";        // Resetear primer número
        operador = null; // Resetear operador
        B = null;       // Resetear segundo número
    }

    // Método para formatear números (quitar .0 si es entero)
    String quitarCeroAlFinal(double numDisplay){
        if(numDisplay % 1 == 0){  // Si es número entero
            return Integer.toString((int) numDisplay);  // Convertir a int y luego a string
        }
        return Double.toString(numDisplay);  // Dejar como double con decimales
    }
}
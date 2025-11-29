package EjercicioHora;

public class Main {
    static void main() {
        Hora12 hora= new Hora12(6,30, Hora12.Meridiano.AM);

        for (int i=0; i<=60; i++){
            hora.inc();
        }
        System.out.println(hora.toString());
        hora.setHora(12);
        System.out.println(hora.toString());
    }
}

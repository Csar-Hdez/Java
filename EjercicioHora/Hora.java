package EjercicioHora;

public class Hora {
    protected int hora, minutos;

    public Hora(int hora, int minutos){
        this.hora=hora;
        this.minutos=minutos;
    }

    public void inc(){
        minutos++;
        if(minutos >=60){
            minutos=0;
            hora++;
        }
        if(hora>=24){
            hora=0;
        }
    }

    public void setMinutos(int minutos){
        if(minutos>=0 && minutos<60){
            this.minutos=minutos;
        }
    }

    public void setHora(int hora){
        if(hora>=0 && hora<24){
            this.hora=hora;
        }
    }

    @Override
    public String toString(){
        return hora+" : "+minutos;
    }
}

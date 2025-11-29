package EjercicioHora;

public class Hora12 extends Hora {
    public enum Meridiano{AM, PM};//tipo enumerado para distinguir el meridiano
    protected Meridiano mer;

    public Hora12(int hora, int minutos, Meridiano mer){
        super(hora,minutos);
        setHora(hora);
        this.mer=mer;
    }

    @Override
    public void setHora(int hora){
        if(hora>=0 && hora<=12){
            this.hora=hora;
        }
    }

    @Override
    public void inc(){
     super.inc();

        if(hora>12){
            hora=1;
            mer=mer==Meridiano.AM ? Meridiano.PM : Meridiano.AM;
        }
        }

        @Override
        public String toString(){
        return hora+" : "+minutos+" "+mer;
        }
    }


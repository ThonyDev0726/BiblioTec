package WebServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "Biblioteca")
public class Biblioteca {

    @WebMethod(operationName = "ESTUDIANTE")
    public List ESTUDIANTE(
            @WebParam(name = "estNombres") String estNombres,
            @WebParam(name = "estApellidos") String estApellidos,
            @WebParam(name = "estCedula") String estCedula,
            @WebParam(name = "estCarrera") String estCarrera,
            @WebParam(name = "estNivel") String estNivel,
            @WebParam(name = "estParalelo") String estParalelo,
            @WebParam(name = "estDireccion") String estDireccion,
            @WebParam(name = "estTelefono") String estTelefono,
            @WebParam(name = "estCorreo") String estCorreo) {
        ArrayList<String> estudianteLista = new ArrayList<>();
        estudianteLista.add(estNombres);
        estudianteLista.add(estApellidos);
        estudianteLista.add(estCedula);
        estudianteLista.add(estCarrera);
        estudianteLista.add(estNivel);
        estudianteLista.add(estParalelo);
        estudianteLista.add(estDireccion);
        estudianteLista.add(estTelefono);
        estudianteLista.add(estCorreo);
        return estudianteLista;
    }

    @WebMethod(operationName = "LIBRO_DATOS")
    public List LIBRO_DATOS(
            @WebParam(name = "libCodigo") String libCodigo,
            @WebParam(name = "libTitulo") String libTitulo,
            @WebParam(name = "estCedula") String libAutor,
            @WebParam(name = "estCarrera") Integer libPag,
            @WebParam(name = "estNivel") String libTipo,
            @WebParam(name = "estParalelo") String libCarrera,
            @WebParam(name = "estDireccion") String libDescripcion) {
        ArrayList<String> LIBRO_DATOS_LISTA = new ArrayList<>();
        LIBRO_DATOS_LISTA.add(libCodigo);
        LIBRO_DATOS_LISTA.add(libTitulo);
        LIBRO_DATOS_LISTA.add(libAutor);
        LIBRO_DATOS_LISTA.add(libPag.toString());
        LIBRO_DATOS_LISTA.add(libTipo);
        LIBRO_DATOS_LISTA.add(libCarrera);
        LIBRO_DATOS_LISTA.add(libDescripcion);
        return LIBRO_DATOS_LISTA;
    }

    @WebMethod(operationName = "CALCULO_TOTAL")
    public String CALCULO_TOTAL(
            @WebParam(name = "DIA_ENTREGA") String DIA_ENTREGA,
            @WebParam(name = "MES_ENTREGA") String MES_ENTREGA,
            @WebParam(name = "ANO_ENTREGA") String ANO_ENTREGA,
            @WebParam(name = "HORA_ENTREGA") String HORA_ENTREGA,
            @WebParam(name = "MINUTOS_ENTREGA") String MINUTOS_ENTREGA,
            @WebParam(name = "ESTADO_ENTREGA") String ESTADO_ENTREGA)
            throws ParseException {
        /*OBJETOS PARA REALIZAR LOS REQUERIMIENTOS */
        Date FECHA = new Date();
        Calendar CALENDARIO = new GregorianCalendar();
        ZoneId ZONA_HORARIA = ZoneId.systemDefault();
        LocalDate DIA_ACTUAL = FECHA.toInstant().atZone(ZONA_HORARIA).toLocalDate();
        DateTimeFormatter DIA_ACTUAL_TIEMPO_FORMATEADOR = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        /* =========== VARIABLES PRESTAMO =========== */
        int MES_PRESTAMO = DIA_ACTUAL.getMonth().getValue();
        int DIA_PRESTAMO = DIA_ACTUAL.getDayOfMonth();
        String DIA_SEMANA_PRESTAMO = DIA_ACTUAL.getDayOfWeek().toString();
        String DIA_SEMANA_PRESTAMO_ESPANOL;
        int HORA_PRESTAMO = CALENDARIO.get(Calendar.HOUR_OF_DAY);
        String DIA_PERSTAMO_FORMATEADO = DIA_ACTUAL_TIEMPO_FORMATEADOR.format(LocalDateTime.now());
        /* =========== VARIABLES ENTREGA =========== */
        String FECHA_ENTREGA = DIA_ENTREGA + "-" + MES_ENTREGA + "-" + ANO_ENTREGA + " " + HORA_ENTREGA + ":" + MINUTOS_ENTREGA + ":00";
        Date fechaInicialPrestamo = CALENDARIO.getTime();
//        int dias = (int) ((fechaSolicitud.getTime() - fechaInicialPrestamo.getTime()) / 86400000);

        if ((HORA_PRESTAMO >= 10)) {
            if (Integer.parseInt(HORA_ENTREGA) <= 23) {
                /* =========== VALIDACION DE LOS SABADOS Y DOMINGOS PARA NO LABORAR EN LOS PRESTAMOS =========== */
                System.out.println("SI HAY ATENCION\n");
                // =========== GESTION PARA LOS FERIADOS PARA LOS PRESTAMOS Y ENTREGAS =========== */
                if ((MES_PRESTAMO == 1 && DIA_PRESTAMO == 1) || (Integer.parseInt(MES_ENTREGA) == 7 && Integer.parseInt(DIA_ENTREGA) == 9)) {
                    return "NO HAY ATENCION PORQUE ESTAMOS EN FERIADO";
                } else if ((MES_PRESTAMO == 1 && DIA_PRESTAMO == 1) || (Integer.parseInt(MES_ENTREGA) == 7 && Integer.parseInt(DIA_ENTREGA) == 9)) {
                    return "NO HAY ATENCION PORQUE ES MI BDAY";
                }
                if (DIA_SEMANA_PRESTAMO.equals("SATURDAY")) {
                    DIA_SEMANA_PRESTAMO_ESPANOL = "Sabados";
                    return "NO HAY ATENCION LOS DIAS " + DIA_SEMANA_PRESTAMO_ESPANOL.toUpperCase();
                } 
                else if (DIA_SEMANA_PRESTAMO.equals("SUNDAY")) {
                    DIA_SEMANA_PRESTAMO_ESPANOL = "Domingos";
                    return "NO HAY ATENCION LOS DIAS " + DIA_SEMANA_PRESTAMO_ESPANOL.toUpperCase();
                }
                System.out.println("HORA ENTREGA VALIDA");
                return "\n" + "DIA DE LA SOLICITUD: " + DIA_PERSTAMO_FORMATEADO + "\n"
                        + "FECHA DE LA ENTREGA: " + FECHA_ENTREGA + "\n"
                        + "CONTABILIZACION DEL PRESTAMO: " + VER_DIFERENCIA_DE_FECHAS(DIA_PERSTAMO_FORMATEADO, FECHA_ENTREGA);
            } else {
                System.out.println("HORA ENTREGA INVALIDA");
            }
            return "HORA PRESTAMO VALIDA";
        } else {
            /* =========== VALIDACION DE LOS SABADOS Y DOMINGOS PARA NO LABORAR EN LOS PRESTAMOS =========== */
            return "INTENTELO DE NUEVO A PARTIR DE LAS 14h:30min hasta las 18h:30min";
        }

    }

    public static String VER_DIFERENCIA_DE_FECHAS(String start_date, String end_date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);
            long difference_In_Time = d2.getTime() - d1.getTime();
            long DIFERENCIA_SEGUNDOS = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;
            long DIFERENCIA_MINUTOS = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;
            long DIFERENCIA_HORAS = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;
            long DIFERENCIA_DIAS = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
            long DIFERENCIA_ANOS = TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l;
            return DIFERENCIA_ANOS + " Anos, " + DIFERENCIA_DIAS + " Dias, " + DIFERENCIA_HORAS + " Horas, " + DIFERENCIA_MINUTOS + " Minutos, " + DIFERENCIA_SEGUNDOS + " Segundos";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "No se puede realizar el calculo";
    }
}

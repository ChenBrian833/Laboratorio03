import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase que representa el sistema de gestión del hospital
 */
public class Hospital {
    private String nombreHospital;
    private List<TrabajadorMedico> personal;
    private List<Cita> citas;

    public Hospital(String nombreHospital) {
        this.nombreHospital = nombreHospital;
        this.personal = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    public void contratarPersonal(TrabajadorMedico trabajador) {
        personal.add(trabajador);
    }

    public boolean programarCita(Cita cita) {
        if (!detectarConflictos(cita.getTrabajadorAsignado(), cita.getFechaHora())) {
            citas.add(cita);
            return true;
        }
        return false;
    }

    public boolean reagendarCita(String idCita, LocalDateTime nuevaFecha, String motivo) {
        for (Cita cita : citas) {
            if (cita.getIdCita().equals(idCita)) {
                if (!detectarConflictos(cita.getTrabajadorAsignado(), nuevaFecha)) {
                    return cita.reagendar(nuevaFecha, motivo);
                }
                return false;
            }
        }
        return false;
    }

    public boolean verificarDisponibilidad(TrabajadorMedico trabajador, LocalDateTime fecha) {
        return !detectarConflictos(trabajador, fecha);
    }

    public boolean detectarConflictos(TrabajadorMedico trabajador, LocalDateTime fecha) {
        for (Cita cita : citas) {
            if (cita.getTrabajadorAsignado().equals(trabajador) && 
                cita.getFechaHora().equals(fecha) &&
                (cita.getEstado() == EstadoCita.PROGRAMADA || 
                 cita.getEstado() == EstadoCita.CONFIRMADA)) {
                return true;
            }
        }
        return false;
    }

    public List<TrabajadorMedico> buscarPersonalDisponible(String especialidad, LocalDateTime fecha) {
        List<TrabajadorMedico> disponibles = new ArrayList<>();
        for (TrabajadorMedico trabajador : personal) {
            if (trabajador instanceof Doctor) {
                Doctor doctor = (Doctor) trabajador;
                if (doctor.getEspecializacion().equalsIgnoreCase(especialidad) && 
                    verificarDisponibilidad(trabajador, fecha)) {
                    disponibles.add(trabajador);
                }
            }
        }
        return disponibles;
    }

    public double calcularNominaDepartamento(String departamento) {
        double total = 0;
        for (TrabajadorMedico trabajador : personal) {
            if (trabajador.getDepartamento().equalsIgnoreCase(departamento)) {
                total += trabajador.calcularSalario();
            }
        }
        return total;
    }

    public String generarReportePersonal() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n========== REPORTE DE PERSONAL ==========\n");
        reporte.append("Hospital: ").append(nombreHospital).append("\n");
        reporte.append("Total de empleados: ").append(personal.size()).append("\n\n");
        
        for (TrabajadorMedico trabajador : personal) {
            reporte.append(trabajador.toString()).append("\n");
        }
        return reporte.toString();
    }

    public String generarReporteCitas(EstadoCita estadoFiltro) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n========== REPORTE DE CITAS ==========\n");
        reporte.append("Estado: ").append(estadoFiltro != null ? estadoFiltro : "TODAS").append("\n\n");
        
        int contador = 0;
        for (Cita cita : citas) {
            if (estadoFiltro == null || cita.getEstado() == estadoFiltro) {
                reporte.append(cita.toString()).append("\n");
                contador++;
            }
        }
        reporte.append("\nTotal de citas: ").append(contador).append("\n");
        return reporte.toString();
    }

    public String generarReporteFinanciero() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n========== ANÁLISIS FINANCIERO ==========\n");
        reporte.append("Hospital: ").append(nombreHospital).append("\n\n");
        
        String[] departamentos = {"Medicina General", "Cirugía", "Enfermería", 
                                  "Radiología", "Farmacia", "Terapia"};
        
        double totalGeneral = 0;
        for (String depto : departamentos) {
            double totalDepto = calcularNominaDepartamento(depto);
            if (totalDepto > 0) {
                reporte.append("Departamento: ").append(depto)
                      .append(" - Q").append(String.format("%.2f", totalDepto)).append("\n");
                totalGeneral += totalDepto;
            }
        }
        
        reporte.append("\n------------------------------------------\n");
        reporte.append("NÓMINA TOTAL: Q").append(String.format("%.2f", totalGeneral)).append("\n");
        return reporte.toString();
    }

    public String listarHistorialReagendamientos() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n========== HISTORIAL DE REAGENDAMIENTOS ==========\n\n");
        
        int totalReagendamientos = 0;
        for (Cita cita : citas) {
            List<HistorialReagendamiento> historial = cita.getHistorial();
            if (!historial.isEmpty()) {
                reporte.append("Cita #").append(cita.getIdCita())
                      .append(" - Paciente: ").append(cita.getNombrePaciente()).append("\n");
                for (HistorialReagendamiento registro : historial) {
                    reporte.append("  ").append(registro.toString()).append("\n");
                    totalReagendamientos++;
                }
                reporte.append("\n");
            }
        }
        
        reporte.append("Total de reagendamientos: ").append(totalReagendamientos).append("\n");
        return reporte.toString();
    }

    public List<Cita> obtenerCitasPorTrabajador(TrabajadorMedico trabajador) {
        List<Cita> citasTrabajador = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getTrabajadorAsignado().equals(trabajador)) {
                citasTrabajador.add(cita);
            }
        }
        return citasTrabajador;
    }

    public double calcularNominaTotal() {
        double total = 0;
        for (TrabajadorMedico trabajador : personal) {
            total += trabajador.calcularSalario();
        }
        return total;
    }

    public List<TrabajadorMedico> getPersonal() {
        return new ArrayList<>(personal);
    }

    public List<Cita> getCitas() {
        return new ArrayList<>(citas);
    }

    public String getNombreHospital() {
        return nombreHospital;
    }
}
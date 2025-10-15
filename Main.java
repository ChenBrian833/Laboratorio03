import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

/**
 * Clase principal del Sistema Integral de Gestión Hospitalaria
 * Programa creado para administrar personal médico y citas
 */
public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital("Hospital San Rafael");
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA INTEGRAL DE GESTIÓN HOSPITALARIA           ║");
        System.out.println("║   Hospital San Rafael - Guatemala                    ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Cargar datos de prueba iniciales
        cargarDatosPrueba(hospital);
        
        boolean continuar = true;
        
        int opcion = 0;
        if (continuar) {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                opcion = 0;
            }
            
            System.out.println();
            
            if (opcion == 1) {
                contratarNuevoPersonal(hospital, scanner);
            } else if (opcion == 2) {
                programarNuevaCita(hospital, scanner);
            } else if (opcion == 3) {
                reagendarCitaExistente(hospital, scanner);
            } else if (opcion == 4) {
                cambiarEstadoCita(hospital, scanner);
            } else if (opcion == 5) {
                registrarActividad(hospital, scanner);
            } else if (opcion == 6) {
                System.out.println(hospital.generarReportePersonal());
            } else if (opcion == 7) {
                reporteCitasPorEstado(hospital, scanner);
            } else if (opcion == 8) {
                System.out.println(hospital.generarReporteFinanciero());
            } else if (opcion == 9) {
                System.out.println(hospital.listarHistorialReagendamientos());
            } else if (opcion == 10) {
                buscarPersonalDisponible(hospital, scanner);
            } else if (opcion == 11) {
                ejecutarEscenarioPrueba(hospital);
            } else if (opcion == 12) {
                System.out.println("\n¡Gracias por usar el sistema! ¡Hasta pronto!");
                continuar = false;
            } else {
                System.out.println("⚠ Opción no válida. Intente de nuevo.");
            }
            
            if (continuar && opcion != 0) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                    MENÚ PRINCIPAL                      ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Contratar nuevo personal médico                   ║");
        System.out.println("║  2. Programar nueva cita                              ║");
        System.out.println("║  3. Reagendar cita existente                          ║");
        System.out.println("║  4. Cambiar estado de cita                            ║");
        System.out.println("║  5. Registrar actividad médica                        ║");
        System.out.println("║  6. Reporte de personal                               ║");
        System.out.println("║  7. Reporte de citas por estado                       ║");
        System.out.println("║  8. Análisis financiero                               ║");
        System.out.println("║  9. Historial de reagendamientos                      ║");
        System.out.println("║ 10. Buscar personal disponible                        ║");
        System.out.println("║ 11. Ejecutar escenario de prueba completo             ║");
        System.out.println("║ 12. Salir                                             ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    private static void cargarDatosPrueba(Hospital hospital) {
        System.out.println("⏳ Cargando personal médico inicial...\n");
        
        // Doctores
        hospital.contratarPersonal(new Doctor("D001", "Dr. Carlos Méndez", 
            "Medicina General", 10, 8000, "Medicina General", 15, 150));
        hospital.contratarPersonal(new Doctor("D002", "Dra. María López", 
            "Medicina General", 8, 8000, "Pediatría", 12, 180));
        hospital.contratarPersonal(new Doctor("D003", "Dr. Roberto García", 
            "Medicina General", 15, 8000, "Cardiología", 10, 200));
        
        // Cirujanos
        hospital.contratarPersonal(new Cirujano("C001", "Dr. Fernando Ruiz", 
            "Cirugía", 12, 12000, 
            Arrays.asList("Apendicectomía", "Colecistectomía", "Hernia"), 
            40, 3000, 500));
        hospital.contratarPersonal(new Cirujano("C002", "Dra. Ana Martínez", 
            "Cirugía", 10, 12000, 
            Arrays.asList("Cesárea", "Histerectomía"), 
            35, 2500, 450));
        
        // Enfermeros
        hospital.contratarPersonal(new Enfermero("E001", "Lic. Juan Pérez", 
            "Enfermería", 5, 4500, TipoTurno.DIURNO, "Nivel 2"));
        hospital.contratarPersonal(new Enfermero("E002", "Lic. Sofía Ramírez", 
            "Enfermería", 7, 4500, TipoTurno.NOCTURNO, "Nivel 3"));
        hospital.contratarPersonal(new Enfermero("E003", "Lic. Pedro Morales", 
            "Enfermería", 3, 4000, TipoTurno.NOCTURNO, "Nivel 1"));
        
        // Radiólogos
        hospital.contratarPersonal(new Radiologo("R001", "Dr. Luis Castillo", 
            "Radiología", 9, 7000, 
            Arrays.asList("Rayos X", "Tomografía", "Resonancia Magnética"), 
            250));
        hospital.contratarPersonal(new Radiologo("R002", "Dra. Carmen Flores", 
            "Radiología", 6, 7000, 
            Arrays.asList("Ecografía", "Rayos X"), 
            200));
        
        // Farmacéuticos
        hospital.contratarPersonal(new Farmaceutico("F001", "Q.F. Jorge Díaz", 
            "Farmacia", 8, 5000, 50, true));
        hospital.contratarPersonal(new Farmaceutico("F002", "Q.F. Patricia Vásquez", 
            "Farmacia", 4, 5000, 40, false));
        
        // Terapeutas
        hospital.contratarPersonal(new Terapeuta("T001", "Lic. Miguel Hernández", 
            "Terapia", 6, 5500, "Fisioterapia", 60, 120));
        hospital.contratarPersonal(new Terapeuta("T002", "Lic. Laura Sánchez", 
            "Terapia", 5, 5500, "Terapia Ocupacional", 45, 100));
        hospital.contratarPersonal(new Terapeuta("T003", "Lic. Ricardo Morales", 
            "Terapia", 4, 5500, "Terapia Respiratoria", 50, 110));
        
        System.out.println("✓ Se han contratado " + hospital.getPersonal().size() + " trabajadores médicos");
        
        // Cargar algunas citas de prueba
        System.out.println("⏳ Programando citas iniciales...\n");
        
        TrabajadorMedico doctor1 = hospital.getPersonal().get(0);
        TrabajadorMedico doctor2 = hospital.getPersonal().get(1);
        TrabajadorMedico cirujano1 = hospital.getPersonal().get(3);
        
        hospital.programarCita(new Cita("C001", "Juan Pérez González", doctor1, 
            LocalDateTime.of(2025, 10, 20, 9, 0), TipoCita.CONSULTA_GENERAL));
        hospital.programarCita(new Cita("C002", "María Fernández", doctor2, 
            LocalDateTime.of(2025, 10, 20, 10, 0), TipoCita.CONSULTA_GENERAL));
        hospital.programarCita(new Cita("C003", "Pedro Martínez", cirujano1, 
            LocalDateTime.of(2025, 10, 21, 14, 0), TipoCita.CIRUGIA));
        hospital.programarCita(new Cita("C004", "Ana Rodríguez", doctor1, 
            LocalDateTime.of(2025, 10, 22, 11, 0), TipoCita.CONSULTA_GENERAL));
        hospital.programarCita(new Cita("C005", "Carlos López", doctor2, 
            LocalDateTime.of(2025, 10, 23, 15, 0), TipoCita.CONSULTA_GENERAL));
        
        System.out.println("✓ Se han programado " + hospital.getCitas().size() + " citas iniciales");
        System.out.println("✓ Sistema listo para usar\n");
    }
    
    private static void contratarNuevoPersonal(Hospital hospital, Scanner scanner) {
        System.out.println("\n══════ CONTRATAR NUEVO PERSONAL ══════");
        System.out.println("Tipos de personal:");
        System.out.println("1. Doctor General");
        System.out.println("2. Cirujano");
        System.out.println("3. Enfermero");
        System.out.println("4. Radiólogo");
        System.out.println("5. Farmacéutico");
        System.out.println("6. Terapeuta");
        System.out.print("\nSeleccione tipo: ");
        
        int tipo = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("ID de empleado: ");
        String id = scanner.nextLine();
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Departamento: ");
        String depto = scanner.nextLine();
        System.out.print("Años de experiencia: ");
        int anios = scanner.nextInt();
        System.out.print("Salario base: ");
        double salario = scanner.nextDouble();
        scanner.nextLine();
        
        if (tipo == 1) {
            System.out.print("Especialización: ");
            String esp = scanner.nextLine();
            System.out.print("Capacidad pacientes por día: ");
            int cap = scanner.nextInt();
            System.out.print("Tarifa por consulta: ");
            double tarifa = scanner.nextDouble();
            
            hospital.contratarPersonal(new Doctor(id, nombre, depto, anios, salario, esp, cap, tarifa));
            System.out.println("\n✓ Doctor contratado exitosamente");
            
        } else if (tipo == 2) {
            System.out.print("Horas cirugía disponibles: ");
            double horas = scanner.nextDouble();
            System.out.print("Bono por riesgo: ");
            double bono = scanner.nextDouble();
            System.out.print("Tarifa por hora: ");
            double tarifa = scanner.nextDouble();
            
            hospital.contratarPersonal(new Cirujano(id, nombre, depto, anios, salario, 
                Arrays.asList("General"), horas, bono, tarifa));
            System.out.println("\n✓ Cirujano contratado exitosamente");
            
        } else if (tipo == 3) {
            System.out.print("Tipo de turno (1=Diurno, 2=Nocturno): ");
            int turno = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Nivel de certificación: ");
            String nivel = scanner.nextLine();
            
            TipoTurno tipoTurno = (turno == 1) ? TipoTurno.DIURNO : TipoTurno.NOCTURNO;
            hospital.contratarPersonal(new Enfermero(id, nombre, depto, anios, salario, tipoTurno, nivel));
            System.out.println("\n✓ Enfermero contratado exitosamente");
            
        } else if (tipo == 4) {
            System.out.print("Tarifa por estudio: ");
            double tarifa = scanner.nextDouble();
            
            hospital.contratarPersonal(new Radiologo(id, nombre, depto, anios, salario, 
                Arrays.asList("Rayos X"), tarifa));
            System.out.println("\n✓ Radiólogo contratado exitosamente");
            
        } else if (tipo == 5) {
            System.out.print("Límite prescripciones por día: ");
            int limite = scanner.nextInt();
            System.out.print("¿Licencia sustancias controladas? (1=Sí, 0=No): ");
            int lic = scanner.nextInt();
            
            hospital.contratarPersonal(new Farmaceutico(id, nombre, depto, anios, salario, 
                limite, lic == 1));
            System.out.println("\n✓ Farmacéutico contratado exitosamente");
            
        } else if (tipo == 6) {
            scanner.nextLine();
            System.out.print("Tipo de terapia: ");
            String tipoTerapia = scanner.nextLine();
            System.out.print("Duración promedio sesión (min): ");
            int duracion = scanner.nextInt();
            System.out.print("Tarifa por sesión: ");
            double tarifa = scanner.nextDouble();
            
            hospital.contratarPersonal(new Terapeuta(id, nombre, depto, anios, salario, 
                tipoTerapia, duracion, tarifa));
            System.out.println("\n✓ Terapeuta contratado exitosamente");
        }
    }
    
    private static void programarNuevaCita(Hospital hospital, Scanner scanner) {
        System.out.println("\n══════ PROGRAMAR NUEVA CITA ══════");
        
        System.out.print("ID de cita: ");
        String idCita = scanner.nextLine();
        System.out.print("Nombre del paciente: ");
        String paciente = scanner.nextLine();
        
        System.out.println("\nPersonal médico disponible:");
        List<TrabajadorMedico> personal = hospital.getPersonal();
        for (int i = 0; i < personal.size(); i++) {
            System.out.println((i + 1) + ". " + personal.get(i).getNombreCompleto() + 
                " - " + personal.get(i).getClass().getSimpleName());
        }
        
        System.out.print("\nSeleccione médico (número): ");
        int indiceMedico = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (indiceMedico < 0 || indiceMedico >= personal.size()) {
            System.out.println("⚠ Médico no válido");
            return;
        }
        
        System.out.print("Año: ");
        int anio = scanner.nextInt();
        System.out.print("Mes: ");
        int mes = scanner.nextInt();
        System.out.print("Día: ");
        int dia = scanner.nextInt();
        System.out.print("Hora: ");
        int hora = scanner.nextInt();
        System.out.print("Minuto: ");
        int minuto = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\nTipos de cita:");
        System.out.println("1. CONSULTA_GENERAL");
        System.out.println("2. CIRUGIA");
        System.out.println("3. TERAPIA");
        System.out.println("4. DIAGNOSTICO");
        System.out.print("Seleccione tipo: ");
        int tipoNum = scanner.nextInt();
        scanner.nextLine();
        
        TipoCita tipo = TipoCita.CONSULTA_GENERAL;
        if (tipoNum == 2) tipo = TipoCita.CIRUGIA;
        else if (tipoNum == 3) tipo = TipoCita.TERAPIA;
        else if (tipoNum == 4) tipo = TipoCita.DIAGNOSTICO;
        
        LocalDateTime fecha = LocalDateTime.of(anio, mes, dia, hora, minuto);
        Cita nuevaCita = new Cita(idCita, paciente, personal.get(indiceMedico), fecha, tipo);
        
        if (hospital.programarCita(nuevaCita)) {
            System.out.println("\n✓ Cita programada exitosamente");
        } else {
            System.out.println("\n⚠ No se pudo programar la cita. Conflicto de horario.");
        }
    }
    
    private static void reagendarCitaExistente(Hospital hospital, Scanner scanner) {
        System.out.println("\n══════ REAGENDAR CITA ══════");
        
        System.out.println("Citas disponibles:");
        List<Cita> citas = hospital.getCitas();
        for (int i = 0; i < citas.size(); i++) {
            System.out.println((i + 1) + ". " + citas.get(i).toString());
        }
        
        System.out.print("\nID de cita a reagendar: ");
        String idCita = scanner.nextLine();
        
        System.out.print("Nueva fecha - Año: ");
        int anio = scanner.nextInt();
        System.out.print("Mes: ");
        int mes = scanner.nextInt();
        System.out.print("Día: ");
        int dia = scanner.nextInt();
        System.out.print("Hora: ");
        int hora = scanner.nextInt();
        System.out.print("Minuto: ");
        int minuto = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Motivo del cambio: ");
        String motivo = scanner.nextLine();
        
        LocalDateTime nuevaFecha = LocalDateTime.of(anio, mes, dia, hora, minuto);
        
        if (hospital.reagendarCita(idCita, nuevaFecha, motivo)) {
            System.out.println("\n✓ Cita reagendada exitosamente");
            System.out.println("📧 Notificación enviada al paciente (simulado)");
        } else {
            System.out.println("\n⚠ No se pudo reagendar. Verifique el ID o conflictos de horario.");
        }
    }
    
    private static void cambiarEstadoCita(Hospital hospital, Scanner scanner) {
        System.out.println("\n══════ CAMBIAR ESTADO DE CITA ══════");
        
        System.out.println("Citas disponibles:");
        List<Cita> citas = hospital.getCitas();
        for (int i = 0; i < citas.size(); i++) {
            System.out.println((i + 1) + ". " + citas.get(i).toString());
        }
        
        System.out.print("\nSeleccione número de cita: ");
        int indice = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (indice < 0 || indice >= citas.size()) {
            System.out.println("⚠ Cita no válida");
            return;
        }
        
        System.out.println("\nEstados disponibles:");
        System.out.println("1. PROGRAMADA");
        System.out.println("2. CONFIRMADA");
        System.out.println("3. EN_PROGRESO");
        System.out.println("4. COMPLETADA");
        System.out.println("5. CANCELADA");
        System.out.print("\nSeleccione estado: ");
        int estadoNum = scanner.nextInt();
        scanner.nextLine();
        
        EstadoCita nuevoEstado = EstadoCita.PROGRAMADA;
        if (estadoNum == 2) nuevoEstado = EstadoCita.CONFIRMADA;
        else if (estadoNum == 3) nuevoEstado = EstadoCita.EN_PROGRESO;
        else if (estadoNum == 4) nuevoEstado = EstadoCita.COMPLETADA;
        else if (estadoNum == 5) nuevoEstado = EstadoCita.CANCELADA;
        
        citas.get(indice).cambiarEstado(nuevoEstado);
        System.out.println("\n✓ Estado actualizado exitosamente");
    }
    
    private static void registrarActividad(Hospital hospital, Scanner scanner) {
        System.out.println("\n══════ REGISTRAR ACTIVIDAD MÉDICA ══════");
        
        System.out.println("Seleccione personal:");
        List<TrabajadorMedico> personal = hospital.getPersonal();
        for (int i = 0; i < personal.size(); i++) {
            System.out.println((i + 1) + ". " + personal.get(i).getNombreCompleto() + 
                " - " + personal.get(i).getClass().getSimpleName());
        }
        
        System.out.print("\nSeleccione número: ");
        int indice = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (indice < 0 || indice >= personal.size()) {
            System.out.println("⚠ Personal no válido");
            return;
        }
        
        TrabajadorMedico trabajador = personal.get(indice);
        
        if (trabajador instanceof Doctor) {
            ((Doctor) trabajador).registrarConsulta();
            System.out.println("\n✓ Consulta registrada");
        } else if (trabajador instanceof Cirujano) {
            System.out.print("Horas de cirugía: ");
            double horas = scanner.nextDouble();
            ((Cirujano) trabajador).registrarCirugia(horas);
            System.out.println("\n✓ Cirugía registrada");
        } else if (trabajador instanceof Radiologo) {
            ((Radiologo) trabajador).registrarEstudio();
            System.out.println("\n✓ Estudio registrado");
        } else if (trabajador instanceof Farmaceutico) {
            ((Farmaceutico) trabajador).despacharPrescripcion();
            System.out.println("\n✓ Prescripción despachada");
        } else if (trabajador instanceof Terapeuta) {
            ((Terapeuta) trabajador).registrarSesion();
            System.out.println("\n✓ Sesión registrada");
        } else {
            System.out.println("⚠ Este tipo de personal no registra actividades");
        }
    }
    
    private static void reporteCitasPorEstado(Hospital hospital, Scanner scanner) {
        System.out.println("\n══════ REPORTE DE CITAS ══════");
        System.out.println("1. Todas las citas");
        System.out.println("2. Solo PROGRAMADAS");
        System.out.println("3. Solo CONFIRMADAS");
        System.out.println("4. Solo COMPLETADAS");
        System.out.println("5. Solo CANCELADAS");
        System.out.print("\nSeleccione opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        
        EstadoCita estado = null;
        if (opcion == 2) estado = EstadoCita.PROGRAMADA;
        else if (opcion == 3) estado = EstadoCita.CONFIRMADA;
        else if (opcion == 4) estado = EstadoCita.COMPLETADA;
        else if (opcion == 5) estado = EstadoCita.CANCELADA;
        
        System.out.println(hospital.generarReporteCitas(estado));
    }
    
    private static void buscarPersonalDisponible(Hospital hospital, Scanner scanner) {
        System.out.println("\n══════ BUSCAR PERSONAL DISPONIBLE ══════");
        System.out.print("Especialidad a buscar: ");
        String especialidad = scanner.nextLine();
        
        System.out.print("Fecha - Año: ");
        int anio = scanner.nextInt();
        System.out.print("Mes: ");
        int mes = scanner.nextInt();
        System.out.print("Día: ");
        int dia = scanner.nextInt();
        System.out.print("Hora: ");
        int hora = scanner.nextInt();
        System.out.print("Minuto: ");
        int minuto = scanner.nextInt();
        
        LocalDateTime fecha = LocalDateTime.of(anio, mes, dia, hora, minuto);
        List<TrabajadorMedico> disponibles = hospital.buscarPersonalDisponible(especialidad, fecha);
        
        System.out.println("\n════ RESULTADOS ════");
        if (disponibles.isEmpty()) {
            System.out.println("⚠ No hay personal disponible con esa especialidad en ese horario");
        } else {
            System.out.println("✓ Personal disponible encontrado:");
            for (TrabajadorMedico t : disponibles) {
                System.out.println("  • " + t.getNombreCompleto());
            }
        }
    }
    
    private static void ejecutarEscenarioPrueba(Hospital hospital) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║     EJECUTANDO ESCENARIO DE PRUEBA COMPLETO         ║");
        System.out.println("╚══════════════════════════════════════════════════════╝\n");
        
        System.out.println("📋 ESCENARIO 1: Agenda Saturada");
        System.out.println("Programando 15 citas adicionales...");
        
        List<TrabajadorMedico> personal = hospital.getPersonal();
        for (int i = 0; i < 15; i++) {
            TrabajadorMedico medico = personal.get(i % personal.size());
            Cita cita = new Cita(
                "TEST" + (i + 1),
                "Paciente Test " + (i + 1),
                medico,
                LocalDateTime.of(2025, 10, 25 + (i / 5), 8 + (i % 8), 0),
                TipoCita.CONSULTA_GENERAL
            );
            hospital.programarCita(cita);
        }
        System.out.println("✓ 15 citas adicionales programadas\n");
        
        System.out.println("📋 ESCENARIO 2: Reagendamientos masivos");
        List<Cita> citas = hospital.getCitas();
        int reagendados = 0;
        for (int i = 0; i < Math.min(5, citas.size()); i++) {
            Cita cita = citas.get(i);
            LocalDateTime nuevaFecha = cita.getFechaHora().plusDays(2);
            if (hospital.reagendarCita(cita.getIdCita(), nuevaFecha, "Optimización de agenda")) {
                reagendados++;
            }
        }
        System.out.println("✓ " + reagendados + " citas reagendadas exitosamente\n");
        
        System.out.println("📋 ESCENARIO 3: Registro de actividades médicas");
        for (TrabajadorMedico trabajador : personal) {
            if (trabajador instanceof Doctor) {
                Doctor doc = (Doctor) trabajador;
                for (int i = 0; i < 5; i++) {
                    doc.registrarConsulta();
                }
            } else if (trabajador instanceof Cirujano) {
                Cirujano cir = (Cirujano) trabajador;
                cir.registrarCirugia(4.5);
            } else if (trabajador instanceof Radiologo) {
                Radiologo rad = (Radiologo) trabajador;
                for (int i = 0; i < 3; i++) {
                    rad.registrarEstudio();
                }
            } else if (trabajador instanceof Farmaceutico) {
                Farmaceutico farm = (Farmaceutico) trabajador;
                for (int i = 0; i < 10; i++) {
                    farm.despacharPrescripcion();
                }
            } else if (trabajador instanceof Terapeuta) {
                Terapeuta ter = (Terapeuta) trabajador;
                for (int i = 0; i < 4; i++) {
                    ter.registrarSesion();
                }
            }
        }
        System.out.println("✓ Actividades registradas para todo el personal\n");
        
        System.out.println("📋 ESCENARIO 4: Cambios de estado de citas");
        int confirmadas = 0;
        int completadas = 0;
        for (int i = 0; i < Math.min(10, citas.size()); i++) {
            if (i < 5) {
                citas.get(i).cambiarEstado(EstadoCita.CONFIRMADA);
                confirmadas++;
            } else {
                citas.get(i).cambiarEstado(EstadoCita.COMPLETADA);
                completadas++;
            }
        }
        System.out.println("✓ " + confirmadas + " citas confirmadas");
        System.out.println("✓ " + completadas + " citas completadas\n");
        
        System.out.println("📋 ESCENARIO 5: Emergencia médica - Redistribución");
        System.out.println("Simulando cirugía urgente...");
        TrabajadorMedico cirujanoEmergencia = null;
        for (TrabajadorMedico t : personal) {
            if (t instanceof Cirujano) {
                cirujanoEmergencia = t;
                break;
            }
        }
        
        if (cirujanoEmergencia != null) {
            Cita citaUrgente = new Cita(
                "URGENTE001",
                "Paciente Emergencia",
                cirujanoEmergencia,
                LocalDateTime.of(2025, 10, 26, 10, 0),
                TipoCita.CIRUGIA
            );
            
            if (hospital.programarCita(citaUrgente)) {
                citaUrgente.cambiarEstado(EstadoCita.CONFIRMADA);
                System.out.println("✓ Cirugía urgente programada y confirmada");
            } else {
                System.out.println("⚠ Conflicto detectado - se requiere reagendar otras citas");
            }
        }
        
        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("           REPORTES FINALES");
        System.out.println("═══════════════════════════════════════════════");
        
        System.out.println(hospital.generarReportePersonal());
        System.out.println(hospital.generarReporteCitas(null));
        System.out.println(hospital.generarReporteFinanciero());
        System.out.println(hospital.listarHistorialReagendamientos());
        
        System.out.println("\n✓ ESCENARIO DE PRUEBA COMPLETADO EXITOSAMENTE");
    }
}
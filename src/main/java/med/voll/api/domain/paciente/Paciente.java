package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;


@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String documentoIdentidad;
    private String telefono;
    private boolean activo;

    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datos){
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.documentoIdentidad = datos.documentoIdentidad();
        this.telefono = datos.telefono();
        this.direccion = new Direccion(datos.direccion());
        this.activo = true;
    }

    public void eliminar() {
        this.activo = false;
    }

    public void actualizarInformacion(DatosActualizacionPaciente datosActualizacionPaciente) {
        if (datosActualizacionPaciente.nombre() != null){
            this.nombre = datosActualizacionPaciente.nombre();
        }

        if (datosActualizacionPaciente.email() != null){
            this.documentoIdentidad = datosActualizacionPaciente.email();
        }

        if (datosActualizacionPaciente.documentoIdentidad() != null){
            this.documentoIdentidad = datosActualizacionPaciente.documentoIdentidad();
        }

        if (datosActualizacionPaciente.telefono() != null){
            this.documentoIdentidad = datosActualizacionPaciente.telefono();
        }

        if (datosActualizacionPaciente.direccion() != null){
            this.direccion = direccion.actualizarDatos(datosActualizacionPaciente.direccion());
        }
    }
}

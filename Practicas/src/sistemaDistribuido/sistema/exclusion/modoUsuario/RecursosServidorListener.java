package sistemaDistribuido.sistema.exclusion.modoUsuario;

import java.util.List;


public interface RecursosServidorListener {
	void actualizarEstado(int recurso, Integer propietario, List<Integer> cola);
}

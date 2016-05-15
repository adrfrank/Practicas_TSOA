package sistemaDistribuido.sistema.exclusion.modoUsuario;

public interface RecursosClienteListener {
	void recursoConseguido(int recurso);
	void recursoLiberado(int recurso);
}

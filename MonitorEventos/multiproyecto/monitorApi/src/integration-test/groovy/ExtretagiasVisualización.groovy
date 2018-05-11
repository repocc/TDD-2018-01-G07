package monitorapi

public enum ExtretagiasVisualizacion {
	DERECHA ("Derecha"),
	IZQUIERDA ("Izquierda")

	final String value

	ExtretagiasVisualización(String value) { this.value = value }

	@Override
	String toString() { value }
	String getKey() { name() }

	static ExtretagiasVisualización convertirEnEnum( String value ) {
		values().find { it.value == value }
	}
}
